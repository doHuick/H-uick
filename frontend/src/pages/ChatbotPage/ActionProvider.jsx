import React from 'react';
import { createClientMessage } from 'react-chatbot-kit';
import axios from 'axios';

var isLender = true
localStorage.setItem("toSendLocal", '')
localStorage.setItem("userKakaoCaptureURL", '')
localStorage.setItem("isPWDCorrect", 'false')

const ActionProvider = ({ createChatBotMessage, setState, children }) => {
  // 처음으로
  const startAfterButton = () => {
    const botMessage = createChatBotMessage(
      "다음 항목을 먼저 선택해주세요", { widget: 'lendborrowbutton'}
    );

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  const toFirst = () => {
    localStorage.setItem("toSendLocal", '')
    localStorage.setItem("userKakaoCaptureURL", '')
    localStorage.setItem("tempContractLocal", '')
    localStorage.setItem("userButtonsLocal", '[]')
    localStorage.setItem("isPWDCorrect", 'false')
    const botMessage = createChatBotMessage(
      "휙봇을 통해 간편하게\n차용증을 작성할 수 있습니다\n\n다음의 항목들에 하나씩 답변해주세요!",
    );
    const botMessageSecond = createChatBotMessage('가장 중요한 것부터 시작할게요', {
      widget: 'lendborrowbutton',
    });

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage, botMessageSecond],
    }));
  };

  // 빌려줌
  const handleILend = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[0]) {
      isLender = true;
      userButtons.push('iLend')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('빌려주기');
      const botMessage = createChatBotMessage(
        '작성 방식을 선택해주세요', {widget: 'chatkakaobutton'}
        );
        // const botMessagesecond = createChatBotMessage('얼마를 빌려주시나요?', {
        //   delay: 900,
        // });
        
        setState((prev) => ({
          ...prev,
          messages: [...prev.messages, clientmessage, botMessage],
        }));
        
    }
  };

  // 빌림
  const handleIBorrow = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[0]) {
      isLender = false
      userButtons.push('iBorrow')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('빌리기');
      const botMessage = createChatBotMessage(
        '작성 방식을 선택해주세요', {widget: 'chatkakaobutton'}
        );
        // const botMessagesecond = createChatBotMessage('얼마를 빌려주시나요?', {
        //   delay: 900,
        // });
        
        setState((prev) => ({
          ...prev,
          messages: [...prev.messages, clientmessage, botMessage],
        }));
        
    }
  };

  // 대화형 작성
  const handleChatCreate = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[1]) {
      userButtons.push('createwithChat')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('휙봇과 대화로 작성');
      const botMessage = createChatBotMessage('금액을 말씀해주세요', { delay: 900 }
        );
        // const botMessagesecond = createChatBotMessage('얼마를 빌려주시나요?', {
        //   delay: 900,
        // });
        
        setState((prev) => ({
          ...prev,
          messages: [...prev.messages, clientmessage, botMessage],
        }));
        
    }
  };

  // 카카오캡쳐 작성
  const handleKakaoCreate = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[1]) {
      userButtons.push('createwithChat')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      localStorage.setItem("kakaoUploaded", 'false')
      const clientmessage = createClientMessage('메신저 캡처로 작성', { widget: 'userimageupload', delay: 900 });
        
      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, clientmessage ],
      }));
        
    }
  };

  // 카카오캡쳐 업로드
  const handleUpload = () => {
    if (localStorage.getItem('kakaoUploaded') == 'false') {
      const botMessage = createChatBotMessage('이미지를 분석한 내용으로 만들어드릴게요');
      // 여기서 이미지 보내고 내용 받기
      const toSend = JSON.parse(localStorage.getItem('tempContractLocal'));

      // 더미데이터
      // const toSend = {
      //   loanAmount: 120,
      //   maturityDate: '20240101',
      //   interestRate: 12,
      // };
      // localStorage.setItem("tempContractLocal", JSON.stringify(toSend))



      var date = new Date();
      var borrowedYear = date.getFullYear();
      var borrowedMonth = ('0' + (1 + date.getMonth())).slice(-2);
      var borrowedDay = ('0' + date.getDate()).slice(-2);
      var borrowedDate = borrowedYear + borrowedMonth + borrowedDay;

      localStorage.setItem("kakaoUploaded", 'true')
      
      const botMessageSecond = createChatBotMessage(
        `금액: ${toSend.loanAmount
          .toString()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원
      시작일: ${borrowedDate.slice(0, 4)}년 ${borrowedDate.slice(
        4,
        6,
      )}월 ${borrowedDate.slice(6, 8)}일
      만기일: ${toSend.maturityDate.slice(0, 4)}년 ${toSend.maturityDate.slice(
        4,
        6,
      )}월 ${toSend.maturityDate.slice(6, 8)}일
      이자율: ${toSend.interestRate}%\n
      내용이 맞는지 확인하고\n궁금한 점이 있다면 '질문하기'를 보내보세요`,
        { widget: 'confirmbutton', delay: 900 },
      );;

      const toSendContract = { isLender: isLender, loanAmount: toSend.loanAmount, maturityDate: toSend.maturityDate, interestRate: toSend.interestRate }
      localStorage.setItem("toSendLocal", JSON.stringify(toSendContract))
      // 여기서 이미지 POST, 정보 GET
      
      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, botMessage, botMessageSecond],
      }));
    }

    
    
  };

  // 금액 형식 맞지않음
  const priceFailed = () => {
    const botMessage = createChatBotMessage('금액을 숫자로 입력해주세요');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 금액 0이하
  const lessThanZero = () => {
    const botMessage = createChatBotMessage('금액은 0이상이어야 합니다');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 만기일
  const endDate = () => {
    const botMessage = createChatBotMessage('갚는 날짜는 언제로 할까요?');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 날짜 형식 안맞음
  const dateFailed = () => {
    const botMessage = createChatBotMessage('정확한 날짜를 입력해주세요');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 만기일이 오늘 이하임
  const endDateFailed = () => {
    const botMessage = createChatBotMessage(
      '갚는 날짜는 시작일 이후여야 합니다\n만기일을 다시 입력해주세요',
    );

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 이자율
  const annualRate = () => {
    const botMessage = createChatBotMessage('이자율은 몇 퍼센트로 하시겠습니까?');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 이자율 형식 안맞음
  const maxRate = () => {
    const botMessage = createChatBotMessage('법정 최고 이자율은 20%입니다');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 이자율 형식 안맞음
  const rateFailed = () => {
    const botMessage = createChatBotMessage('이자율을 숫자로 입력해주세요');

    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };

  // 내용확인
  const contractConfirm = (toSend, borrowedDate) => {
    const botMessage = createChatBotMessage(
      `금액: ${toSend.loanAmount
        .toString()
        .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원
    시작일: ${borrowedDate.slice(0, 4)}년 ${borrowedDate.slice(
      4,
      6,
    )}월 ${borrowedDate.slice(6, 8)}일
    만기일: ${toSend.maturityDate.slice(0, 4)}년 ${toSend.maturityDate.slice(
      4,
      6,
    )}월 ${toSend.maturityDate.slice(6, 8)}일
    이자율: ${toSend.interestRate}%\n
    내용이 맞는지 확인하고\n궁금한 점이 있다면 '질문하기'를 보내보세요`,
      { widget: 'confirmbutton' },
    );

    const toSendContract = { isLender: isLender, loanAmount: toSend.loanAmount, maturityDate: toSend.maturityDate, interestRate: toSend.interestRate }
    localStorage.setItem("toSendLocal", JSON.stringify(toSendContract))
    
    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage],
    }));
  };
  
  // 내용 맞음
  const handleYesConfirmButton = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
      if (!userButtons[2]) {
        userButtons.push('handleConfirm')
        localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
        const clientmessage = createClientMessage('맞아요');
        const botMessage = createChatBotMessage('위의 내용으로 만들어드릴게요');
        const botMessageSecond = createChatBotMessage(
        '작성된 임시 차용증을 확인해보세요',
        { widget: 'finalconfirmbutton', delay: 1600 },
        );
        
        setState((prev) => ({
          ...prev,
          messages: [...prev.messages, clientmessage, botMessage, botMessageSecond],
        }));
      }
    };
    
    // 내용 틀림
    const handleNoConfirmButton = () => {
      const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
      if (!userButtons[2]) {
        userButtons.push('handleConfirm')
        localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
        const clientmessage = createClientMessage('아니에요');
        const botMessage = createChatBotMessage(
          "수정하고 싶은 항목을 선택해주세요", { widget: 'editbutton' }
        );
    
        setState((prev) => ({
          ...prev,
          messages: [...prev.messages, clientmessage, botMessage],
        }));
      }
    };


    // 금액 수정
  const handlePriceEdit = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[3]) {
      userButtons.push('handlePriceEdit')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('금액');
      const botMessage = createChatBotMessage('수정할 금액을 입력해주세요', { delay: 900 });

      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, clientmessage, botMessage ],
      }));
    }
  };

  // 만기일 수정
  const handleDateEdit = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[3]) {
      userButtons.push('handleDateEdit')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('갚는 날짜');
      const botMessage = createChatBotMessage('수정할 날짜를 입력해주세요', { delay: 900 });

      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, clientmessage, botMessage ],
      }));
    }
  };

  // 이자율 수정
  const handleRateEdit = () => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    if (!userButtons[3]) {
      userButtons.push('handleRateEdit')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      const clientmessage = createClientMessage('이자율');
      const botMessage = createChatBotMessage('수정할 이자율을 입력해주세요', { delay: 900 });

      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, clientmessage, botMessage ],
      }));
    }
  };

  const handleShare = () => {
      const botMessage = createChatBotMessage('작성이 완료되었습니다', {});

      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, botMessage ],
      }));

  };

  const handleAsk = () => {
      const botMessage = createChatBotMessage('휙봇에게 질문을 해주세요');

      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, botMessage ],
      }));
      
    };

    const handleAnswer = (response) => {
      const botMessage = createChatBotMessage(`${response}`);
      const botMessageSecond = createChatBotMessage("추가 질문이 있다면 '질문하기'를,\n없으시다면 '돌아가기'를 입력해주세요");
      
      setState((prev) => ({
        ...prev,
        messages: [...prev.messages, botMessage, botMessageSecond ],
      }));
    }

  const handleGPTResponse = (message) => {
    const toSend = JSON.parse(localStorage.getItem('tempContractLocal'));
    // console.log(message)
    const botMessage = createChatBotMessage('잠시 기다려주세요');
    const askGPT = {
      user_id: 1,
      contract_tmp_key: "11",
      contract_info: {
                          loanAmount: toSend?.loanAmount,
                          amountInKorean: null,
                          interestRate: toSend?.interestRate,
                          maturityDate: toSend?.maturityDate,
  
                        },
      chat: message,
    }

    axios.post(
      `https://h-uick.com/ai/contracts/assist?user_id=1&contract_tmp_key=11`,
      askGPT,
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .then((res) => {
        handleAnswer(res.data.answer)
      })
      
    setState((prev) => ({
      ...prev,
      messages: [...prev.messages, botMessage ],
    }));
  };


  // Put the handleHello function in the actions object to pass to the MessageParser
  return (
    <div>
      {React.Children.map(children, (child) => {
        return React.cloneElement(child, {
          actions: {
            startAfterButton,
            toFirst,
            handleILend,
            handleIBorrow,
            handleChatCreate,
            handleKakaoCreate,
            handleUpload,
            lessThanZero,
            priceFailed,
            dateFailed,
            endDate,
            endDateFailed,
            annualRate,
            maxRate,
            rateFailed,
            contractConfirm,
            handleYesConfirmButton,
            handleNoConfirmButton,
            handlePriceEdit,
            handleDateEdit,
            handleRateEdit,
            handleShare,
            handleAsk,
            handleGPTResponse,
          },
        });
      })}
    </div>
  );
};

export default ActionProvider;
