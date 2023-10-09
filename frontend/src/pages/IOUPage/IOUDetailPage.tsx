import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Main } from '../../style';
import HeadBar from '../../components/HeadBar/HeadBar';
import NavBar from '../../components/NavBar/NavBar';
import LeseeTransferModal from '../../components/TransferModal/LesseeTransfer';
import SharePasswordModal from '../../components/Password/SharePasswordModal';
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg';
import { useNavigate, useParams } from 'react-router-dom';
import axios, { BASE_URL } from '../../api/apiController';
import toast, { toastConfig } from 'react-simple-toasts';
import 'react-simple-toasts/dist/theme/frosted-glass.css';

interface ContractInfoProps{
  contract_id: number,
  lessee_id: number,
  lessor_id: number,
  lessee_name: string,
  lessee_address: string,
  lessee_wallet_address: string,
  lessor_name: string,
  lessor_address: string,
  lessor_rrn: number,
  lessor_phone_number: string,
  lessor_wallet_address: string,
  total_repayment_count: number,
  current_repayment_count: number,
  start_date: string,
  due_date: string,
  repayment_date: string,
  current_amount: number,
  amount: number,
  amount_in_korean: string,
  rate: number,
  status: string,
  pdf_path: string
  paid_count: number,
  balance: number,
}

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

export default function IOUDetailPage() {
  const params = useParams();
  const contractId = params.contractId

  const navigate = useNavigate()

  const [contractInfo, setContractInfo] = useState<ContractInfoProps>()
  const [userInfo, setUserInfo] = useState<UserInfoProps>()

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    zIndex: 5,
    maxVisibleToasts: 1
  })

  const justTransferred = localStorage.getItem("justTransferred");
  if (justTransferred == "true") {
    toast('송금이 완료되었습니다');
    localStorage.setItem("justTransferred", "false")
  } else {
    localStorage.setItem("justTransferred", 'false')

  }

  

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
  });
  
  useEffect(() => {
    axios.get(`${BASE_URL}/contracts/${contractId}`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setContractInfo(res.data)
      // console.log(res.data)
    })
    .catch(() => {
      // console.log(err)
    })
  }, []);
  
  useEffect(() => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setUserInfo(res.data)
      // console.log(res.data)
    })
    .catch(() => {
    })
  }, []);


  const statusReturn = () => {
    if (contractInfo?.status == "BEFORE_EXECUTION") {
      return "계약 체결 전"
    } else if (contractInfo?.status == "EXECUTION_COMPLETED") {
      return "계약 체결 완료"
    } else if (contractInfo?.status == "REPAYMENT_COMPLETED") {
      return "상환 완료"
    } else if (contractInfo?.status == "TERMINATION") {
      return "계약 파기됨"
    }
  }
  
  const [transferModalOpen, setTransferModalOpen] = useState(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState(false);

  const openTransferModal = () => {
    setTransferModalOpen(true)
  }

  const closeTransferModal = () => {
    setTransferModalOpen(false);
  };
  
  const transferClicked = () => {
    setTransferModalOpen(false);
    setPasswordModalOpen(true);
  }

  const closePasswordModal = () => {
    setPasswordModalOpen(false);
  }

  const passwordCorrect = () => {
    setPasswordModalOpen(false);
    axios.post(
      `${BASE_URL}/banking/repayment`,
      {
        contract_id: contractId
      }
      ,
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      },
      )
      .then(() => {
        // console.log(res)
        localStorage.setItem("justTransferred", 'true')
        location.reload();
      })
  }

  const toPDF = () => {
    navigate(`/pdf/${contractId}`)
  }



  return (
    <Main backgroundColor='var(--white)'>
      <HeadBar pageName="차용증" />
      <InnerContainer>
        <TitleFrame>
            납부 요약
        </TitleFrame>

        <CountBox>
          <SummaryLeft>
            납부회수
          </SummaryLeft>
          <SummaryRight>
            {contractInfo?.paid_count}회
          </SummaryRight>
        </CountBox>

        <RemainBox>
          <SummaryLeft>
            납부잔여금액
          </SummaryLeft>
          <SummaryRight>
            {contractInfo?.balance.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원
          </SummaryRight>
        </RemainBox>
        
        <TitleFrame>
            계약 상세
        </TitleFrame>

        <DetailFrame>
          <DetailContext>
            <DetailLeft>채권자</DetailLeft>
            <DetailRight>{contractInfo?.lessor_name}</DetailRight>
          </DetailContext>

          <DetailContext>
            <DetailLeft>채무자</DetailLeft>
            <DetailRight>{contractInfo?.lessee_name}</DetailRight>
          </DetailContext>

          <DetailContext>
            <DetailLeft>차용금액</DetailLeft>
            <DetailRight>{contractInfo?.amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</DetailRight>
          </DetailContext>
          
          <DetailContext>
            <DetailLeft>계약시작일</DetailLeft>
            <DetailRight>{contractInfo?.start_date.replace(/-/g, '.').slice(0,10)}</DetailRight>
          </DetailContext>
          
          <DetailContext>
            <DetailLeft>계약종료일</DetailLeft>
            <DetailRight>{contractInfo?.due_date.replace(/-/g, '.').slice(0,10)}</DetailRight>
          </DetailContext>
          
          <DetailContext>
            <DetailLeft>이율</DetailLeft>
            <DetailRight>{contractInfo?.rate}%</DetailRight>
          </DetailContext>
          
          <DetailContext>
            <DetailLeft>계약상태</DetailLeft>
            <DetailRight>{statusReturn()}</DetailRight>
          </DetailContext>
        </DetailFrame>
        <StyledBar/>

      </InnerContainer>

      {/* @ts-ignore */}
      {contractInfo?.lessee_id == userInfo?.user_id && contractInfo?.status == 'EXECUTION_COMPLETED' && (contractInfo?.total_repayment_count - contractInfo?.paid_count == 1) ? (
        <MenuBarClickable onClick={openTransferModal}>
          {/* @ts-ignore */}
          <MenuContextLeft>이자와 잔금 송금하기</MenuContextLeft>
          <MenuContextRight>
            <RightArrowResized />
          </MenuContextRight>
        </MenuBarClickable>
        ) : null}
      {contractInfo?.lessee_id == userInfo?.user_id && contractInfo?.status == 'EXECUTION_COMPLETED' && (contractInfo.total_repayment_count - contractInfo?.paid_count != 1)? (
        <MenuBarClickable onClick={openTransferModal}>
          {/* @ts-ignore */}
          <MenuContextLeft>{contractInfo?.paid_count + 1}회차 이자 송금하기</MenuContextLeft>
          <MenuContextRight>
            <RightArrowResized />
          </MenuContextRight>
        </MenuBarClickable>
        ) : null}
        <MenuBarClickable onClick={toPDF}>
          <MenuContextLeft>차용증 PDF로 보기</MenuContextLeft>
          <MenuContextRight>
            <RightArrowResized />
          </MenuContextRight>
        </MenuBarClickable>
      <NavBar />
      
      {transferModalOpen? (
        <LeseeTransferModal
        closeModal={closeTransferModal}
        transferClicked={transferClicked}
        paymentCount={contractInfo?.paid_count}
        lessorName={contractInfo?.lessor_name}
        balance={userInfo?.account_info.balance}
        />
      ): null}

      {passwordModalOpen? (
        <SharePasswordModal
        closePasswordModal={closePasswordModal}
        passwordCorrect={passwordCorrect}
        userPassword={userInfo?.password}
        />
      ): null}

      
    </Main>
  );
}


const InnerContainer = styled.div`
  position: relative;
  width: calc(100% - 40px);
  margin-top: 80px;
  margin-left: 20px;
  `

  const TitleFrame = styled.div`
    position: relative;
    width: calc(100% - 56px);
    margin-left: 4px;
    font-size: 19px;
    font-weight: 700;
    color: var(--black);
  `

const SummaryLeft = styled.div`
  font-size: 15px;
  font-weight: 400;
  color: var(--font-gray);
  margin-left: 15px;
`

const SummaryRight = styled(SummaryLeft)`
  margin-right: 15px;
  font-weight: 600;
  color: var(--black);
  margin-right: 15px;
`

const DetailFrame = styled.div`
  position: relative;
  width: calc(100% - 8px);
  margin-left: 4px;
  margin-top: 22px;
  margin-bottom: 32px;
`

const DetailContext = styled.div`
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: 500;
`

const DetailLeft = styled.div`
  color: var(--black);
`

const DetailRight = styled.div`
  font-size: 15px;
  font-weight: 400;
  color: var(--font-gray);
`



const CountBox = styled.div`
  position: relative;
  width: 100%;
  height: 64px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f0f2f8;
  border-radius: 10px 10px 0px 0px;
  margin-top: 10px;
`;

const RemainBox = styled(CountBox)`
  height: 48px;
  background-color: #e6e9f3;
  border-radius: 0px 0px 10px 10px;
  margin-bottom: 32px;
  margin-top: 0px;
`;

const RightArrowResized = styled(RightArrow)`
  width: 8px;
`;


const StyledBar = styled.div`
  position: relative;
  width: 100%;
  border-bottom: 1px solid var(--gray);
  margin-bottom: 8px;
`;

const MenuBarClickable = styled.div`
  position: relative;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--white);
  align-items: center;
  font-size: 17.5px;
  &:hover {
    background-color: var(--background);
    transition: all ease 250ms;
  }
  background-color: var(--white);
  transition: all ease 250ms;
`;

const MenuContextLeft = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  color: var(--black);
  font-weight: 500;
  margin-left: 24px;
`;

const MenuContextRight = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  color: var(--font-gray);
  font-size: 16.5px;
  margin-right: 28px;
`;