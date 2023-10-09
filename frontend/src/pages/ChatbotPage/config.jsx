import React from 'react';
import { createChatBotMessage } from 'react-chatbot-kit';
import ConfirmButton from './ConfirmButton';
import LendBorrowButton from './LendBorrowButton';
import EditButton from './EditButton';
import ChatKakaoButton from './ChatKakaoButton';
import UserImageUpload from './UserImageUpload';
import FinalConfirmButton from './FinalContract';

const config = {
  initialMessages: [
    createChatBotMessage(
      `휙봇을 통해 간편하게\n차용증을 작성할 수 있습니다.\n\n다음의 항목들에 하나씩 답변해주세요!`,
    ),
    createChatBotMessage('가장 중요한 것부터 시작할게요', { widget: 'lendborrowbutton' }),
  ],

  customComponents: {
    botAvatar: (props) => (
      <div
        style={{
          position: 'absolute',
          marginTop: '-32px',
          marginLeft: '3px',
          display: 'flex',
          alignItems: 'flex-end',
          zIndex: '0',
        }}
      >
        <img
          style={{ width: '33px', height: '33px', marginTop: '4px' }}
          src="../../../pig-head.png"
          alt=""
        />
        <div style={{marginLeft: '7px', marginBottom: '8px', fontSize: '14.5px', color: 'var(--black'}}>
          H-uick
        </div>
      </div>
    ),
    // botChatMessage: (props) => <MyCustomChatMessage {...props} />,
    userAvatar: (props) => <div></div>,
    // userChatMessage: (props) => <MyCustomUserChatMessage {...props} />
  },
  botName: '휙봇',

  customStyles: {
    botMessageBox: {
      backgroundColor: '#00B7FE',
    },
  },

  widgets: [
    {
      widgetName: 'confirmbutton',
      widgetFunc: (props) => <ConfirmButton {...props} />,
    },
    {
      widgetName: 'lendborrowbutton',
      widgetFunc: (props) => <LendBorrowButton {...props} />,
    },
    {
      widgetName: 'editbutton',
      widgetFunc: (props) => <EditButton {...props} />,
    },
    {
      widgetName: 'chatkakaobutton',
      widgetFunc: (props) => <ChatKakaoButton {...props} />,
    },
    {
      widgetName: 'userimageupload',
      widgetFunc: (props) => <UserImageUpload {...props} />,
    },
    {
      widgetName: 'finalconfirmbutton',
      widgetFunc: (props) => <FinalConfirmButton {...props} />,
    },
  ],
};

export default config;
