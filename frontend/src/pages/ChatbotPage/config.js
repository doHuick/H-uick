import { createChatBotMessage } from "react-chatbot-kit";

const config = {
  initialMessages: [
    createChatBotMessage(
      "안녕하세요! 궁금한 내용을 입력해주세요."
    ),
  ],
  botName: "휙봇",
  customStyles: {
    botMessageBox: {
      backgroundColor: '#00B7FE',
    },
    chatButton: {
      backgroundColor: '#00B7FE',
    },
  },

};

export default config;