import React from 'react';
import styled from 'styled-components';
import { Main } from '../../style';
import { TextBox } from '../../components/TextBox/TextBox';
import { ReactComponent as Check } from '../../assets/icons/check.svg';
import { ReactComponent as Bar } from '../../assets/icons/long-bar.svg';
import { ConfirmButton } from '../../components/Button/Button';
import HeadBar from '../../components/HeadBar/HeadBar';
import NavBar from '../../components/NavBar/NavBar';
import toast, { toastConfig } from 'react-simple-toasts';
import 'react-simple-toasts/dist/theme/frosted-glass.css';

export default function Welcomepage() {
  // [23-09-15] 계좌주소 받아서 연동시켜야 함
  const walletAdd = '2fW4MLoas49wX0z235pw1z3223k21dolqwl';

  // 계좌 복사 클릭 시 toast 메시지
  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    maxVisibleToasts: 1,
  });

  const handleCopyClick = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      toast('지갑주소가 복사되었습니다.');
    } catch (error) {
      toast('에러가 발생했습니다.');
    }
  };

  return (
    <Main>
      <HeadBar pageName="회원가입" bgcolor="var(--background)" />
      <CheckDiv>
        <Check />
      </CheckDiv>
      <FlexWrapDiv>
        <TextBox
          fontSize="25px"
          fontWeight="700"
          textAlign="center"
          margin="0 0 17px 0"
        >
          회원가입 및 계좌계설 완료
        </TextBox>
        <TextBox fontSize="15.5" color="var(--font-gray)" margin="0 0 55px 0">
          비대면 계좌개설이 완료되었습니다
        </TextBox>
        <Bar />
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            고객명
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            김싸피
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            계좌번호
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            110-400-88327
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            개설일시
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            2023년 9월 5일 14:05
          </TextBox>
        </BetweenDiv>
        <Bar />
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            지갑주소
          </TextBox>
          <CopyButton onClick={() => handleCopyClick(`${walletAdd}`)}>
            복사
          </CopyButton>
        </BetweenDiv>
        <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
          {walletAdd}
        </TextBox>
        <CenterDiv>
          <ConfirmButton>휙 사용해보기</ConfirmButton>
        </CenterDiv>
      </FlexWrapDiv>
      <NavBar />
    </Main>
  );
}

const CenterDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 70px;
`;

const BetweenDiv = styled(CenterDiv)`
  width: 320px;
  justify-content: space-between;
  margin: 7px 0px;
`;

const CopyButton = styled.div`
  width: 50px;
  height: 20px;
  font-size: 11px;
  border: 1px solid var(--black);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  &:hover {
    cursor: pointer;
  }
`;

const FlexWrapDiv = styled.div`
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
`;

const CheckDiv = styled.div`
  margin-top: 150px;
  margin-bottom: 36px;
  display: flex;
  justify-content: center;
`;
