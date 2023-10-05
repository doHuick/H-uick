import axios from 'axios';

export const BASE_URL = import.meta.env.VITE_BASEURL_BACK;

const instance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

const getAccessToken = () => {
  return localStorage.getItem('access_token');
};

const setAccessToken = (access_token: string) => {
  localStorage.setItem('access_token', access_token);
};

// Request
instance.interceptors.request.use(
  (config) => {
    const access_token = getAccessToken();
    if (access_token) {
      config.headers.Authorization = `Bearer ${access_token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// Response
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // ERROR 401 => Refresh Token 보내기
    if (error?.response?.status === 401) {
      try {
        // access_token 재발급 요청
        await axios
          .post(
            `${BASE_URL}/auth/refresh`,
            { refresh_token: localStorage.getItem('refresh_token') },
            {
              headers: { Authorization: localStorage.getItem('access_token') },
            },
          )
          .then((res) => {
            setAccessToken(res.data.token);
          });

        //access token 을 다시 setting 하고 origin request 를 재요청
        originalRequest.headers.Authorization = `Bearer ${getAccessToken()}`;

        return axios(originalRequest);
      } catch (error) {
        // refresh_token 만료되는 경우는 에러나고 재로그인 해야 함.
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        window.location.href = '/login';
      }
    }

    return Promise.reject(error);
  },
);

export default instance;
