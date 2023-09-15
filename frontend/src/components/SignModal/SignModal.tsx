import React, { useRef } from 'react';
import './SignModal.css'
import ModalBase from '../Modal/ModalBase'
import styled from 'styled-components';
import SignatureCanvas from 'react-signature-canvas'
import { ReactComponent as ModalClose } from '../../assets/icons/close-button.svg'
import { MiniConfirmButton } from '../Button/Button';
import toast, { toastConfig } from 'react-simple-toasts'
import 'react-simple-toasts/dist/theme/frosted-glass.css';
import 'react-simple-toasts/dist/theme/light.css';

interface SignModalProps {
  closeModal: () => void;
  onSave: (imageData: string) => void;
  frameHeight: String;
}

export default function SignModal({ closeModal, onSave, frameHeight }: SignModalProps) {

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    zIndex: 5,
    maxVisibleToasts: 1
  })

  const handleSignModalCloseClick = () => {
    closeModal(); // ModalBase.tsx에서 전달한 closeModal 함수 호출
  };

  const handleSaveClick = () => {
    // const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
    // onSave(image);
    if (signCanvas.current.isEmpty()) {
      toast("서명 그려!!!")
    } else {

      // 여기서 POST
      const image = signCanvas.current.getTrimmedCanvas().toDataURL('image/png');
      onSave(image);

      setTimeout(() => {
        closeModal();
      }, 260);

    }
  };

  const signCanvas = useRef() as React.MutableRefObject<any>;

  const clear = () => {
    signCanvas.current.clear();
  };


  return (
    <ModalBase closeModal={closeModal} frameHeight={frameHeight}>
              <SignModalUpperBar>
          <SignModalTitle>서명등록</SignModalTitle>
          <SignModalUpperButtons>
            <SignModalEraseButton  onClick={clear} >&nbsp;&nbsp;재설정&nbsp;&nbsp;</SignModalEraseButton>
            <SignModalCloseButton onClick={handleSignModalCloseClick}/>
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
    </ModalBase>
  )
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
`