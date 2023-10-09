import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg';
import { useNavigate } from 'react-router-dom';
import axios, { BASE_URL } from '../../api/apiController';

import LendModal from '../../components/TransferModal/LendModal';
import BorrowModal from '../../components/TransferModal/BorrowModal';
import PasswordModal from '../../components/Password/PasswordModal';
import ShareModal from '../../components/ShareModal/ShareModal';

const FinalContract = (props) => {
  const userButtons = JSON.parse(localStorage.getItem('userButtonsLocal'));
  const contractDetail = JSON.parse(localStorage.getItem('toSendLocal'));
  const [userInfo, setUserInfo] = useState(null)
  const [contractID, setContractID] = useState(null)

  useEffect(() => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setUserInfo(res.data)
    })
    .catch((err) => {
      // console.log(err);
    })
  }, []);

  const buttonText =
    userButtons[0] == 'iLend'
      ? '송금하고 공유하기'
      : '자동이체 등록하고 공유하기';
  const signatureURL = localStorage.getItem('userSignature');
  const navigate = useNavigate();

  var date = new Date();
  var borrowedYear = date.getFullYear();
  var borrowedMonth = ('0' + (1 + date.getMonth())).slice(-2);
  var borrowedDay = ('0' + date.getDate()).slice(-2);
  var borrowedDate = borrowedYear + borrowedMonth + borrowedDay;
  var isPWDCorrect = JSON.parse(localStorage.getItem('isPWDCorrect') || null);
  const withoutAuto = {
    lesse_id: null,
    lessor_id: null,
    start_date: '',
    due_date: '',
    amount: contractDetail.loanAmount,
    rate: contractDetail.interestRate,
    use_auto_transfer: "N"
  };
  var contract_id = 0

  function num2han(num) {
    num = parseInt((num + '').replace(/[^0-9]/g, ''), 10) + ''; // 숫자/문자/돈 을 숫자만 있는 문자열로 변환
    if (num == '0') return '영';
    var number = ['영', '일', '이', '삼', '사', '오', '육', '칠', '팔', '구'];
    var unit = ['', '만', '억', '조'];
    var smallUnit = ['천', '백', '십', ''];
    var result = []; //변환된 값을 저장할 배열
    var unitCnt = Math.ceil(num.length / 4); //단위 갯수. 숫자 10000은 일단위와 만단위 2개이다.
    num = num.padStart(unitCnt * 4, '0'); //4자리 값이 되도록 0을 채운다
    var regexp = /[\w\W]{4}/g; //4자리 단위로 숫자 분리
    var array = num.match(regexp);
    //낮은 자릿수에서 높은 자릿수 순으로 값을 만든다(그래야 자릿수 계산이 편하다)
    for (var i = array.length - 1, unitCnt = 0; i >= 0; i--, unitCnt++) {
      var hanValue = _makeHan(array[i]); //한글로 변환된 숫자
      if (hanValue == '')
        //값이 없을땐 해당 단위의 값이 모두 0이란 뜻.
        continue;
      result.unshift(hanValue + unit[unitCnt]); //unshift는 항상 배열의 앞에 넣는다.
    }
    //여기로 들어오는 값은 무조건 네자리이다. 1234 -> 일천이백삼십사
    function _makeHan(text) {
      var str = '';
      for (var i = 0; i < text.length; i++) {
        var num = text[i];
        if (num == '0')
          //0은 읽지 않는다
          continue;
        str += number[num] + smallUnit[i];
      }
      return str;
    }
    return result.join('');
  }

  var koreanNumber = num2han(contractDetail.loanAmount);

  // 모달 띄우기
  const [modalOpen, setModalOpen] = useState(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState(false);
  const [shareModalOpen, setShareModalOpen] = useState(false);

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const transferClicked = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const start_date = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;

    const due = contractDetail.maturityDate;
    const due_date = `${due.slice(0, 4)}-${due.slice(4, 6)}-${due.slice(
      6,
      8,
    )}T${hours}:${minutes}:${seconds}`;
    if (userButtons[0] == 'iLend') {
      withoutAuto.lessor_id = userInfo.user_id
      withoutAuto.lesse_id = null

    } else {
      withoutAuto.lesse_id = userInfo.user_id
      withoutAuto.lessor_id = null
      const isAuto = localStorage.getItem('isAuto');
      if (isAuto == 'true') {
        withoutAuto.use_auto_transfer = "Y"
      }
    }

    withoutAuto.start_date = start_date
    withoutAuto.due_date = due_date

    setModalOpen(false);
    setPasswordModalOpen(true); // 패스워드 모달 열기
  };

  const sendTempContract = async () => {
    // console.log(withoutAuto)
    try {
      await axios.post(
        `${BASE_URL}/contracts`,
        {
          lessee_id: withoutAuto.lesse_id,
          lessor_id: withoutAuto.lessor_id,
          start_date: withoutAuto.start_date,
          due_date: withoutAuto.due_date,
          amount : withoutAuto.amount,
          amount_in_korean: koreanNumber,
          rate : withoutAuto.rate,
          status :"BEFORE_EXECUTION",
          pdf_path : "",
          use_auto_transfer: withoutAuto.use_auto_transfer
        }
        ,
        {
          headers: { Authorization: localStorage.getItem('access_token') },
        },
      )
      .then((res) => {
        setContractID(res.data.contract_id)
        // console.log(res.data)
      })
    } catch (error) {
      console.error('서버 요청 실패:', error);
    }
  };


  const passwordClicked = () => {
    setModalOpen(false);
    transferClicked();
    setPasswordModalOpen(false);
    sendTempContract();
    setShareModalOpen(true); // 패스워드 모달 열기
  };

  const closePasswordModal = () => {
    setPasswordModalOpen(false);
  };

  const closeShareModal = () => {
    setShareModalOpen(false);
  };

  
  const shareKakao = () => {
    
    Kakao.Share.sendDefault({
      objectType: 'feed',
      content: {
        title: '휙, 편리한 차용증 관리📝',
        description: `${userInfo.name}님이 보낸 차용증이 도착했어요`,
        imageUrl:
        'https://github-production-user-asset-6210df.s3.amazonaws.com/55385934/273015907-b422779c-530e-4c95-8cc1-9e1867dce437.png',
        link: {
          // [내 애플리케이션] > [플랫폼] 에서 등록한 사이트 도메인과 일치해야 함
          mobileWebUrl: `http://h-uick.com/share/${contractID}`,
          webUrl: `http://h-uick.com/share/${contractID}`,
        },
      },
    });
    
  };

  const closeAndShare = () => {
    var isPWDCorrect = JSON.parse(localStorage.getItem('isPWDCorrect'));
    // console.log(userInfo)
    shareKakao();
    setShareModalOpen(false);
  };
  
  return (
    <>
      <ChatbotButton>
        <ContractPaper>
          <br />
          <br />
          <Title>
            <span>
              차&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;증
            </span>
          </Title>
          <br />
          <br />
          <br />
          <Body>
            &#8361;&nbsp;
            {contractDetail.loanAmount
              .toString()
              .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}{' '}
            &nbsp;( {koreanNumber} 원 )
          </Body>
          <br />
          <Body>
            위 금액을 채무자가 채권자로부터{' '}
            <TextBold>
              {borrowedDate.slice(0, 4)}년 {borrowedDate.slice(4, 6)}월{' '}
              {borrowedDate.slice(6, 8)}일
            </TextBold>
            에 빌립니다. 이자는{' '}
            <TextBold>연 {contractDetail.interestRate}%</TextBold>로 하여 매달{' '}
            <TextBold>{borrowedDate.slice(6, 8)}일</TextBold>에 갚겠으며, 원금은{' '}
            <TextBold>
              {contractDetail.maturityDate.slice(0, 4)}년{' '}
              {contractDetail.maturityDate.slice(4, 6)}월{' '}
              {contractDetail.maturityDate.slice(6, 8)}일
            </TextBold>
            까지 채권자에게 갚겠습니다.
          </Body>
          <br />
          <ContractTable>
            <FirstCol>
              <div>채무자</div>
              <div></div>
            </FirstCol>
            <SecondCol>
              <SecondColContext>
                <span>이 름</span>
              </SecondColContext>
              <SecondColContext>
                <span>주 소</span>
              </SecondColContext>
              <SecondColContext>주 민 등 록 번 호</SecondColContext>
              <SecondColContext>전 화 번 호</SecondColContext>
            </SecondCol>
            <ThirdCol>
              <span>:</span>
              <span>:</span>
              <span>:</span>
              <span>:</span>
            </ThirdCol>
            <FourthCol>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? '' : userInfo?.name }
              </FourthColContext>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? '' : userInfo?.address}
              </FourthColContext>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? '' : `${userInfo?.rrn.slice(0,6)}-${userInfo?.rrn.slice(6,13)}`}
              </FourthColContext>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? '' : `${userInfo?.phone_number.slice(0,3)}-${userInfo?.phone_number.slice(3,7)}-${userInfo?.phone_number.slice(7,11)}`}
              </FourthColContext>
            </FourthCol>
            <FifthCol>( 서 명 )</FifthCol>
          </ContractTable>

          <ContractTable>
            <FirstCol>채권자</FirstCol>
            <SecondCol>
              <SecondColContext>
                <span>이 름</span>
              </SecondColContext>
              <SecondColContext>
                <span>주 소</span>
              </SecondColContext>
            </SecondCol>
            <ThirdCol>
              <span>:</span>
              <span>:</span>
            </ThirdCol>
            <FourthCol>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? userInfo?.name : ''}
              </FourthColContext>
              <FourthColContext>
                {userButtons[0] == 'iLend' ? userInfo?.address : ''}
              </FourthColContext>
            </FourthCol>
            <FifthCol>( 서 명 )</FifthCol>
          </ContractTable>
          <Today>
            {borrowedDate.slice(0, 4)}년 {borrowedDate.slice(4, 6)}월{' '}
            {borrowedDate.slice(6, 8)}일
          </Today>
          <br />
        </ContractPaper>
      </ChatbotButton>
      <ChatbotButtonShare>
        <ShareText onClick={() => {showModal();}}>
          {buttonText}
        </ShareText>
        <RightArrowResized
          onClick={() => {
            showModal();
          }}
        />
        {modalOpen && userButtons[0] == 'iLend' ? (
          <LendModal
            closeModal={closeModal}
            transferClicked={transferClicked}
            balance={userInfo?.account_info.balance}
          />
        ) : null}
        {modalOpen && userButtons[0] == 'iBorrow' ? (
          <BorrowModal
            closeModal={closeModal}
            transferClicked={transferClicked}
          />
        ) : null}
        {passwordModalOpen ? (
          <PasswordModal
            closePasswordModal={closePasswordModal}
            passwordClicked={passwordClicked}
            userPassword={userInfo?.password}
          />
        ) : null}
        {shareModalOpen ? (
          <ShareModal
            closeShareModal={closeShareModal}
            closeAndShare={closeAndShare}/>
        ) : null}
      </ChatbotButtonShare>
      {isPWDCorrect ? (
        <ToFirst>
          차용증 공유가 완료되었습니다.
          <br />
          다른 차용증을 작성하려면
          <br />
          '처음으로'를 입력해주세요
        </ToFirst>
      ) : null}
    </>
  );
};

export default FinalContract;

const ChatbotButton = styled.span`
  position: relative;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  color: var(--white);
  text-align: left;
  padding-left: 10px;
  padding-right: 11px;
  margin-right: 12px;
  /* margin-right: 12px; */
  line-height: 19px;
  background-color: var(--huick-blue);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
`;

const ChatbotButtonShare = styled(ChatbotButton)`
  position: relative;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  color: var(--black);
  text-align: left;
  padding-left: 10px;
  padding-right: 11px;
  margin-right: 12px;
  line-height: 19px;
  background-color: #f1f1f1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  width: 52%;
`;

const RightArrowResized = styled(RightArrow)`
  position: absolute;
  right: 12px;
  margin-top: 1.2px;
  height: 11px;
  width: auto;
`;

const ContractPaper = styled.div`
  position: relative;
  width: 100%;
  height: auto;
  padding: 20px;
  background-color: var(--white);
  color: black;
  font-size: 10.5px;
`;

const Title = styled.div`
  position: relative;
  width: 100%;
  font-size: 18px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  height: auto;
`;

const Body = styled.div``;

const TextBold = styled.span`
  font-weight: bold;
`;

const ContractTable = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  line-height: 1.65;
  font-size: 10px;
  margin-bottom: 24px;
  zoom: 0.87;
`;

const FirstCol = styled.div`
  position: relative;
  width: 16%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const SecondCol = styled.div`
  position: relative;
  width: 22%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const SecondColContext = styled.span`
  position: relative;
  width: 100%;
  text-align: justify;
  line-height: 0.46;
  &:before {
    content: '';
    display: inline-block;
    width: 100%;
  }

  &:after {
    content: '';
    display: inline-block;
    width: 100%;
  }
`;

const ThirdCol = styled.div`
  position: relative;
  width: 5%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const FourthCol = styled.div`
  position: relative;
  width: 55%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
`;

const FourthColContext = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  /* overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap; */
`;

const FifthCol = styled.div`
  position: absolute;
  width: 14%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  right: 0px;
  top: 0px;
`;

const Signature = styled.img`
  position: absolute;
  top: 2px;
  width: 100%;
`;

const Today = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  margin-top: 44px;
`;

const ToFirst = styled(ChatbotButton)`
  justify-content: flex-start;
  width: 180px;
`;

const ShareText = styled.div`
  position: relative;
  width: 90%;
`