import { styled } from 'styled-components';

export const ConfirmButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 300px;
  height: 55px;
  border: none;
  border-radius: 20px;
  background-color: var(--huick-blue);
  position: relative;
  font-size: 22px;
  font-weight: 700;
  color: var(--white);
  &:hover {
    cursor: pointer;
  }
`;

export const CancleButton = styled(ConfirmButton)`
  background-color: var(--red);
`;

export const MiniConfirmButton = styled(ConfirmButton)`
  width: 150px;
  /* 높이 46px로 */
`;

export const MiniCancleButton = styled(MiniConfirmButton)`
  background-color: var(--red);
`;
