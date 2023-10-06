import React, { useState } from 'react';
import styled, { keyframes } from 'styled-components'
import { ReactComponent as ModalClose } from '../../assets/icons/close-button.svg'
import { MiniConfirmButton } from '../Button/Button';

interface LesseeTransferModalProps {
  closeModal: () => void;
  transferClicked: () => void;
  paymentCount?: number;
  lessorName?: string;
  balance?: number;

}

interface DarkBackgroundProps {
  isClosing: boolean;
}

interface ModalContainerProps {
  isClosing: boolean;
  children: React.ReactNode;
}

export default function LeseeTransferModal({ closeModal, transferClicked, paymentCount, lessorName, balance }: LesseeTransferModalProps) {
  const [isClosing, setIsClosing] = useState(false);
  
  
  const handleDarkBackgroundClick = (event: React.MouseEvent<HTMLDivElement>) => {
    if (event.target === event.currentTarget) {
      closeAndAnimate();
    }
  };

  const closeAndAnimate = () => {
    setIsClosing(true);
    setTimeout(() => {
      closeModal();
    }, 320);
  };

  const closeAndPWD = () => {
    setIsClosing(true);
    localStorage.setItem("isPWDCorrect", 'false')
    setTimeout(() => {
      closeModal();
      transferClicked();
    }, 310);
  }

  
  return (
  <>
      <DarkBackground onClick={handleDarkBackgroundClick} isClosing={isClosing}/>
      <ModalContainer isClosing={isClosing}>
      
        <TransferModalUpperBar>
            <TransferModalTitle></TransferModalTitle>
            <TransferModalUpperButtons>
              <TransferModalCloseButton onClick={closeAndAnimate} />
            </TransferModalUpperButtons>
          </TransferModalUpperBar>

        <TransferTextLine style={{ marginTop : '32px' }}>
          <TransferTextBold>
            {lessorName}
          </TransferTextBold>
          <TransferText>님께</TransferText>
        </TransferTextLine>

        <TransferTextLine>
          {/* @ts-ignore */}
          <TransferTextBold>{paymentCount + 1}회차&nbsp;</TransferTextBold>
          <TransferText>이자를 송금합니다</TransferText>
        </TransferTextLine>
    
        {/* <TransferMoadlAccount>계약이 체결되면 휙이 보내드릴게요 </TransferMoadlAccount> */}
        <TransferMoadlAccount>
          잔액: {balance?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원
        </TransferMoadlAccount>

        <TransferButtonFrame>
          <TransferButton onClick={closeAndPWD}>송금</TransferButton>
        </TransferButtonFrame>
        


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

// const fadeOut = keyframes`
//   from {
//     opacity: 1;
//   }
//   to {
//     opacity: 0; // 원하는 어두운 배경의 최종 투명도 설정
//   }
// `;

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
  animation: ${({ isClosing }) => (isClosing ? '' : fadeIn)} 0.32s ease-in-out; // 수정된 부분
`;

const ModalContainer = styled.div<ModalContainerProps>`
  position: fixed;
  z-index: 3;
  width: 100%;
  height: 332px;
  left: 0;
  bottom: 0;
  background-color: var(--white);
  border-radius: 30px 30px 0px 0px;
  box-shadow: 0px -5px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  animation: ${({ isClosing }) => (isClosing ? slideOut : slideIn)} 0.35s ease-in-out;
`;

const TransferModalUpperBar = styled.div`
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

const TransferModalTitle = styled.span`
  margin-left: 30px;
`

const TransferModalUpperButtons = styled.div`
  margin-right: 30px;
  display: flex;
  align-items: center;
  
`

const TransferModalCloseButton = styled(ModalClose)`
  width: 16px;
`;

const TransferTextLine = styled.div`
  width: 100%;
  text-align: center;
  margin-bottom: 8px;
`

const TransferText = styled.span`
  font-size: 20px;

`

const TransferTextBold = styled.span`
  font-size: 20px;
  font-weight: bold;
`

const TransferMoadlAccount = styled.div`
  width: 100%;
  text-align: center;
  margin-top: 24px;
  font-size: 14px;
  color: var(--font-gray)
`

const TransferButtonFrame = styled.div`
  position: absolute;
  bottom: 48px;
  width: 100%;
  display: flex;
  justify-content: center;
`

const TransferButton = styled(MiniConfirmButton)`
  position: relative;
  margin-left: 30px;
  margin-right: 30px;
  height: 46px;
  width: 100%;
`