import React, { useState, useEffect } from 'react';
import styled, { keyframes } from 'styled-components'
import { ReactComponent as PasswordDelete } from '../../assets/icons/password-delete.svg'

interface SignupPasswordModalProps {
  closePasswordModal: () => void;
  handlePassword: (userPassword: string) => void;
}

interface DarkBackgroundProps {
  isClosing: boolean;
}

interface ModalContainerProps {
  isClosing: boolean;
}

function generateUniqueRandomNumbers(count: number): number[] {
  const uniqueNumbers: number[] = [];
  while (uniqueNumbers.length < count) {
    const randomNumber = Math.floor(Math.random() * 10);
    if (!uniqueNumbers.includes(randomNumber)) {
      uniqueNumbers.push(randomNumber);
    }
  }
  return uniqueNumbers;
}

export default function SignupPasswordModal({ closePasswordModal, handlePassword }: SignupPasswordModalProps) {
  const [isClosing, setIsClosing] = useState(false);
  const [numList, setNumList] = useState<number[]>([]);
  const [selectedPassword, setSelectedPassword] = useState<string>('');
  const [isPasswordComplete, setIsPasswordComplete] = useState(false); // 비밀번호가 완료되었는지 여부


  useEffect(() => {
    // 모달이 열릴 때 랜덤한 숫자 배열 생성 (중복 없이)
    const randomNumList = generateUniqueRandomNumbers(10);
    setNumList(randomNumList);
  }, []);


  const closeAndShare = () => {
    setIsClosing(true);
    setTimeout(() => {
      closePasswordModal();
    }, 310);
  };

  const handlePasswordClick = (password: string) => {
    if (!isPasswordComplete) {
      setSelectedPassword((prevPassword) => {
        // 현재 선택된 비밀번호와 새로 클릭한 비밀번호를 합쳐서 새로운 비밀번호를 만듭니다.
        const newCombinedPassword = prevPassword + password;
  
        // 만약 비밀번호가 4자리를 초과하면 비밀번호를 리셋하고 완료 플래그를 설정합니다.
        if (newCombinedPassword.length == 6) {
          // console.log(newCombinedPassword)
          handlePassword(newCombinedPassword)
          closeAndShare();
        }

        return newCombinedPassword;
      });
    }
  };

  const handlePasswordDelete = () => {
    if (selectedPassword.length > 0) {
      setSelectedPassword((prevPassword) => prevPassword.slice(0, -1));
      setIsPasswordComplete(false); // 비밀번호를 삭제하면 완료 플래그도 다시 false로 설정
    }
  };

  return (
    <>
      <DarkBackground isClosing={isClosing}/>
      <ModalContainer isClosing={isClosing}>
      
        <TransferModalModalUpperBar>
          <TransferModalModalTitle></TransferModalModalTitle>
          <TransferModalModalUpperButtons>
          </TransferModalModalUpperButtons>
        </TransferModalModalUpperBar>

        <PasswordFrame>
        <span>비밀번호</span>
        <PasswordEllipseFrame>
          {Array.from({ length: 6 }, (_, index) => (
            <React.Fragment key={index}>
              {index < selectedPassword.length ? (
                <FilledEllipse />
              ) : (
                <EmptyEllipse />
              )}
            </React.Fragment>
          ))}
        </PasswordEllipseFrame>
      </PasswordFrame>
        
        <PasswordPadFrame>
          <PasswordNumPad style={{ marginTop: '6px'}}>
            {numList.map((num) => (
              <PasswordNum
                key={num}
                onClick={() => handlePasswordClick(num.toString())}
                className={isPasswordComplete ? 'disabled' : ''}
              >
                {num}
              </PasswordNum>
            ))}
          </PasswordNumPad>
          <PasswordDeleteButton onClick={handlePasswordDelete}>
            <PasswordDelete/>
          </PasswordDeleteButton>

        </PasswordPadFrame>

      </ModalContainer>
    </>
  )
}


// const fadeIn = keyframes`
//   from {
//     opacity: 0;
//   }
//   to {
//     opacity: 1; // 원하는 어두운 배경의 최종 투명도 설정
//   }
// `;

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0; // 원하는 어두운 배경의 최종 투명도 설정
  }
`;

const slideIn = keyframes`
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0%);
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

const DarkBackground = styled.div<DarkBackgroundProps>`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); // 어두운 배경의 색상과 투명도 조절
  z-index: 2; // 모달 뒤에 위치하도록 설정
  animation: ${({ isClosing }) => (isClosing ? fadeOut : '' )} 0.32s ease-in-out;

`;

const ModalContainer = styled.div<ModalContainerProps>`
  position: fixed;
  z-index: 3;
  width: 100%;
  height: 640px;
  bottom: 0;
  left: 0;
  background-color: var(--white);
  border-radius: 30px 30px 0px 0px;
  box-shadow: 0px -5px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  animation: ${({ isClosing }) =>
  (isClosing ? slideOut : slideIn)} 0.35s ease-in-out;
`;


const TransferModalModalUpperBar = styled.div`
  position: relative;
  margin-top: 32px;
  width: 100%;
  height: 36px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 24px;
  font-weight: bold;
`

const TransferModalModalTitle = styled.span`
  margin-left: 30px;
`

const TransferModalModalUpperButtons = styled.div`
  margin-right: 30px;
  display: flex;
  align-items: center;
  
`

const PasswordFrame = styled.div`
  width: 100%;
  text-align: center;
  margin-top: 40px;
  font-size: 24px;
  font-weight: bold;
`

const PasswordEllipseFrame = styled.div`
  width: 100%;
  height: 56px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 16px;
  margin-bottom: 20px;

`

const EmptyEllipse = styled.div`
  width: 24px;
  height: 24px;
  background-color: var(--background);
  border: 1.2px solid #C8DCF7;
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.08);
  border-radius: 50px;
  margin-left: 6px;
  margin-right: 6px;
`

const FilledEllipse = styled.div`
  width: 25px;
  height: 25px;
  background-color: var(--huick-blue);
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.08);
  border-radius: 50px;
  margin-left: 6px;
  margin-right: 6px;

`

const PasswordPadFrame = styled.div`
  position: absolute;
  bottom: 0px;
  width: 100%;
  height: 320px;
  background-color: var(--huick-blue);
`

const PasswordNumPad = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  font-size: 24px;
  color: var(--white);
  font-weight: bold;
`

const PasswordNum = styled.div`
  width: 33.3%;
  height: 72px;
  display: flex;
  justify-content: center;
  align-items: center;
`

const PasswordDeleteButton = styled.div`
  position: absolute;
  width: 33.3%;
  height: 72px;
  display: flex;
  justify-content: center;
  align-items: center;
  right: 0px;
  top: 222px;
`