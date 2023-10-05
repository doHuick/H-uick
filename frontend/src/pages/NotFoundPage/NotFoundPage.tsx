import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

export default function NotFoundPage() {
  const navigate = useNavigate();

  const onClickBack = () => {
    navigate('/')
  }
  
  return (
    <Main>
      <TextFrame>
        없는 페이지입니다
        <GoBack onClick={onClickBack}>
          메인으로 가기
        </GoBack>
      </TextFrame>
      <PigHead src="/pig-head.png" alt="" />
    </Main>
  );
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--white);
  overflow-y: scroll;
  overflow-x: hidden;
`;

const TextFrame = styled.div`
  position: relative;
  width: 100%;
  right: 10%;
  top: 20%;
  text-align: end;
  font-size: 32px;
  font-weight: 600;
`

const GoBack = styled.div`
  position: relative;
  font-size: 28px;
  font-weight: 500;
  margin-top: 6px;
`;


const PigHead = styled.img`
  position: absolute;
  width: 92%;
  bottom: -6%;
  left: -28%;
`