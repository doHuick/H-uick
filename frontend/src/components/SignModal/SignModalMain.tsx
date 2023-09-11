import './SignModalMain.css'
import React, { useRef, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import { ReactComponent as ModalClose } from '../../assets/icons/close-button.svg'
// import SignaturePad from "react-signature-canvas";
import SignatureCanvas from "react-signature-canvas";
import toast, { toastConfig } from 'react-simple-toasts'
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';

interface SignModalMainProps {
  closeModal: () => void;
  onSave: (imageData: string) => void;
}

interface SignModalContainerProps {
  isClosing: boolean;
}

export default function SignModalMain({ closeModal, onSave }: SignModalMainProps) {
  const [isClosing, setIsClosing] = useState(false);

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    zIndex: 5,
    maxVisibleToasts: 1
  })

  const handleDarkBackgroundClick = (event: React.MouseEvent<HTMLDivElement>) => {
    if (event.target === event.currentTarget) {
      closeAndAnimate();
    }
  };
  

  const isModalCloseClick = () => {
    closeAndAnimate();
  };

  const closeAndAnimate = () => {
    setIsClosing(true);
    setTimeout(() => {
      closeModal();
    }, 350);
  };

  const handleSaveClick = () => {
    // const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
    // onSave(image);
    if (signCanvas.current.isEmpty()) {
      toast("서명 그려!!!")
    } else {

      // 여기서 POST

      setIsClosing(true);
      setTimeout(() => {
        const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
        onSave(image);
      }, 350);

    }
  };


  const signCanvas = useRef() as React.MutableRefObject<any>;

  const clear = () => {
    signCanvas.current.clear();
  };

  // const save = () => {
  //   const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
  //   onSave(image);
  // };
  
  return (
    <>
      <DarkBackground className={isClosing ? "hide" : "show"} onClick={handleDarkBackgroundClick} />
      <SignModalContainer isClosing={isClosing}>
        <SignModalUpperBar>
          <span style={{marginLeft: '30px'}}>서명등록</span>
          <SignModalUpperButtons>
            <SignModalEraseButton  onClick={clear} >&nbsp;&nbsp;재설정&nbsp;&nbsp;</SignModalEraseButton>
            <SignModalCloseButton onClick={isModalCloseClick} />
          </SignModalUpperButtons>
        </SignModalUpperBar>

        <SignatureArea>
          <SignatureCanvas // canvas element
            ref={signCanvas}
            canvasProps={{ className: 'sigCanvas canvasStyle' }}
          />
        </SignatureArea>

      <div style={{ position: 'relative', width: '100%', display: 'flex', justifyContent: 'center'}}>
        <SignSaveButton onClick={handleSaveClick}>
          저장
        </SignSaveButton>

      </div>

      </SignModalContainer>
    </>
  );
}

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

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1; // 원하는 어두운 배경의 최종 투명도 설정
  }
`;

const DarkBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); // 어두운 배경의 색상과 투명도 조절
  z-index: 2; // 모달 뒤에 위치하도록 설정
  animation: ${fadeIn} 0.3s ease-in-out; // 서서히 어두워지는 애니메이션 적용
`;


const SignModalContainer = styled.div<SignModalContainerProps>`
  position: fixed;
  z-index: 3;
  width: 100%;
  height: 380px;
  bottom: 0;
  background-color: var(--white);
  /* animation: ${slideIn} 0.5s ease-in-out; */
  border-radius: 30px 30px 0px 0px;
  box-shadow: 0px -5px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  animation: ${({ isClosing }) => (isClosing ? slideOut : slideIn)} 0.5s ease-in-out;
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
  margin-right: 22px;
`

const SignModalCloseButton = styled(ModalClose)`
  width: 16px;
`;

const SignatureArea = styled.div`
  position: relative;
  margin-top: 22px;
  width: 100%;
  height: 168px;
  ;

`

const SignSaveButtonDisabled = styled.div`
  position: relative;
  margin-top: 20px;
  width: 100%;
  text-align: center;
  background-color: var(--gray);
  color: var(--black);
`

const SignSaveButton = styled.div`
  position: relative;
  margin-top: 28px;
  margin-left: 30px;
  margin-right: 30px;
  width: 100%;
  height: 46px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--huick-blue);
  color: white;
  border-radius: 20px;
  font-size: 22px;
`