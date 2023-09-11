import React, { useState } from 'react'
import { styled } from 'styled-components'
import { ReactComponent as RightArrow } from '../../assets/icons/right-arrow.svg'
import HeadBar from '../../components/SignModal/HeadBar/HeadBar'
import SignModalMain from '../../components/SignModal/SignModalMain'
import toast, { toastConfig } from 'react-simple-toasts'
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';

export default function MyPage() {
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [signatureImage, setSignatureImage] = useState<string | null>(null);

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center'
  })

  const showModal = () => {
    setModalOpen(true);
  }

  const closeModal = () => {
    setModalOpen(false)
  }

  const handleSave = (imageData: string) => {
    setSignatureImage(imageData);
    closeModal();
    toast("서명이 등록되었습니다")
  };

  return (
    <Main>
      <HeadBar pageName="마이페이지" />
      
      <WhiteFrame style={{marginTop: '108px'}}>
        <MenuBar style={{paddingTop: '8px', pointerEvents: 'none'}}>
          <MenuTitle>내 계좌 정보</MenuTitle>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>은행명</MenuContextLeft>
          <MenuContextRight>휙뱅크</MenuContextRight>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>예금주</MenuContextLeft>
          <MenuContextRight>임준수</MenuContextRight>
        </MenuBar>

        <MenuBar style={{paddingBottom: '4px'}}>
          <MenuContextLeft>계좌번호</MenuContextLeft>
          <MenuContextRight>110-400-888327</MenuContextRight>
        </MenuBar>
      </WhiteFrame>

      <WhiteFrame>
        <MenuBar style={{paddingTop: '8px', pointerEvents: 'none'}}>
          <MenuTitle>내 전자 서명</MenuTitle>
        </MenuBar>

        <MenuBar>
          <MenuContextLeft>내 서명</MenuContextLeft>
          <MenuContextRight>
            {signatureImage ? (
              <SignPreview>
                <img src={signatureImage} style={{ width: '80%', height: '90%', objectFit: 'scale-down' }} alt="" />
              </SignPreview>
        ) : <span>서명을 등록해주세요</span> }
              {/* <img src="../../../sign-test.png" style={{width: '80%', height: '90%'}} alt="" /> */}

          </MenuContextRight>
        </MenuBar>

        <MenuBarClickable style={{paddingBottom: '4px'}}
        onClick={()=>{ showModal() }}>
          <MenuContextLeft>등록하기</MenuContextLeft>
          <MenuContextRight>
            <RightArrowResized />
          </MenuContextRight>
        </MenuBarClickable>
      </WhiteFrame>

      {modalOpen && <SignModalMain closeModal={closeModal} onSave={handleSave} />} {}
      
      <br/>로그아웃버튼<br/><br/><br/><br/>
      
      <SignOut>회원탈퇴</SignOut>
      <HuickVer style={{marginBottom : '28px'}}>©️ H-uick v 1.0.0</HuickVer>
    </Main>
  )
}

const Main = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  background-color: var(--background);
  overflow-y: scroll;
`

const WhiteFrame = styled.div`
  margin-bottom: 36px;
  width: 100%;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.04);
`

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

`

const MenuBarClickable = styled(MenuBar)`
  &:hover {
    background-color: var(--background);
    transition: all ease 250ms;
  }
  background-color: var(--white);
  transition: all ease 250ms;
`

const MenuTitle = styled.div`
  font-size: 22px;
  font-weight: bold;
  margin-left: 22px;
`

const MenuContextLeft = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  margin-left: 22px;
  color: var(--black);
  font-weight: 500;
`

const MenuContextRight = styled.span`
  display: flex;
  align-items: center;
  height: 100%;
  margin-right: 26px;
  color: var(--font-gray);
  font-size: 16.5px;
  
`

const SignPreview = styled.div`
  width: 100px;
  height: 75%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  background-color: var(--background);

`

const RightArrowResized = styled(RightArrow)`  
  width: 8px;
`;

const HuickVer = styled.div`
  width: 100%;
  text-align: center;
  font-size: 14;
  color: var(--gray);
  font-weight: bold;
`

const SignOut = styled(HuickVer)`
  font-size: 12px;
  color: #D37373;
  text-decoration: underline;
  margin-bottom: 6px;
`