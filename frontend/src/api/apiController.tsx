import axios from 'axios';

export const BASE_URL = 'http://localhost:5173'

const instance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

const getAccessToken = () => {
  return localStorage.getItem('access_token');
};

const getRefreshToken = () => {
  return localStorage.getItem('refresh_token');
};

const setAccessToken = (access_token: string) => {
  localStorage.setItem('access_token', access_token);
};

const setRefreshToken = (refresh_token: string) => {
  localStorage.setItem('refresh_token', refresh_token);
};

// Request
instance.interceptors.request.use(
  (config) => {
    const access_token = getAccessToken();
    console.log(`Access Token Request : ${access_token}`);
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
    console.log(error.response, error.response.data.status, getRefreshToken());

    // ERROR 401 => Refresh Token 보내기
    if (error?.response?.data?.status === 401) {
      console.log('Access Token 만료!');
      try {
        // [23-09-22] 이 부분도 다시 확인 : get 요청, api 엔드포인트
        console.log('Refresh Token 전송!');
        const response = await axios.get(`${BASE_URL}/oauth/reissue`, {
          headers: {
            refresh_token: getRefreshToken(),
          },
        });
        
        // [23-09-22] 이 부분 로직은 수정해야 함! - Header가 아닌 어디서 받아서 꺼내는지 확인
        // 응답 헤더에서 Access Token과 Refresh Token 추출
        const access_token = response.headers['access_token'];
        const refresh_token = response.headers['refresh_token'];

        //access token 을 다시 setting 하고 origin request 를 재요청
        setAccessToken(access_token);
        setRefreshToken(refresh_token);
        originalRequest.headers.Authorization = `Bearer ${access_token}`;

        //새로운 토큰 발급 확인
        console.log(access_token, refresh_token);

        return axios(originalRequest);
      } catch (error) {
        // 만약 refresh_token 보내도 error 가 뜨면 login 화면으로 보내기 -> redirect
        console.log('Error refreshing token:', error);

        window.location.href = '/login'; // 로그인화면으로 보내기
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
      }
    } else if (error?.response?.data?.status === 403) {
      // access_token 자체를 안보낸 경우
      localStorage.removeItem('access_token');
      localStorage.removeItem('refresh_token');
      window.location.href = '/login';
    }

    return Promise.reject(error);
  },
);

export default instance;
