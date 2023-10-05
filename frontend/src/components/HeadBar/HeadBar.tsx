import { styled } from 'styled-components'
import { ReactComponent as LeftArrow } from '../../assets/icons/left-arrow.svg'
import { useNavigate } from 'react-router-dom';

interface HeadBarProps {
  pageName: string;
  color?: string;
}

export default function HeadBar({ pageName, color }: HeadBarProps) {
  const navigation = useNavigate();

  return (
    <HeadBarContainer pageName={pageName} color={color}>
        <HeadBarInner>
          <HeadBarLeft>
            <LeftArrow onClick={() => navigation('/')}/>
            <span>&nbsp;&nbsp;{pageName}</span>
          </HeadBarLeft>
          <HeadBarRight>
            
          </HeadBarRight>
        </HeadBarInner>
    </HeadBarContainer>
  )
}

const HeadBarContainer = styled.div<HeadBarProps>`
  position: fixed;
  top: 0px;
  width: 100%;
  height: 64px;
  z-index: 1;
  background-color: ${(props) => props.color || 'var(--white)'};
`

const HeadBarInner = styled.div`
  position: relative;
  margin-top: 24px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 23px;
  font-weight: bold;
`

const HeadBarLeft = styled.div`
  margin-left: 22px;
  display: flex;
  align-items: center;
`

const HeadBarRight = styled.div`
  
`