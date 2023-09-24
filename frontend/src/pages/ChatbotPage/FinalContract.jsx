import React, { useState } from "react";
import styled from "styled-components";
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg';
import { useNavigate } from "react-router-dom";

const FinalContract = (props) => {
  const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
  const contractDetail = JSON.parse(localStorage.getItem('toSendLocal'));
  const buttonText = userButtons[0] == 'iLend' ? '송금하고 공유하기' : '확인하고 공유하기'

  const navigate = useNavigate();

return (
  <>
    <ChatbotButton>
      <ContractPaper>
        <div>
        <span>차</span><span>용</span><span>증</span>
        </div>
        <div>
          {contractDetail.loanAmount}
        </div>
        <div>
          위 금액을 채무자가 채권자로부터 에 빌립니다. 이자는 연 {contractDetail.interestRate}%로 하여 매달 에 갚겠으며, 원금은 {contractDetail.maturityDate}
        </div>
      </ContractPaper>
    </ChatbotButton>
    <ChatbotButtonShare>
      {buttonText}&nbsp;&nbsp;&nbsp;&nbsp;<RightArrowResized/>
    </ChatbotButtonShare>
  </>
)

}


export default FinalContract

const ChatbotButton = styled.span`
  position: relative;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  color: var(--white);
  text-align: left;
  padding-left: 10px;
  padding-right: 11px;
  margin-left: 12px;
  margin-right: 12px;
  line-height: 19px;
  background-color: var(--huick-blue);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
`

const ChatbotButtonShare = styled(ChatbotButton)`
  position: relative;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  color: var(--black);
  text-align: left;
  padding-left: 10px;
  padding-right: 11px;
  margin-left: 12px;
  margin-right: 12px;
  line-height: 19px;
  background-color: #f2f2f7;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  width: 40%;
`

const RightArrowResized = styled(RightArrow)`
  margin-top: 1.5;
  height: 11px;
  width: auto;
  
`

const ContractPaper = styled.div`
  position: relative;
  width: 100%;
  height: 100px;
  
  background-color: var(--white);
  color: black;
`