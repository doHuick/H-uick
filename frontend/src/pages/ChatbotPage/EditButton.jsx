import React, { useState } from 'react';
import styled from 'styled-components';

const EditButton = (props) => {
  const editOptions = [
    {
      text: '금액',
      handler: props.actionProvider.handlePriceEdit,
      id: 1,
    },
    {
      text: '갚는 날짜',
      handler: props.actionProvider.handleDateEdit,
      id: 2,
    },
    {
      text: '이자율',
      handler: props.actionProvider.handleRateEdit,
      id: 3,
    },
  ];

  const buttonsMarkup = editOptions.map((option) => (
    <ChatbotButton
      key={option.id}
      onClick={
        option.handler
        // setTimeout(() => {
        //   setButtonsVisible(false)
        // }, 1000);
      }
    >
      {option.text}
    </ChatbotButton>
  ));

  return <div style={{ display: 'flex' }}>{buttonsMarkup}</div>;
};

export default EditButton;

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
  color: #2b2b2b;
  font-size: 14px;
`;
