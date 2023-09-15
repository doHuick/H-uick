import { createChatBotMessage } from "react-chatbot-kit";

const config = {
  initialMessages: [
    createChatBotMessage(
      "안녕하세요! 궁금한 내용을 입력해주세요."
    ),
  ],

  customComponents: {
    botAvatar: (props) => <div><img src="../../../public/pig-head.png" style={{width:'35px', height: '36px', marginTop:'4px'}} alt="" /></div>,
    // botChatMessage: (props) => <MyCustomChatMessage {...props} />,
    userAvatar: (props) => <div></div>,
    // userChatMessage: (props) => <MyCustomUserChatMessage {...props} />
  },
  
  botName: "휙봇",
  customStyles: {
    botMessageBox: {
      backgroundColor: '#00B7FE',
    },
  },

};

export default config;