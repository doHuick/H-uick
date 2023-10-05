import React, { useReducer } from 'react';

const userChat = [];
localStorage.setItem('tempContractLocal', '');

const toSend = {
  loanAmount: 0,
  maturityDate: '',
  interestRate: 0,
};

var date = new Date();
var borrowedYear = date.getFullYear();
var borrowedMonth = ('0' + (1 + date.getMonth())).slice(-2);
var borrowedDay = ('0' + date.getDate()).slice(-2);

var borrowedDate = borrowedYear + borrowedMonth + borrowedDay;

const MessageParser = ({ children, actions, props }) => {
  const parse = (message) => {
    const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
    userChat.push(message);
    console.log(userChat)

    if (message.includes('처음으로')) {
      userChat.length = 0;
      localStorage.setItem('tempContractLocal', '');
      localStorage.setItem('toSendLocal', '');
      localStorage.setItem('userKakaoCaptureURL', '');
      localStorage.setItem('tempContractLocal', '');
      localStorage.setItem('userButtonsLocal', '[]');

      actions.toFirst();

    }

    
    
    // 금액
    if (userChat.length == 1 && !userButtons[0] && userButtons.length < 3 && !(userButtons.includes('질문하기'))) {
      userChat.pop();
      actions.startAfterButton();
    } else if (userChat.length == 1 && userButtons[0] && userButtons.length < 3) {
      message = message.replace(/,/g, '');
      message = message.replace('원', '');
      
      if (message <= 0) {
        userChat.pop();
        actions.lessThanZero();
      } else if (!isNaN(message)) {
        localStorage.setItem('tempContractLocal', '');
        toSend.loanAmount = message;
        actions.endDate();
      } else {
        userChat.pop();
        actions.priceFailed();
      }
    }
    
    // 만기일자
    if (userChat.length == 2 && userButtons.length < 3 && !(userButtons.includes('질문하기'))) {
      var year = '';
      var month = '';
      var day = '';
      var combined = '';
      const letters = ['/', '-', '.', ','];
      
      if (!isNaN(message)) {
        if (message.length == 6) {
          combined = '20' + message;
        } else if (message.length == 8) {
          combined = message;
        }
        year = combined.slice(0, 4);
        month = combined.slice(4, 6);
        day = combined.slice(6, 8);
      } else {
        if (
          message.includes('년') ||
          message.includes('월') ||
          message.includes('일')
          ) {
            message = message.replace(/ /gi, '');
            message = message.replace('년', '/');
            message = message.replace('월', '/');
            message = message.replace('일', '');
          }
          
          for (var i = 0; i < letters.length; i++) {
            if (message.includes(letters[i])) {
              // 연도 설정
              var first = message.indexOf(letters[i]);
              
              if (first == 2) {
                year = '20' + message.substr(0, 2);
              } else if (first == 4) {
              year = message.substr(0, 4);
            }
            message = message.substr(first + 1, message.length);
            
            // 월 설정
            var second = message.indexOf(letters[i]);
            if (second == 1) {
              month = '0' + message.substr(0, 1);
            } else if (second == 2) {
              month = message.substr(0, 2);
            }
            
            message = message.substr(second + 1, message.length);
            
            // 일 설정
            if (message.length == 1) {
              day = '0' + message;
            } else if (message.length == 2) {
              day = message;
            }
            
            break;
          }
        }
      }
      combined = year + month + day;
      if (
        month < 1 ||
        month > 12 ||
        day < 1 ||
        day > 31 ||
        combined.length != 8
        ) {
          actions.dateFailed();
          userChat.pop();
        } else if (borrowedDate >= combined) {
          userChat.pop();
          actions.endDateFailed();
        } else {
        actions.annualRate();
        toSend.maturityDate = combined;
      }
    }
    
    // 이자율
    if (userChat.length == 3 && userButtons.length < 3 && !(userButtons.includes('질문하기'))) {
      const letters = ['%', '퍼센트', '퍼'];
      for (var i = 0; i < letters.length; i++) {
        message = message.replace(letters[i], '');
      }
      if (!isNaN(message)) {
        if (message > 20) {
          userChat.pop();
          actions.maxRate();
        } else {
          toSend.interestRate = message;
          localStorage.setItem('tempContractLocal', JSON.stringify(toSend));
          actions.contractConfirm(toSend, borrowedDate);
        }
      } else {
        userChat.pop();
        actions.rateFailed();
      }
    }
    
    if (message.includes('돌아가기')) {
      userChat.pop();
      // console.log(userChat)
      actions.contractConfirm(toSend, borrowedDate);
    }
    

    if (userButtons.includes('질문하기')) {
      console.log('지피티질문')
      actions.handleGPTResponse(message)
      userButtons.pop();
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      userChat.pop();
      userChat.pop();

    }

    if (message.includes('질문하기')) {
      userButtons.push('질문하기')
      localStorage.setItem("userButtonsLocal", JSON.stringify(userButtons))
      actions.handleAsk();
    }


    // 수정
    if (userButtons[2] == 'handleConfirm') {
      const toSend2 = JSON.parse(localStorage.getItem('tempContractLocal'));
      
      // 금액수정
      if (userButtons[3] == 'handlePriceEdit') {
        message = message.replace(/,/g, '');
        message = message.replace('원', '');
        
        if (message <= 0) {
          userChat.pop();
          actions.lessThanZero();
        } else if (!isNaN(message)) {
          toSend2.loanAmount = message;
          userButtons.pop();
          userButtons.pop();
          userChat.pop();
          localStorage.setItem('userButtonsLocal', JSON.stringify(userButtons));
          localStorage.setItem('tempContractLocal', JSON.stringify(toSend2));
          actions.contractConfirm(toSend2, borrowedDate);
        } else {
          userChat.pop();
          actions.priceFailed();
        }
        // 날짜 수정
      } else if (userButtons[3] == 'handleDateEdit') {
        var year = '';
        var month = '';
        var day = '';
        var combined = '';
        const letters = ['/', '-', '.', ','];

        if (!isNaN(message)) {
          if (message.length == 6) {
            combined = '20' + message;
          } else if (message.length == 8) {
            combined = message;
          }
          year = combined.slice(0, 4);
          month = combined.slice(4, 6);
          day = combined.slice(6, 8);
        } else {
          if (
            message.includes('년') ||
            message.includes('월') ||
            message.includes('일')
          ) {
            message = message.replace(/ /gi, '');
            message = message.replace('년', '/');
            message = message.replace('월', '/');
            message = message.replace('일', '');
          }

          for (var i = 0; i < letters.length; i++) {
            if (message.includes(letters[i])) {
              // 연도 설정
              var first = message.indexOf(letters[i]);

              if (first == 2) {
                year = '20' + message.substr(0, 2);
              } else if (first == 4) {
                year = message.substr(0, 4);
              }
              message = message.substr(first + 1, message.length);

              // 월 설정
              var second = message.indexOf(letters[i]);
              if (second == 1) {
                month = '0' + message.substr(0, 1);
              } else if (second == 2) {
                month = message.substr(0, 2);
              }

              message = message.substr(second + 1, message.length);

              // 일 설정
              if (message.length == 1) {
                day = '0' + message;
              } else if (message.length == 2) {
                day = message;
              }

              break;
            }
          }
        }
        combined = year + month + day;
        if (
          month < 1 ||
          month > 12 ||
          day < 1 ||
          day > 31 ||
          combined.length != 8
        ) {
          actions.dateFailed();
          userChat.pop();
        } else if (borrowedDate >= combined) {
          userChat.pop();
          actions.endDateFailed();
        } else {
          toSend2.maturityDate = combined;
          userButtons.pop();
          userButtons.pop();
          userChat.pop();
          localStorage.setItem('userButtonsLocal', JSON.stringify(userButtons));
          localStorage.setItem('tempContractLocal', JSON.stringify(toSend2));
          actions.contractConfirm(toSend2, borrowedDate);
        }
        // 이자율 수정
      } else if (userButtons[3] == 'handleRateEdit') {
        const letters = ['%', '퍼센트', '퍼'];
        for (var i = 0; i < letters.length; i++) {
          message = message.replace(letters[i], '');
        }
        if (!isNaN(message)) {
          if (message > 20) {
            userChat.pop();
            actions.maxRate();
          } else {
            toSend2.interestRate = message;
            userButtons.pop();
            userButtons.pop();
            userChat.pop();
            localStorage.setItem(
              'userButtonsLocal',
              JSON.stringify(userButtons),
            );
            localStorage.setItem('tempContractLocal', JSON.stringify(toSend2));
            actions.contractConfirm(toSend2, borrowedDate);
          }
        } else {
          userChat.pop();
          actions.rateFailed();
        }
      }
    }
  };

  return (
    <div>
      {React.Children.map(children, (child) => {
        return React.cloneElement(child, {
          parse: parse,
          actions,
        });
      })}
    </div>
  );
};

export default MessageParser;
