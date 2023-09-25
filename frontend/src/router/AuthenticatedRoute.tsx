import { Navigate, Outlet, useNavigate } from 'react-router-dom';
import axios, { BASE_URL } from '../api/apiController';

export default function AuthenticatedRoute() {
  const navigate = useNavigate();
  axios
    .get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    })
    .then((res) => {
      const data = res.data;
      if (!data.address) {
        console.log('이사람 가입 안한사람임!!');
        return <Outlet />;
      }
    });
  return <Navigate replace to="/" />;
}
