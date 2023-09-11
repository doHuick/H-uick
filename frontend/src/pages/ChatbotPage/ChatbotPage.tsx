import React from 'react'
import styled from 'styled-components'
import Chatbot from "react-chatbot-kit";

import config from "./config"
import MessageParser from './MessageParser';
import ActionProvider from './ActionProvider';
import "react-chatbot-kit/build/main.css"
import "./chatbot.css"

import HeadBar from '../../components/SignModal/HeadBar/HeadBar';

export default function ChatbotPage() {
  return (
    <Main>
      <HeadBar pageName="차용증 작성" bgColor="white" />
      <Chatbot
        config={config}
        messageParser={MessageParser}
        actionProvider={ActionProvider}
        headerText=''
      />
    </Main>
  )
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--white);
  overflow-y: scroll;
`