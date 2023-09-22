import React, { useState } from 'react';
// import styled from 'styled-components'
import { MiniConfirmButton } from '../../components/Button/Button'
import TransferModal from '../../components/TransferModal/TransferModal';
import PasswordModal from '../../components/Password/PasswordModal';
// import ModalBase from '../../components/Modal/ModalBase';

import DatePicker, { CalendarContainer } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale'; //한국어 설정
import styled from 'styled-components';

export default function TransferPage() {
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState<boolean>(false);

  const today = new Date();

  const [startDate, setStartDate] = useState<Date>(today);
  // const [endDate, setEndDate] = useState<Date>(today);

  const showModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const closePasswordModal = () => {
    setPasswordModalOpen(false);
  }

  const transferClicked = () => {
    setModalOpen(false);
    setPasswordModalOpen(true); // 패스워드 모달 열기
  }





  

  return (
    <div>
      <MiniConfirmButton onClick={() => {showModal();
          }}>
        송금
      </MiniConfirmButton>

      <CalCon>
        <StyledDatePicker // DatePicker의 styled-component명
                locale={ko} //한글
                dateFormat="yyyy.MM.dd"
                selected={startDate}
                // inline
                // withPortal
                onChange={(date: Date) => setStartDate(date)}
              />
      </CalCon>
          <button onClick={() => {
            console.log(startDate)
          }}>dddd</button>


      {modalOpen? (<TransferModal closeModal={closeModal} transferClicked={transferClicked} />): null}
      {passwordModalOpen? (<PasswordModal closePasswordModal={closePasswordModal} />): null}
    </div>
  )
}


const StyledDatePicker = styled(DatePicker)`
  width: 122px;
  background-color: transparent;
  border: none;
  /* height: 48px;
  border: none;
  font-weight: 400;
  font-size: 16px;
  line-height: 100%;
  padding: 20px;
  background-color: transparent;
  color: #707070;
  position: absolute;
  top: -48px;
  left: 5px; */
`;

const CalCon = styled.div`
  position: absolute;
  top: 100px;
  width: 100%;
  display: flex;
  justify-content: center;
`