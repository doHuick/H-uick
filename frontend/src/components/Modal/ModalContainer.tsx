import React from 'react';
import styled, { keyframes } from 'styled-components';

interface ModalContainerProps {
  isClosing: boolean;
  children: React.ReactNode;
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

export default function ModalContainer({ isClosing, children }: ModalContainerProps) {
  return (
    <StyledModalContainer isClosing={isClosing}>
      {children}
    </StyledModalContainer>
  );
}

const StyledModalContainer = styled.div<ModalContainerProps>`
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
  animation: ${({ isClosing }) => (isClosing ? slideOut : slideIn)} 0.45s ease-in-out;
`;