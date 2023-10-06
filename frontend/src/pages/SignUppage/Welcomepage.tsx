import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Main } from '../../style';
import { TextBox } from '../../components/TextBox/TextBox';
import { ReactComponent as Check } from '../../assets/icons/check.svg';
import { ReactComponent as Bar } from '../../assets/icons/long-bar.svg';
import { ConfirmButton } from '../../components/Button/Button';
import NavBar from '../../components/NavBar/NavBar';
import toast, { toastConfig } from 'react-simple-toasts';
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import axios, { BASE_URL } from '../../api/apiController';
import { useNavigate } from 'react-router-dom';

export default function Welcomepage() {
  const [name, setName] = useState<String>('');
  const [accountNumber, setAccountNumber] = useState<String>('');
  const [createdTime, setCreatedTime] = useState<String>('');
  const [wallet, setWallet] = useState<String>('');

  const navigation = useNavigate();

  useEffect(() => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      // console.log('res:data : ',res.data);
      // console.log('res.data.name : ', res.data.name);
      setName(res.data.name);
      setAccountNumber(res.data.account_info.accountNumber);
      setCreatedTime(res.data.created_time);
      setWallet(res.data.wallet_address);
    })
  }, []);

  // 계좌 복사 클릭 시 toast 메시지
  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    maxVisibleToasts: 1,
  });

  const handleCopyClick = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      toast('지갑주소가 복사되었습니다.');
    } catch (error) {
      toast('에러가 발생했습니다.');
    }
  };

  return (
    <Main backgroundColor="var(--white)">
      {/* <HeadBar pageName="회원가입" color="var(--white)" /> */}
      <CheckDiv>
        <Check />
      </CheckDiv>
      <FlexWrapDiv>
        <TextBox
          fontSize="25px"
          fontWeight="700"
          textAlign="center"
          margin="0 0 17px 0"
        >
          회원가입 및 계좌계설 완료
        </TextBox>
        <TextBox fontSize="15.5" color="var(--font-gray)" margin="0 0 55px 0">
          비대면 계좌개설이 완료되었습니다
        </TextBox>
        <Bar />
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            고객명
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            {name}
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            계좌번호
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            {accountNumber}
          </TextBox>
        </BetweenDiv>
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            개설일시
          </TextBox>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            {createdTime}
          </TextBox>
        </BetweenDiv>
        <Bar />
        <BetweenDiv>
          <TextBox fontSize="15.5px" fontWeight="500" color="var(--font-gray)">
            지갑주소
          </TextBox>
          <CopyButton onClick={() => handleCopyClick(`${wallet}`)}>
            복사
          </CopyButton>
        </BetweenDiv>
        <BetweenDiv>
          <RenewedTextBox>
            {wallet}
          </RenewedTextBox>
        </BetweenDiv>
        <ButtonFrame>
          <ConfirmButtonRenewed onClick={() => navigation('/')}>휙 사용해보기</ConfirmButtonRenewed>
        </ButtonFrame>
      </FlexWrapDiv>
      <NavBar />
    </Main>
  );
}

const CenterDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 70px;
`;

const BetweenDiv = styled(CenterDiv)`
  width: 320px;
  justify-content: space-between;
  margin: 7px 0px;
`;

const CopyButton = styled.div`
  width: 50px;
  height: 20px;
  font-size: 11px;
  border: 1px solid var(--black);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  &:hover {
    cursor: pointer;
  }
`;

const FlexWrapDiv = styled.div`
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
`;

const CheckDiv = styled.div`
  margin-top: 124px;
  margin-bottom: 36px;
  display: flex;
  justify-content: center;
`;

const ButtonFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 60px;
`

const ConfirmButtonRenewed = styled(ConfirmButton)`
  position: relative;
  width: 324px;
  border-radius: 16px;
  font-size: 17px;
  font-weight: 600;
`;

const RenewedTextBox = styled(TextBox)`
  position: relative;
  width: 320px;
  font-size: 15.5px;
  font-weight: 500;
  color: var(--font-gray);
  word-break: break-all;
`;
