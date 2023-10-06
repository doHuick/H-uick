export const useAuth = (): boolean => {
  // local stroage에서 accessToken을 가져와야 함.
  const nowAccessToken = localStorage.getItem('access_token');
  // console.log('지금 accessToken : ', nowAccessToken);
  
  return !!nowAccessToken;
};