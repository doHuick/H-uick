import React from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';



export default function NotFoundPage() {
  const navigate = useNavigate();

  const onClickBack = () => {
    navigate('/')
  }
  
  return (
    <Main>
      <GoBack onClick={onClickBack}>
        메인으로가기
      </GoBack>
      <NotFound>
        없는 페이지입니다
      </NotFound>
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

const GoBack = styled.div`
  position: relative;
  margin-top: 100px;
  
`

const NotFound = styled.div`
  position: absolute;
  width: 100%;
  top: 50%;
  display: flex;
  justify-content: center;

`