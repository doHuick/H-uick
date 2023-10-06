// import './SignModalMain.css'
import React, { useRef, useState } from 'react';
import styled from 'styled-components';
import { ReactComponent as ModalClose } from '../../assets/icons/close-button.svg'
// import SignaturePad from "react-signature-canvas";
import { MiniConfirmButton } from '../Button/Button';
import { DarkBackground } from '../Modal/DarkBackground';
import ModalContainer from '../Modal/ModalContainer';
import SignatureCanvas from "react-signature-canvas";
import toast, { toastConfig } from 'react-simple-toasts'
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';

interface SignModalMainProps {
  closeModal: () => void;
  onSave: (imageData: string) => void;
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
      <DarkBackground onClick={handleDarkBackgroundClick} isClosing={isClosing} />
      <ModalContainer isClosing={isClosing}>

        <SignModalUpperBar>
          <SignModalTitle>서명등록</SignModalTitle>
          <SignModalUpperButtons>
            <SignModalEraseButton  onClick={clear} >&nbsp;&nbsp;재설정&nbsp;&nbsp;</SignModalEraseButton>
            <SignModalCloseButton onClick={isModalCloseClick} />
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
  );
}


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

const SignSaveButtonFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
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
  margin-top: 28px;
  margin-left: 30px;
  margin-right: 30px;
  width: 100%;
  height: 46px;
`