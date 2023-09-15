import React, { useState, useEffect } from 'react';
import NavBar from '../../components/NavBar/NavBar';
import { styled } from 'styled-components';
import { Main } from '../../style';
import { TextBox } from '../../components/TextBox/TextBox';
import { ReactComponent as Dots } from '../../assets/icons/dots.svg';
import { ReactComponent as Copy } from '../../assets/icons/copy.svg';
import { ReactComponent as Bulb } from '../../assets/images/bulb.svg';
import { MainTab } from '../../components/TabBar/MainTab';

type FlexDivProps = {
  margin?: string;
};

export default function Mainpage() {
  // [23-09-10 / 미완] 차용, 대여 기록 API 구현되면 Tab 기능 완성시키기
  const [nowActive, setNowActive] = useState<string>('borrowing');

  return (
    <Main>
      {/* 계좌 박스 */}
      <BlueBox>
        {/* 계좌 정보 */}
        {/* [23-09-11 / 미완 ] 이미지 레이아웃*/}
        {/* <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Bank.png" alt="Bank" width="35" height="35" /> */}
        <FlexDiv margin='0px 30px'>
          <FlexDiv2 margin='10px 0px 0px 0px'>
            <TextBox fontSize='17px' fontWeight='700' color='var(--white)' textAlign='left'>입출금</TextBox>
            <TextBox fontSize='17px' fontWeight='500' color='var(--white)' textAlign='left'>싸피우대통장</TextBox>
          </FlexDiv2>
          <Dots style={{ marginBottom: '10px', cursor: 'pointer'}} />
        </FlexDiv>
        
        {/* 계좌 번호 */}
        <FlexDiv2 margin='0px 30px'>
          <TextBox fontSize='14px' fontWeight='500' color='var(--white)' textAlign='left'>싸피 110-400-88327</TextBox>
          <Copy style={{ cursor: 'pointer' }}/>
        </FlexDiv2>

        {/* 잔액 */}
        <TextBox fontSize='30px' fontWeight='700' color='var(--white)' textAlign='left' margin='33px 30px'>123,456원</TextBox>

        {/* 롤링 공지 */}
        <RollingDiv>
          <TextBox fontSize='12px'><Bulb />이번달 입금 예정 금액</TextBox>
          <TextBox fontSize='12.5px' fontWeight='700'>1,210,000원</TextBox>
        </RollingDiv>
      </BlueBox>
      {/* [23-09-10] setNowActive로 FeeTabProps 전달해주어야 함. */}
      <MainTab />
      {/* 계약 내용 - API 구현되면 로직처리는 ManTab.tsx로 */}
      <WhiteBox>
        <FlexDiv margin='0px 30px 2px 30px'>
          <TextBox fontSize='15px' fontWeight='500' color='var(--black)' textAlign='left'>임준수</TextBox>
          <TextBox fontSize='20px' fontWeight='700' color='var(--huick-blue)' textAlign='left'>2.3%</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px 9px 30px'>
          <TextBox fontSize='12px' fontWeight='500' color='var(--gray)' textAlign='left'>12회 중 8회 납부 완료</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px'>
          <TextBox fontSize='24px' fontWeight='700' color='var(--black)' textAlign='left'>120,000원</TextBox>
          <TextBox fontSize='14px' fontWeight='500' color='var(--black)' textAlign='left'>18일 납부</TextBox>
        </FlexDiv>
      </WhiteBox>
      <WhiteBox>
        <FlexDiv margin='0px 30px 2px 30px'>
          <TextBox fontSize='15px' fontWeight='500' color='var(--black)' textAlign='left'>임준수</TextBox>
          <TextBox fontSize='20px' fontWeight='700' color='var(--huick-blue)' textAlign='left'>2.3%</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px 9px 30px'>
          <TextBox fontSize='12px' fontWeight='500' color='var(--gray)' textAlign='left'>12회 중 8회 납부 완료</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px'>
          <TextBox fontSize='24px' fontWeight='700' color='var(--black)' textAlign='left'>120,000원</TextBox>
          <TextBox fontSize='14px' fontWeight='500' color='var(--black)' textAlign='left'>18일 납부</TextBox>
        </FlexDiv>
      </WhiteBox>
      <WhiteBox>
        <FlexDiv margin='0px 30px 2px 30px'>
          <TextBox fontSize='15px' fontWeight='500' color='var(--black)' textAlign='left'>임준수</TextBox>
          <TextBox fontSize='20px' fontWeight='700' color='var(--huick-blue)' textAlign='left'>2.3%</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px 9px 30px'>
          <TextBox fontSize='12px' fontWeight='500' color='var(--gray)' textAlign='left'>12회 중 8회 납부 완료</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px'>
          <TextBox fontSize='24px' fontWeight='700' color='var(--black)' textAlign='left'>120,000원</TextBox>
          <TextBox fontSize='14px' fontWeight='500' color='var(--black)' textAlign='left'>18일 납부</TextBox>
        </FlexDiv>
      </WhiteBox>
      <WhiteBox>
        <FlexDiv margin='0px 30px 2px 30px'>
          <TextBox fontSize='15px' fontWeight='500' color='var(--black)' textAlign='left'>임준수</TextBox>
          <TextBox fontSize='20px' fontWeight='700' color='var(--huick-blue)' textAlign='left'>2.3%</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px 9px 30px'>
          <TextBox fontSize='12px' fontWeight='500' color='var(--gray)' textAlign='left'>12회 중 8회 납부 완료</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px'>
          <TextBox fontSize='24px' fontWeight='700' color='var(--black)' textAlign='left'>120,000원</TextBox>
          <TextBox fontSize='14px' fontWeight='500' color='var(--black)' textAlign='left'>18일 납부</TextBox>
        </FlexDiv>
      </WhiteBox>
      <WhiteBox>
        <FlexDiv margin='0px 30px 2px 30px'>
          <TextBox fontSize='15px' fontWeight='500' color='var(--black)' textAlign='left'>임준수</TextBox>
          <TextBox fontSize='20px' fontWeight='700' color='var(--huick-blue)' textAlign='left'>2.3%</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px 9px 30px'>
          <TextBox fontSize='12px' fontWeight='500' color='var(--gray)' textAlign='left'>12회 중 8회 납부 완료</TextBox>
        </FlexDiv>
        <FlexDiv margin='0px 30px'>
          <TextBox fontSize='24px' fontWeight='700' color='var(--black)' textAlign='left'>120,000원</TextBox>
          <TextBox fontSize='14px' fontWeight='500' color='var(--black)' textAlign='left'>18일 납부</TextBox>
        </FlexDiv>
      </WhiteBox>
      <NavBar />
    </Main>
  );
}

const BlueBox = styled.div`
  background-color: var(--huick-blue);
  width: 360px;
  height: 184px;
  border-radius: 10px;
  box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
  margin: 6.5px 14px;
  padding: 20px 0px;
  `;

const WhiteBox = styled.div`
  background-color: var(--white);
  width: 360px;
  height: 70px;
  border-radius: 10px;  
  box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
  margin: 9px 14px;
  padding: 14px 0px;
  padding-bottom: 18px;
  `;

const FlexDiv = styled.div<FlexDivProps>`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: ${(props => props.margin)};
  `;

const FlexDiv2 = styled(FlexDiv)`
  justify-content: left;
  gap: 5px;
  `;

const RollingDiv = styled.div`
  width: 275px;
  height: 30px;
  background-color: var(--white);
  border-radius: 9px;
  box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
  padding: 0px 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0px 30px;
`;
