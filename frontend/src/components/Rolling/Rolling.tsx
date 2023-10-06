import { useState, useRef, useEffect } from 'react'
import styled from 'styled-components';
import { TextBox } from '../TextBox/TextBox';
import { ReactComponent as Bulb } from '../../assets/images/bulb.svg';

interface RollingProps {
    monthBorrow: number | null;
    monthRent: number | null;
}

export default function Rolling({monthBorrow, monthRent}: RollingProps) {
  const [idx, setIdx] = useState(0);
  const idxRef = useRef(0);
  
  const notice = [
      {
      no: 1,
      title: '이번달 입금 예정 금액',
      content: `${monthRent}원`,
      },
      {
      no: 2,
      title: '이번달 출금 예정 금액',
      content: `${monthBorrow}원`,
      }
  
  ]
      
  useEffect(() => {
    const intervalId = setInterval(() => {
      idxRef.current = (idxRef.current + 1) % notice.length;
      setIdx(idxRef.current);
    }, 3000);

    // 컴포넌트 언마운트 시에 클리어
    return () => clearInterval(intervalId);
  }, [notice]);

    return (
      <RollingDiv>
        <NoticeUl>
          <NoticeContainer style={{ transform: `translateY(${-30 * idx}px)` }}>
            {notice.map((item) => (
              <NoticeContent key={item.no}>
                <TextBox fontSize="12px">
                  <Bulb />
                  {item.title}
                </TextBox>
                <TextBox fontSize="12.5px" fontWeight="600">
                  {item.content}
                </TextBox>
              </NoticeContent>
            ))}
          </NoticeContainer>
        </NoticeUl>
      </RollingDiv>
    );
  };
      
const RollingDiv = styled.li`
width: 281px;
height: 30px;
background-color: var(--white);
border-radius: 9px;
box-shadow: 2px 2px 8px 0px rgba(0, 0, 0, 0.04);
padding: 0px 10px;
display: flex;
justify-content: center;
align-items: center;
margin: 0px auto;
`;

const NoticeUl = styled.ul`
  width: 275px;
  height: 30px;
  display: flex;
  justify-content: center;
  margin: 0px 0px 0 -45px;
  overflow: hidden;
`;

const NoticeContent = styled.li`
  width: 275px;
  height: 30px;
  line-height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style-type: none;
`;

const NoticeContainer = styled.div`
  transition: transform 1s;
`;
