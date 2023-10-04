import { keyframes, styled } from 'styled-components';

interface DarkBackgroundProps {
  isClosing: boolean;
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

export const DarkBackground = styled.div<DarkBackgroundProps>`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); // 어두운 배경의 색상과 투명도 조절
  z-index: 2; // 모달 뒤에 위치하도록 설정
  animation: ${({ isClosing }) => (isClosing ? fadeOut : fadeIn)} 0.37s ease-in-out;
`;