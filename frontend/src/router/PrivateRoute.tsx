import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

export default function PrivateRoute() {
  const isLogin = useAuth();

  if (isLogin) {
    // 로그인이 된 유저면 리렌더링
    // console.log('로그인 된 상태')
    return <Outlet />;
  }

  // 로그인이 안된 유저면 로그인 페이지로
  return <Navigate replace to='/login' />;
}
