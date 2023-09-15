import React, { useState } from 'react';
// import styled from 'styled-components'
import { MiniConfirmButton } from '../../components/Button/Button'
import TransferModal from '../../components/TransferModal/TransferModal';
import PasswordModal from '../../components/Password/PasswordModal';
// import ModalBase from '../../components/Modal/ModalBase';

export default function TransferPage() {
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState<boolean>(false);

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

      {modalOpen? (<TransferModal closeModal={closeModal} transferClicked={transferClicked} />): null}
      {passwordModalOpen? (<PasswordModal closePasswordModal={closePasswordModal} />): null}
    </div>
  )
}
