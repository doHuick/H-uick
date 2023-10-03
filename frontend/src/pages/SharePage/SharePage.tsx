import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import axios, { BASE_URL } from '../../api/apiController';
import { useNavigate, useParams } from 'react-router-dom';

import HeadBar from '../../components/HeadBar/HeadBar';
import NavBar from '../../components/NavBar/NavBar';
// import { ConfirmButton } from '../../components/Button/Button';
import { MiniConfirmButton } from '../../components/Button/Button';
import BorrowModal from '../../components/TransferModal/BorrowModal';
import SharePasswordModal from '../../components/Password/SharePasswordModal';

interface AccountProps{
  accountId: number,
  accountNumber: string,
  balance: number,
  bankCode: string,
  bankName: string,
  createdTime: string,
}

interface UserInfoProps{
  account_info : AccountProps,
  address: string,
  created_time: string,
  issue_date?: string,
  name: string,
  password: string,
  phone_number: string,
  role: string,
  rrn: string,
  signature_url?: string,
  social_id: string,
  social_type: string,
  user_id: number,
  wallet_address?: string,
  withdrawal_time? : string,
}

interface ContractInfoProps {
  amount: number,
  contract_id : number,
  current_amount: number,
  current_repayment_count: number,
  due_date: string,
  lessee_address: string,
  lessee_id?: number,
  lessee_name: string,
  lessor_address: string,
  lessor_id?: number,
  lessor_name: string,
  lessor_phone_number: string,
  lessor_rrn: string,
  pdf_path: string,
  rate: number,
  repayment_data?: string,
  start_date: string,
  status: string,
  total_repayment_count: number,
  amount_in_korean: string,
}

export default function SharePage() {
  const params = useParams();
  const contractId = params.contractId

  const navigate = useNavigate();

  const [contractInfo, setContractInfo] = useState<ContractInfoProps>()
  const [userInfo, setUserInfo] = useState<UserInfoProps>()
  const [modalOpen, setModalOpen] = useState(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState(false);


  const closeModal = () => {
    setModalOpen(false);
  };

  const transferClicked = () => {
    setModalOpen(false);
    setPasswordModalOpen(true); // 패스워드 모달 열기
  }

  
  
  
  useEffect(() => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setUserInfo(res.data)
      // console.log(res.data)
    })
    .catch((err) => {
      navigate('/login');
    })
  }, []);
  
  useEffect(() => {
    axios.get(`${BASE_URL}/contracts/${contractId}`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setContractInfo(res.data)
      // console.log(res.data)
    })
    .catch((err) => {
      console.log(err);
      navigate('/404')
    })
  }, []);
  
  // 차용증 완성시키기
  const executeContract = () => {
    console.log(contractId)
    console.log(contractInfo?.lessee_id)
    console.log(userInfo?.user_id)
    // 지금 유저가 빌려주는사람
    if (contractInfo?.lessor_id == null) {
      axios.patch(
        `${BASE_URL}/contracts/${contractId}`,
        {
          lessee_id: contractInfo?.lessee_id,
          lessor_id: userInfo?.user_id,
          start_date: null,
          due_date: null,
          amount: null,
          amount_in_korean: null,
          rate: null,
          status: "EXECUTION_COMPLETED",
          pdf_path: null,
          use_auto_transfer: null
        }
        ,
        {
          headers: { Authorization: localStorage.getItem('access_token') },
        },
        )
        .then((res) => {
          console.log(res)
          location.reload();
        })
      } else if (contractInfo?.lessee_id == null) {
        setModalOpen(true);
      }
      
      
    };
    
    const terminateContract = async () => {
      try {
        await axios.patch(
          `${BASE_URL}/contracts/status/${contractId}`,
          
          {
            headers: { Authorization: localStorage.getItem('access_token') },
            status: "TERMINATION"
          },
          )
          .then((res) => {
            console.log(res)
            location.reload();
          })
        } catch (error) {
          console.error('서버 요청 실패:', error);
        }
      };
      
      const closePasswordModal = () => {
        setPasswordModalOpen(false);
        const isAuto = localStorage.getItem('isAuto');
        var auto_transfer = "N"
        if (isAuto == 'true') {
          auto_transfer = "Y"
        }
        console.log(auto_transfer)
        // 지금 유저가 빌리는 사람
        axios.patch(
          `${BASE_URL}/contracts/${contractId}`,
          {
            lessee_id: userInfo?.user_id,
            lessor_id: contractInfo?.lessor_id,
            start_date: null,
            due_date: null,
            amount: null,
            amount_in_korean: null,
            rate: null,
            status: "EXECUTION_COMPLETED",
            pdf_path: null,
            use_auto_transfer: auto_transfer
          }
          ,
          {
            headers: { Authorization: localStorage.getItem('access_token') },
          },
        )
        .then((res) => {
          console.log(res)
          location.reload();
        })
      };

      return (
        <Main>
      <HeadBar pageName={'계약 완료하기'} />
      <TextFrame>
        <TextBoxBold>
          계약 완료 전 차용증을 검토해주세요
        </TextBoxBold>
        <TextBox>
          오기입된 내용이 있다면
        </TextBox>
        <TextBox>
          계약 파기 후 재작성 해주세요
        </TextBox>
      </TextFrame>
  
      <ContractFrame>
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
            {contractInfo?.amount
              .toString()
              .replace(/\B(?=(\d{3})+(?!\d))/g, ',')}{' '}
            &nbsp;(  {contractInfo?.amount_in_korean} 원 )
          </Body>
          <br />
          <Body>
            위 금액을 채무자가 채권자로부터{' '}
            <TextBold>
              {contractInfo?.start_date.slice(0, 4)}년{' '}
              {contractInfo?.start_date.slice(5, 7)}월{' '}
              {contractInfo?.start_date.slice(8, 10)}일
            </TextBold>
            에 빌립니다. 이자는{' '}
            <TextBold>연 {contractInfo?.rate}%</TextBold>로 하여 매달{' '}
            <TextBold>{contractInfo?.start_date.slice(8, 10)}일</TextBold>에 갚겠으며, 원금은{' '}
            <TextBold>
              {contractInfo?.due_date.slice(0, 4)}년{' '}
              {contractInfo?.due_date.slice(5, 7)}월{' '}
              {contractInfo?.due_date.slice(8, 10)}일
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
                {contractInfo?.lessor_id? contractInfo?.lessor_name : userInfo?.name}
              </FourthColContext>
              <FourthColContext>
                {contractInfo?.lessor_id? contractInfo?.lessor_address : userInfo?.address}
              </FourthColContext>
              <FourthColContext>
                {contractInfo?.lessor_id? `${contractInfo?.lessor_rrn.slice(0,6)}-${contractInfo?.lessor_rrn.slice(6,13)}`
                : `${userInfo?.rrn.slice(0,6)}-${userInfo?.rrn.slice(6,13)}`}
              </FourthColContext>
              <FourthColContext>
                {contractInfo?.lessor_id? `${contractInfo?.lessor_phone_number.slice(0,3)}-${contractInfo?.lessor_phone_number.slice(3,7)}-${contractInfo?.lessor_phone_number.slice(7,11)}`
                : `${userInfo?.phone_number.slice(0,3)}-${userInfo?.phone_number.slice(3,7)}-${userInfo?.phone_number.slice(7,11)}`}
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
              {contractInfo?.lessor_id? userInfo?.name : contractInfo?.lessee_name}
              </FourthColContext>
              <FourthColContext>
              {contractInfo?.lessor_id? userInfo?.address : contractInfo?.lessee_address}
              </FourthColContext>
            </FourthCol>
            <FifthCol>( 서 명 )</FifthCol>
          </ContractTable>
          <Today>
            {contractInfo?.start_date.slice(0, 4)}년 {contractInfo?.start_date.slice(5, 7)}월{' '}
            {contractInfo?.start_date.slice(8, 10)}일
          </Today>
          <br />
        </ContractPaper>
      </ContractFrame>
      
      {contractInfo?.status === "BEFORE_EXECUTION" && (
        <>
          <CancelContract onClick={terminateContract}>
            파기하기
          </CancelContract>

          <ButtonFrame>
            <Button onClick={executeContract}>
              확인
            </Button>
          </ButtonFrame>
        </>
      ) }

      {contractInfo?.status === "EXECUTION_COMPLETED" && (
        <ButtonFrame>
          <NoButton>
          이미 체결된 차용증입니다
          </NoButton>
        </ButtonFrame>
      )}

      {contractInfo?.status === "TERMINATION" && (
        <ButtonFrame>
          <NoButton>
          이미 파기된 차용증입니다
          </NoButton>
        </ButtonFrame>
      )}

      {contractInfo?.status === "REPAYMENT_COMPLETED" && (
        <ButtonFrame>
          <NoButton>
          이미 상환 완료된 차용증입니다
          </NoButton>
        </ButtonFrame>
      )}

      {contractInfo?.lessor_id == userInfo?.user_id && contractInfo?.status === "BEFORE_EXECUTION" && (
        <ButtonFrame>
          <NoButton>
            다른사람에게 공유해보세요
          </NoButton>
        </ButtonFrame>
      )}

      {contractInfo?.lessee_id == userInfo?.user_id &&  contractInfo?.status === "BEFORE_EXECUTION" &&(
        <ButtonFrame>
          <NoButton>
            다른사람에게 공유해보세요
          </NoButton>
        </ButtonFrame>
      )}

      {modalOpen ? (
          <BorrowModal
            closeModal={closeModal}
            transferClicked={transferClicked}
          />
        ) : null}

      {passwordModalOpen ? (
          <SharePasswordModal
            closePasswordModal={closePasswordModal}
            userPassword={userInfo?.password}
          />
        ) : null}
    </Main>
  );
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--white);
  overflow-y: scroll;
  overflow-x: hidden;
`;

const TextFrame = styled.div`
  position: relative;
  width: 100%;
  margin-top: 124px;
  margin-left: 28px;

`
const TextBox = styled.div`
  font-size: 16px;
  font-weight: 500;
  color: var(--font-gray);
`

const TextBoxBold = styled.div`
  font-size: 22px;
  font-weight: 700;
  margin-bottom: 12px;
  `
const ContractFrame = styled.div`
  position: relative;
  margin-top: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  left: -25%;
  width: 150%;
  padding-top: 32px;
  padding-bottom: 32px;
  background-color: var(--background);
  box-shadow: inset 0px 0px 12px rgba(0, 0, 0, 0.12);
`

const ContractPaper = styled.div`
  position: relative;
  width: 45%;
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
  margin-top: 5px;
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


const Today = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  margin-top: 44px;
  margin-bottom: 14px;
`;

const CancelContract = styled.div`
  position: relative;
  width: 100%;
  text-align: center;
  font-size: 14px;
  color: var(--font-gray);
  text-decoration: underline;
  margin-top: 20px;
`

const ButtonFrame = styled.div`
  position: absolute;
  width: 100%;
  display: flex;
  justify-content: center;
  bottom: 48px;
`

const Button = styled(MiniConfirmButton)`
  position: relative;
  margin-left: 30px;
  margin-right: 30px;
  height: 46px;
  width: 100%;
  font-size: 18px;
  font-weight: 600;
`

const NoButton = styled(Button)`
  background-color: var(--gray);
`