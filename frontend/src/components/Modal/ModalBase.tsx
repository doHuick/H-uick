import React, { useState } from 'react';
import styled, { keyframes } from 'styled-components'

interface ModalBaseProps {
  closeModal: () => void;
  children: React.ReactNode;
  frameHeight: String;
}

interface DarkBackgroundProps {
  isClosing: boolean;
}

interface ModalContainerProps {
  isClosing: boolean;
  children: React.ReactNode;
}

export default function ModalBase({ closeModal, children, frameHeight, }: ModalBaseProps) {
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
    }, 260);
  };
  
  return (
  <>
      <DarkBackground onClick={handleDarkBackgroundClick} isClosing={isClosing}/>
      <ModalContainer style={{height: `${frameHeight}`}} isClosing={isClosing}>
        {children}
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

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0; // 원하는 어두운 배경의 최종 투명도 설정
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
  animation: ${({ isClosing }) => (isClosing ? fadeOut : fadeIn)} 0.30s ease-in-out;
`;

const ModalContainer = styled.div<ModalContainerProps>`
  position: fixed;
  z-index: 3;
  width: 100%;
  bottom: 0;
  background-color: var(--white);
  border-radius: 30px 30px 0px 0px;
  box-shadow: 0px -5px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  animation: ${({ isClosing }) => (isClosing ? slideOut : slideIn)} 0.35s ease-in-out;
`;