import { Navigate, Outlet } from 'react-router-dom';
import { useUserInfo } from '../hooks/useUserInfo';

export default function AuthenticatedRoute() {
  const isUserInfo = useUserInfo();

  if (isUserInfo) {
    // 회원 추가정보를 모두 입력했으면 메인으로
    // console.log('가입 완료 확인');
    return <Navigate replace to="/" />;
  }

  // 회원 추가정보를 모두 입력하지 않았으면 signup 렌더링
  return <Outlet />;
}
