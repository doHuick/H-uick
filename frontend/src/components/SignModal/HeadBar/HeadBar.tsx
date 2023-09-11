import React from 'react'
import { styled } from 'styled-components'
import { ReactComponent as LeftArrow } from '../../../assets/icons/left-arrow.svg'

interface HeadBarProps {
  pageName: string;
  bgColor: string;
}

export default function HeadBar({ pageName, bgColor } : HeadBarProps) {
  return (
    <HeadBarContainer style={{ backgroundColor: `var(--${bgColor})` }} >
        <HeadBarInner>
          <HeadBarLeft>
            <LeftArrow />
            <span>&nbsp;&nbsp;{pageName}</span>
          </HeadBarLeft>
          <HeadBarRight>
            
          </HeadBarRight>
        </HeadBarInner>
    </HeadBarContainer>
  )
}

const HeadBarContainer = styled.div`
  position: fixed;
  top: 0px;
  width: 100%;
  height: 96px;
  z-index: 1;
  background-color: var(--background);
`

const HeadBarInner = styled.div`
  position: relative;
  margin-top: 60px;
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