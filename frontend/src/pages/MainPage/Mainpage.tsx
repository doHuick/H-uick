import { useState, useEffect } from 'react';
import NavBar from '../../components/NavBar/NavBar';
import { styled } from 'styled-components';
import { Main } from '../../style';
import { TextBox } from '../../components/TextBox/TextBox';
import { ReactComponent as Copy } from '../../assets/icons/copy.svg';
import { MainTab } from '../../components/TabBar/MainTab';
import axios, { BASE_URL } from '../../api/apiController';
import toast, { toastConfig } from 'react-simple-toasts';
import Rolling from '../../components/Rolling/Rolling';
import { useNavigate } from 'react-router-dom';

type FlexDivProps = {
  margin?: string;
};

interface ContractProps {
  contract_id?: number;
  lessee_name?: string;
  lessor_name?: string;
  total_repayment_count: number;
  current_repayment_count: number;
  repayment_date: Date;
  current_amount: number;
  rate: number;
  name?: string;
}

export default function Mainpage() {
  const [nowActive, setNowActive] = useState<string>('borrowing');
  const [feeds, setFeeds] = useState<ContractProps[]>([]);
  const [nowBalance, setNowBalance] = useState<number>(0);
  const [accountNumber, setAccountNumber] = useState<string>('');
  const [monthBorrow, setMonthBorrow] = useState<number | null>(null);
  const [monthRent, setMonthRent] = useState<number | null>(null);
  const navigate = useNavigate();

  // [Tab 기능]
  const fetchFeedsForTab = (tab: string) => {
    if (tab === 'borrowing') {
      axios
        .get(`${BASE_URL}/contracts/lessee/me`, {
          headers: { Authorization: localStorage.getItem('access_token') },
        })
        .then((res) => {
          // console.log('lessee/me data : ', res.data);
          if (res.data === '') {
            setFeeds([]);
          } else {
            const myContracts = res.data.map((contractData: ContractProps) => ({
              contract_id: contractData.contract_id,
              name: contractData.lessor_name,
              total_repayment_count: contractData.total_repayment_count,
              current_repayment_count: contractData.current_repayment_count,
              repayment_date: new Date(contractData.repayment_date),
              current_amount: contractData.current_amount,
              rate: contractData.rate,
            }));
            setFeeds(myContracts);
          }
        });
    } else if (tab === 'rental') {
      axios
        .get(`${BASE_URL}/contracts/lessor/me`, {
          headers: { Authorization: localStorage.getItem('access_token') },
        })
        .then((res) => {
          // console.log('lessor/me data : ', res.data);
          if (res.data === '') {
            setFeeds([]);
          } else {
            const myContracts = res.data.map((contractData: ContractProps) => ({
              contract_id: contractData.contract_id,
              name: contractData.lessee_name,
              total_repayment_count: contractData.total_repayment_count,
              current_repayment_count: contractData.current_repayment_count,
              repayment_date: new Date(contractData.repayment_date),
              current_amount: contractData.current_amount,
              rate: contractData.rate,
            }));
            setFeeds(myContracts);
          }
        });
    }
  };

  useEffect(() => {
    fetchFeedsForTab(nowActive);
  }, [nowActive]);

  const handleTabClick = (tab: string) => {
    setNowActive(tab);
    fetchFeedsForTab(tab);
  };

  useEffect(() => {
    // [계좌 정보 API]
    axios
      .get(`${BASE_URL}/banking/accounts/me`, {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .then((res) => {
        setAccountNumber(res.data.account_number);
        setNowBalance(res.data.balance.toLocaleString());
      });

    // [롤링 공지용 이번달 입금, 출금 예정금액 API]
    axios
      .get(`${BASE_URL}/contracts/lessee/me`, {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .then((res) => {
        const totalCurrentAmount = res.data.reduce(
          (accumulator: number, item: any) => {
            return accumulator + item.current_amount;
          },
          0,
        );
        setMonthBorrow(totalCurrentAmount.toLocaleString());
      });

    axios
      .get(`${BASE_URL}/contracts/lessor/me`, {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .then((res) => {
        const totalCurrentAmount = res.data.reduce(
          (accumulator: number, item: any) => {
            return accumulator + item.current_amount;
          },
          0,
        );
        setMonthRent(totalCurrentAmount.toLocaleString());
      });

    if (localStorage.getItem("device_token")) {
      console.log(localStorage.getItem("device_token"));
      axios
        .post(`${BASE_URL}/notifications/token`,
        { token: localStorage.getItem("device_token")},
        {
          headers: { Authorization: localStorage.getItem('access_token') },
        });
        
      localStorage.removeItem("device_token");
    }
  }, []);

  // [계좌 복사 클릭 시 toast 메시지]
  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    maxVisibleToasts: 1,
  });

  const handleCopyClick = async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      toast('계좌번호가 복사되었습니다.');
    } catch (error) {
      toast('에러가 발생했습니다.');
    }
  };

  return (
    <StyledMain>
      {/* 계좌 박스 */}
      <BlueBox>
        {/* 계좌 정보 */}
        <FlexDiv margin="0px 30px">
          <FlexDiv2 margin="10px 0px 0px 0px">
            <TextBox
              fontSize="17px"
              fontWeight="600"
              color="var(--white)"
              textAlign="left"
            >
              입출금
            </TextBox>
            <TextBox
              fontSize="17px"
              fontWeight="400"
              color="var(--white)"
              textAlign="left"
            >
              싸피우대통장
            </TextBox>
          </FlexDiv2>
        </FlexDiv>

        {/* 계좌 번호 */}
        <FlexDiv2 margin="0px 30px">
          <TextBox
            fontSize="14px"
            fontWeight="300"
            color="var(--white)"
            textAlign="left"
          >
            싸피{' '}
            {`${accountNumber.slice(0, 3)}-${accountNumber.slice(
              3,
              6,
            )}-${accountNumber.slice(6, 12)}`}
          </TextBox>
          <Copy
            style={{ cursor: 'pointer' }}
            onClick={() => handleCopyClick(`${accountNumber}`)}
          />
        </FlexDiv2>

        {/* 잔액 */}
        <TextBox
          fontSize="30px"
          fontWeight="600"
          color="var(--white)"
          textAlign="left"
          margin="30px 30px 31px 30px"
        >
          {nowBalance}
          <TextBox
            fontSize="30px"
            fontWeight="500"
            color="var(--white)"
            margin="0 0 0 2px"
          >
            원
          </TextBox>
        </TextBox>

        {/* 롤링 공지 */}
        <Rolling monthBorrow={monthBorrow} monthRent={monthRent} />
      </BlueBox>

      {/* 탭 기능 */}
      <MainTab setNowActive={handleTabClick} />
      {feeds.length === 0 ? (
        <CenterDiv>
          <LineFrame>
            <PigImg src="/pig-head.png" />

          </LineFrame>
          <LineFrame>
            <TextBox
              fontSize="20px"
              fontWeight="500"
              color="var(--font-gray)"
              textAlign="center"
              >
              아직 체결된 계약이 없습니다.
            </TextBox>
            </LineFrame>
        </CenterDiv>
      ) : (
        <ContractsDiv>
          {feeds.map((feed) => (
            <WhiteBox
              key={feed.contract_id}
              onClick={() => navigate(`/detail/${feed.contract_id}`)}
            >
              <FlexDiv margin="0px 30px 2px 30px">
                <TextBox
                  fontSize="15px"
                  fontWeight="500"
                  color="var(--black)"
                  textAlign="left"
                >
                  {feed.name}
                </TextBox>
                <TextBox
                  fontSize="20px"
                  fontWeight="700"
                  color="var(--huick-blue)"
                  textAlign="left"
                  margin="1px 0px 0px 0px"
                >
                  {feed.rate}%
                </TextBox>
              </FlexDiv>
              <FlexDiv margin="0px 30px 9px 30px">
                <TextBox
                  fontSize="12px"
                  fontWeight="400"
                  color="var(--gray)"
                  textAlign="left"
                  margin="-2px 0px 0px 0px"
                >
                  전체 {feed.total_repayment_count}회 중{' '}
                  {feed.current_repayment_count}회차 납부 예정
                </TextBox>
              </FlexDiv>
              <FlexEndDiv margin="14px 30px 0px 30px">
                <TextBox
                  fontSize="24px"
                  fontWeight="700"
                  color="var(--black)"
                  textAlign="left"
                  margin="0 0 -1px 0"
                >
                  {feed.current_amount.toLocaleString()}원
                </TextBox>
                <TextBox
                  fontSize="14px"
                  fontWeight="500"
                  color="var(--black)"
                  textAlign="left"
                >
                  {feed.repayment_date.getDate().toString()}일 납부
                </TextBox>
              </FlexEndDiv>
            </WhiteBox>
          ))}
        </ContractsDiv>
      )}
      <NavBar />
    </StyledMain>
  );
}

const BlueBox = styled.div`
  background-color: var(--huick-blue);
  width: 360px;
  height: 181px;
  border-radius: 10px;
  box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
  margin: 20px 13px 6.5px 15px;
  padding: 20px 0px;
`;

const WhiteBox = styled.div`
  background-color: var(--white);
  width: 360px;
  border-radius: 10px;
  box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
  margin: 0px 13px 12px 15px;
  padding: 14px 0px;
  padding-bottom: 18px;
  &:hover {
    cursor: pointer;
  }
`;

const FlexDiv = styled.div<FlexDivProps>`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: ${(props: FlexDivProps) => props.margin};
`;

const FlexEndDiv = styled(FlexDiv)`
  align-items: flex-end;
`

const FlexDiv2 = styled(FlexDiv)`
  justify-content: left;
  gap: 5px;
`;

const PigImg = styled.img`
  width: 88px;
  height: 88px;
  margin: 124px 0 16px 0;
`;

const CenterDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
`;

const StyledMain = styled(Main)`
  overflow-y: hidden;
`;

const ContractsDiv = styled.div`
  overflow-y: scroll;
  height: calc(100% - 378px);
  /* height: 490px; */
`;

const LineFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`