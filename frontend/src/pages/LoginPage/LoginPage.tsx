import styled from 'styled-components';
import { Main } from '../../style';
import { ReactComponent as Kakao } from '../../assets/images/kakao.svg';

// const LOGIN_URL = ;

export default function LoginPage() {
  return (
    <Main>
      <Landing>
        <Image src="/H-uick.gif" />
        <KakaoBox
          href={
            import.meta.env.VITE_BASEURL_BACK +
            '/oauth2/authorization/kakao?redirect_uri=' +
            import.meta.env.VITE_BASEURL_FRONT +
            '/oauth/redirect'
          }
        >
          <Kakao />
        </KakaoBox>
      </Landing>
    </Main>
  );
}

const Landing = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
  background: url('/landing.png') no-repeat center;
  background-size: cover;
`;

const KakaoBox = styled.a`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 48px;
  margin-top: 600px;
  position: absolute;
`;

const Image = styled.img`
  width: 350px;
  height: 155px;
  margin-top: 450px;
  filter: drop-shadow(0px 0px 12px rgba(0, 0, 0, 0.25));
`;
