import React, { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg';
import HeadBar from '../../components/HeadBar/HeadBar';
// import SignModalMain from '../../components/SignModal/SignModalMain';
import SignModal from '../../components/SignModal/SignModal';
import NavBar from '../../components/NavBar/NavBar';
// import ModalFrame from '../../components/Modal/ModalFrame';
import { ConfirmButton } from '../../components/Button/Button';
import toast, { toastConfig } from 'react-simple-toasts';
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';
import axios, { BASE_URL } from '../../api/apiController';
import { useNavigate } from 'react-router-dom';


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

export default function MyPage() {
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [signatureImage, setSignatureImage] = useState<string | null>(null);
  const [userInfo, setUserInfo] = useState<UserInfoProps>()

  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      setUserInfo(res.data)
      console.log(res.data)
      setSignatureImage(res.data.signature_url)
    })
    .catch((err) => {
      navigate('/login');
    })
  }, []);

  const logout = () => {
    localStorage.clear()
    navigate('/login')
  }
  
  const signOut = () => {
    axios.get(`${BASE_URL}/users/me`, {
      headers: { Authorization: localStorage.getItem('access_token') },
    }).then((res) => {
      console.log(res)
    })
    navigate('/login')
  }

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
  });
  

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const handleSave = (imageData: string) => {
    setSignatureImage(imageData);
    axios.patch(
      `${BASE_URL}/users/signature`,
      {
        signatureBase64: imageData
      }
      ,
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      },
    )
    setTimeout(() => {
      closeModal();
      toast('서명이 등록되었습니다');
    }, 260);
  };

  return (
    <Main>
      <HeadBar pageName="마이페이지" bgcolor='var(--background)' />
      <WhiteFrame style={{ marginTop: '108px' }}>
        <MenuBar style={{ paddingTop: '8px', pointerEvents: 'none' }}>
          <MenuTitle>내 계좌 정보</MenuTitle>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>은행명</MenuContextLeft>
          <MenuContextRight>휙뱅크</MenuContextRight>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>예금주</MenuContextLeft>
          <MenuContextRight>{userInfo?.name}</MenuContextRight>
        </MenuBar>

        <MenuBar style={{ paddingBottom: '4px' }}>
          <MenuContextLeft>계좌번호</MenuContextLeft>
          <MenuContextRight>{`
          ${userInfo?.account_info.accountNumber.slice(0,3)}-${userInfo?.account_info.accountNumber.slice(3,6)}-${userInfo?.account_info.accountNumber.slice(6,12)}`}</MenuContextRight>
        </MenuBar>
      </WhiteFrame>
      <WhiteFrame>
        <MenuBar style={{ paddingTop: '8px', pointerEvents: 'none' }}>
          <MenuTitle>내 전자 서명</MenuTitle>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>내 서명</MenuContextLeft>
          <MenuContextRight>
            {signatureImage ? (
              <SignPreview>
                <img
                  src={signatureImage}
                  style={{
                    width: '80%',
                    height: '90%',
                    objectFit: 'scale-down',
                  }}
                  alt=""
                />
              </SignPreview>
            ) : (
              <span>서명을 등록해주세요</span>
            )}
            {/* <img src="../../../sign-test.png" style={{width: '80%', height: '90%'}} alt="" /> */}
          </MenuContextRight>
        </MenuBar>

        <MenuBarClickable
          style={{ paddingBottom: '4px' }}
          onClick={() => {
            showModal();
          }}
        >
          <MenuContextLeft>수정하기</MenuContextLeft>
          <MenuContextRight>
            <RightArrowResized />
          </MenuContextRight>
        </MenuBarClickable>
      </WhiteFrame>
      {/* {modalOpen && (
        <SignModalMain closeModal={closeModal} onSave={handleSave} />
        // <Modal closeModal={closeModal} >
    
        // </Modal>
      )}{' '} */}

      <WhiteFrame>
        <MenuBar style={{ paddingTop: '8px', pointerEvents: 'none' }}>
          <MenuTitle>계정</MenuTitle>
        </MenuBar>

        <MenuBar onClick={logout}>
          <MenuContextLeft style={{color: 'var(--red)'}}>로그아웃</MenuContextLeft>
        </MenuBar>

        <MenuBar onClick={signOut}>
          <MenuContextLeft>회원탈퇴</MenuContextLeft>
        </MenuBar>
      </WhiteFrame>
      {modalOpen? (
        <SignModal closeModal={closeModal} onSave={handleSave} />
        // <ModalFrame closeModal={closeModal} >
        //   sdsadsadsadasdsa
        // </ModalFrame>
      ): null}
      <LogoutAndVer>
        <HuickVer>©️ H-uick v 1.0.0</HuickVer>
      </LogoutAndVer>

      <NavBar />
    </Main>
  );
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--background);
  overflow-y: scroll;
`;

const WhiteFrame = styled.div`
  margin-bottom: 36px;
  width: 100%;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.04);
`;

const MenuBar = styled.div`
  position: relative;
  width: 100%;
  height: 56px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--white);
  align-items: center;
  font-size: 17.5px;
`;

const MenuBarClickable = styled(MenuBar)`
  &:hover {
    background-color: var(--background);
    transition: all ease 250ms;
  }
  background-color: var(--white);
  transition: all ease 250ms;
`;

const MenuTitle = styled.div`
  font-size: 22px;
  font-weight: bold;
  margin-left: 22px;
`;

const MenuContextLeft = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  margin-left: 22px;
  color: var(--black);
  font-weight: 500;
`;

const MenuContextRight = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  margin-right: 26px;
  color: var(--font-gray);
  font-size: 16.5px;
`;

const SignPreview = styled.div`
  width: 100px;
  height: 75%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  background-color: var(--background);
`;

const RightArrowResized = styled(RightArrow)`
  width: 8px;
`;

const LogoutAndVer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`

const LogOutButton = styled(ConfirmButton)`
  background-color: var(--yellow);
  margin-top: 8px;
  margin-bottom: 36px;
`


const HuickVer = styled.div`
  font-size: 14;
  color: var(--gray);
  font-weight: 500;
  margin-bottom: 102px
`;

const SignOut = styled(HuickVer)`
  font-size: 12px;
  color: #d37373;
  text-decoration: underline;
  margin-bottom: 6px;

`;