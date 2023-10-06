import { styled } from 'styled-components';

interface TextProp {
  fontSize?: string;
  fontWeight?: string;
  color?: string;
  textAlign?: string;
  margin?: string;
}

export const TextBox = styled.div<TextProp>`
  display: flex;
  justify-content: left;
  align-items: center;
  font-size: ${(props => props.fontSize)};
  font-weight: ${(props => props.fontWeight)};
  color: ${(props => props.color)};
  text-align: ${(props => props.textAlign)};
  margin: ${(props => props.margin)};
`;
