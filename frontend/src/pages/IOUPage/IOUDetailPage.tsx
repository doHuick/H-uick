import React from 'react';
import styled from 'styled-components';
import { Main } from '../../style';
import { TextBox } from '../../components/TextBox/TextBox';
import HeadBar from '../../components/HeadBar/HeadBar';
import NavBar from '../../components/NavBar/NavBar';
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg';

type MarginProps = {
  margin?: string;
};

export default function IOUDetailPage() {
  return (
    <Main>
      <HeadBar pageName="차용증" />
      <TitleDiv margin="105px 25px 20px 25px">
        <TextBox fontSize="20px" fontWeight="700" color="var(--black)">
          납부 요약
        </TextBox>
      </TitleDiv>
      <FlexWrapDiv>
        <CountBox>
          <TextBox fontSize="15px" fontWeight="400" color="var(--font-gray)">
            납부횟수
          </TextBox>
          <TextBox fontSize="15px" fontWeight="700" color="var(--black)">
            5회
          </TextBox>
        </CountBox>
        <RemainBox>
          <TextBox fontSize="15px" fontWeight="400" color="var(--font-gray)">
            납부 잔여 금액
          </TextBox>
          <TextBox fontSize="15px" fontWeight="700" color="var(--black)">
            2,300,000원
          </TextBox>
        </RemainBox>
      </FlexWrapDiv>
      <TitleDiv margin="30px 25px 0 25px">
        <TextBox fontSize="20px" fontWeight="700" color="var(--black)">
          계약 상세
        </TextBox>
      </TitleDiv>
      <FlexWrapDiv margin="10px 0 0 0">
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            채권자
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            김싸피
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            채무자
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            이싸피
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            차용금액
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            32,000,000원
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            계약시작일
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            2023.09.12
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            계약종료일
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            2024.10.12
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            1회차 납부일
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            2023.10.01
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            이율
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            1.2%
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            약정사항
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            없음
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="18px" fontWeight="500" color="var(--black)">
            작성일
          </TextBox>
          <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
            2023.09.26
          </TextBox>
        </BetweenDiv>
        <StyledBar />
        <BetweenDiv>
          <TextBox fontSize="17.5px" margin="0 0 15px 0">이미지로 보기</TextBox>
          <RightArrowResized />
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="17.5px">PDF로 보기</TextBox>
          <RightArrowResized />
        </BetweenDiv>
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
  width: 340px;
  justify-content: space-between;
  margin: 7px 0px;
`;

const FlexWrapDiv = styled.div<MarginProps>`
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  margin: ${(props) => props.margin};
`;

const TitleDiv = styled.div<MarginProps>`
  margin: ${(props) => props.margin};
  display: flex;
  justify-content: left;
  align-items: center;
`;

const CountBox = styled.div`
  width: 330px;
  height: 65px;
  padding: 0 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f0f2f8;
  border-radius: 10px 10px 0px 0px;
`;

const RemainBox = styled(CountBox)`
  height: 50px;
  background-color: #e6e9f3;
  border-radius: 0px 0px 10px 10px;
`;

const RightArrowResized = styled(RightArrow)`
  width: 8px;
`;


const StyledBar = styled.div`
  width: 340px;
  border-bottom: 1px solid var(--font-gray);
  margin: 15px 0;
`;