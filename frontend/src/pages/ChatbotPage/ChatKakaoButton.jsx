import React, { useState } from "react";
import styled from "styled-components";


const ChatKakaoButton = (props) => {
    const chatkakaoOptions = [
        {
          text: "휙봇과 대화로 작성",
          handler: props.actionProvider.handleChatCreate,
          id: 1,
        },
        {
          text: "메신저 캡처로 작성",
          handler: props.actionProvider.handleKakaoCreate,
          id: 2
        },
    ]


const buttonsMarkup = chatkakaoOptions.map((option) => (
    <ChatbotButton key={option.id} onClick={option.handler}>
        {option.text}
    </ChatbotButton>
))

return <div style={{display: 'flex'}}>{buttonsMarkup}</div>

}


export default ChatKakaoButton

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