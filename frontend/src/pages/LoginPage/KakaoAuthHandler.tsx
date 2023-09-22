import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import Typewriter from '../../components/Loader/Loader';
import { Main } from '../../style';

const KakaoAuthHandler = () => {
  const navigate = useNavigate();

  // 현재 페이지의 URI에서 Access Token을 추출
  const currentUri: string = window.location.href;
  const accessTokenParam: string | null = new URL(currentUri).searchParams.get(
    'token',
  );

  if (accessTokenParam !== null) {
    localStorage.setItem('access_token', accessTokenParam);
  }

  useEffect(() => {
    // Access Token이 생성되었으면,
    if (accessTokenParam) {
      // 로그인 성공
      console.log('로그인 성공');

      // 회원가입이 된 유저면 메인페이지로 이동
      navigate('/');

      // 회원가입이 안된 유저면 회원가입 페이지로 이동
      // navigate('/signup');
    }
  });

  return (
    <Main>
      <LoaderDiv>
        <Typewriter />
      </LoaderDiv>
    </Main>
  );
};

export default KakaoAuthHandler;

const LoaderDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 300px;
`;
