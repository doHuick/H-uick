import React, { useState } from "react";
import styled from "styled-components";


const ConfirmButton = (props) => {
    const [buttonsVisible, setButtonsVisible] = useState(true);
    const confirmOptions = [
        {
          text: "맞아요",
          handler: props.actionProvider.handleYesConfirmButton,
          id: 1,
        },
        {
          text: "아니에요",
          handler: props.actionProvider.handleNoConfirmButton,
          id: 2
        },
    ]


const buttonsMarkup = confirmOptions.map((option) => (
    <ChatbotButton key={option.id} onClick={
      option.handler
      // setTimeout(() => {
      //   setButtonsVisible(false)
      // }, 1000);
      }>
        {option.text}
    </ChatbotButton>
))

return <div style={{display: 'flex'}}>{buttonsMarkup}</div>

}


export default ConfirmButton

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