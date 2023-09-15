import { styled } from 'styled-components';

type bgProps = {
  backgroundColor?: string;
}

export const Main = styled.div<bgProps>`
  position: relative;
  width: 100%;
  height: 100%;
  background-color: ${(props => props.backgroundColor )};
  overflow-x: clip;
  overflow-y: scroll;
`
