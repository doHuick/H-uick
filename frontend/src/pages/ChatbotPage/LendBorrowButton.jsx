import React, { useState } from "react";
import styled from "styled-components";


const LendBorrowButton = (props) => {
    const lendBorrowOptions = [
        {
          text: "빌려주기",
          handler: props.actionProvider.handleILend,
          id: 1,
        },
        {
          text: "빌리기",
          handler: props.actionProvider.handleIBorrow,
          id: 2
        },
    ]


const buttonsMarkup = lendBorrowOptions.map((option) => (
    <ChatbotButton key={option.id} onClick={option.handler}>
        {option.text}
    </ChatbotButton>
))

return <div style={{display: 'flex'}}>{buttonsMarkup}</div>

}


export default LendBorrowButton

const ChatbotButton = styled.div`
  margin-right: 12px;
  width: auto;
  padding-left: 12px;
  padding-right: 12px;
  height: 32px;
  border: none;
  border-radius: 24px;
  background-color: #f1f1f1;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #2B2B2B;
  font-size: 14px;

`