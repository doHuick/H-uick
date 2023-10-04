import React, { useRef, useState } from 'react';
import './SignModal.css'
import styled, { keyframes } from 'styled-components'
import SignatureCanvas from 'react-signature-canvas'
import { MiniConfirmButton } from '../Button/Button';
import toast, { toastConfig } from 'react-simple-toasts'
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';

interface SignupSignModalProps {
  onSave: (imageData: string) => void;
}

interface DarkBackgroundProps {
  isClosing: boolean;
}

interface ModalContainerProps {
  isClosing: boolean;
}

export default function SignupSignModal({ onSave }: SignupSignModalProps) {
  const [isClosing, setIsClosing] = useState(false);
  
  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    zIndex: 5,
    maxVisibleToasts: 1
  })


  const closeAndAnimate = () => {
    setIsClosing(true);
  };

  const signCanvas = useRef() as React.MutableRefObject<any>;
  
  const clear = () => {
    signCanvas.current.clear();
  };

  const handleSaveClick = () => {
    // const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
    // onSave(image);
    if (signCanvas.current.isEmpty()) {
      toast("서명 그려!!!")
    } else {
      
      const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
      onSave(image)
      closeAndAnimate();
    }
  };
  
  return (
  <>
      <DarkBackground isClosing={isClosing}/>
      <ModalContainer isClosing={isClosing}>
        <SignModalUpperBar>
          <SignModalTitle>서명등록</SignModalTitle>
          <SignModalUpperButtons>
            <SignModalEraseButton  onClick={clear} >&nbsp;&nbsp;재설정&nbsp;&nbsp;</SignModalEraseButton>
          </SignModalUpperButtons>
        </SignModalUpperBar>

        <SignatureArea>
          <SignatureCanvas
            ref={signCanvas}
            canvasProps={{ className: 'sigCanvas canvasStyle' }}
          />
        </SignatureArea>

        <SignSaveButtonFrame>
          <SignSaveButton onClick={handleSaveClick}>
            저장
          </SignSaveButton>
        </SignSaveButtonFrame>
      </ModalContainer>
  </>
  )
}

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1; // 원하는 어두운 배경의 최종 투명도 설정
  }
`;

const slideIn = keyframes`
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
`;

const slideOut = keyframes`
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(100%);
  }
`;

const DarkBackground = styled.div<DarkBackgroundProps>`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); // 어두운 배경의 색상과 투명도 조절
  z-index: 2; // 모달 뒤에 위치하도록 설정
  animation: ${({ isClosing }) => (isClosing ? '' : fadeIn)} 0.30s ease-in-out;
`;

const ModalContainer = styled.div<ModalContainerProps>`
  position: fixed;
  z-index: 3;
  width: 100%;
  height: 380px;
  bottom: 0;
  background-color: var(--white);
  border-radius: 30px 30px 0px 0px;
  box-shadow: 0px -5px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  animation: ${({ isClosing }) => (isClosing ? slideOut : slideIn)} 0.35s ease-in-out;
`;

const SignModalUpperBar = styled.div`
  position: relative;
  margin-top: 32px;
  width: 100%;
  height: 36px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 24px;
  font-weight: bold;
`

const SignModalTitle = styled.span`
  margin-left: 30px;
`

const SignModalUpperButtons = styled.div`
  margin-right: 30px;
  display: flex;
  align-items: center;
  
`
const SignModalEraseButton = styled.div`
  font-size: 13px;
  font-weight: normal;
  border: 1px solid var(--gray);
  border-radius: 5px;
  /* padding-top: 5px;
  padding-bottom: 5px; */
  padding: 7px 3px;
  color: var(--gray);
`
const SignatureArea = styled.div`
  position: relative;
  margin-top: 22px;
  width: 100%;
  height: 168px;
  ;

`

const SignSaveButtonFrame = styled.div`
  position: absolute;
  width: 100%;
  display: flex;
  justify-content: center;
  bottom: 48px;
`

// const SignSaveButtonDisabled = styled.div`
//   position: relative;
//   margin-top: 20px;
//   width: 100%;
//   text-align: center;
//   background-color: var(--gray);
//   color: var(--black);
// `


const SignSaveButton = styled(MiniConfirmButton)`
  position: relative;
  margin-left: 30px;
  margin-right: 30px;
  width: 100%;
  height: 46px;
  font-size: 17px;
  font-weight: 600;

`