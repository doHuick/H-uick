import React from 'react';
import styled from 'styled-components';

import HeadBar from '../../components/HeadBar/HeadBar';



export default function ContractTempPage() {
  const userButtonsJSON = localStorage.getItem('userButtonsLocal'); // userButtonsJSON은 string 또는 null일 수 있음
  let buttonText = "";
  if (userButtonsJSON !== null) {
    const userButtons = JSON.parse(userButtonsJSON);
    buttonText = userButtons[0] === "iLend" ? "송금하고 공유하기" : "공유하기";
  }

  return (
  <Main>
    <HeadBar pageName="임시 차용증" />
    <div>
      임시 차용증 작성을 완료하였습니다<br/>
      계약 전 차용증을 검토해주세요
    </div>
    <div>
      오기입된 내용이 있다면
      파기 후 재작성 해주세요
    </div>
    <div>
      파기
    </div>
    <div>
    {buttonText}
    </div>
  </Main>
  )
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--background);
  overflow-y: scroll;
`;