import { useState, useRef } from 'react';
import styled from 'styled-components';
import { TextBox } from '../../components/TextBox/TextBox';
import { Main } from '../../style';
import { ReactComponent as Bar } from '../../assets/icons/bar.svg';
import { ConfirmButton } from '../../components/Button/Button';
import axios, { BASE_URL } from '../../api/apiController';
import { useNavigate } from 'react-router-dom';
import SignupSignModal from '../../components/SignModal/SignupSignModal';
import SignupPasswordModal from '../../components/Password/SignupPasswordModal';
import toast, { toastConfig } from 'react-simple-toasts';
import { createWallet } from '../../utils/wallet';

interface StyledInputProps {
  value: string;
}

interface DataProps {
  address: string;
}

export default function SignUppage() {
  const [name, setName] = useState<string>('');
  const [idFront, setIdFront] = useState<string>('');
  const [idBack, setIdBack] = useState<string>('');
  const [address, setAddress] = useState<string>('');
  const [phoneNumber, setPhoneNumber] = useState<string>('');
  const navigate = useNavigate();

  const sendData = async () => {
    const [walletAddress, walletKey] = createWallet();

    try {
      await axios.post(
        `${BASE_URL}/users/rrn`,
        {
          name: `${name}`,
          rrn: `${idFront}${idBack}`,
          address: `${address}`,
          phone_number: `${phoneNumber}`,
          wallet_address: `${walletAddress}`,
          wallet_key: `${walletKey}`,
        },
        {
          headers: { Authorization: localStorage.getItem('access_token') },
        },
      )
        .then(() => {
          showSignModal();
        })
    } catch (error) {
      toast('모든 정보를 빠짐없이 정확히 입력해주세요.');
    }
  };

  const wrapRef = useRef<HTMLDivElement | null>(null);

  const setAddr = () => {
    // wrapRef를 사용하여 요소를 보이게 함.
    if (wrapRef.current) {
      wrapRef.current.style.display = 'block';
    }
    // @ts-ignore
    new daum.Postcode({
      width: '100%',
      oncomplete: function (data: DataProps) {
        setAddress(data.address);

        // 검색이 완료되면 wrapRef를 사용하여 요소를 숨김.
        if (wrapRef.current) {
          wrapRef.current.style.display = 'none';
        }
      },
    }).embed(wrapRef.current);
  };

  toastConfig({
    theme: 'frosted-glass',
    position: 'top-center',
    maxVisibleToasts: 1,
  });

  // 모달 띄우기
  const [signModalOpen, setSignModalOpen] = useState(false);
  const [passwordModalOpen, setPasswordModalOpen] = useState(false);

  const showSignModal = () => {
    setSignModalOpen(true);
  };

  const closeSignModal = () => {
    setSignModalOpen(false);
  };

  const closePasswordModal = () => {
    setPasswordModalOpen(false);
    navigate('/welcome');
  };

  const handleSave = (imageData: string) => {
    axios.patch(
      `${BASE_URL}/users/signature`,
      {
        signatureBase64: imageData,
      },
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      },
    );

    // 내 정보 받아오기
    axios
      .get(`${BASE_URL}/users/me`, {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .catch((err) => {
        console.log(err);
      });

    setTimeout(() => {
      closeSignModal();
      setPasswordModalOpen(true);
    }, 250);
  };

  const handlePassword = (userPassword: string) => {
    axios.patch(
      `${BASE_URL}/users/me`,
      {
        password: userPassword,
      },
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      },
    );
  };

  return (
    <Main backgroundColor="var(--white)">
      <TitleFrame>
        <TextBox fontSize="22px" fontWeight="700" margin="0 0 12px 0">
          회원님, <br />
          정보가 제대로 입력되었나요?
        </TextBox>
        <TextBox fontSize="16px" fontWeight="500" color="var(--font-gray)">
          틀림없이 정확히 기입되었는지 확인해주세요. <br />
          오기입된 정보는 수정해주시기 바랍니다.
        </TextBox>
      </TitleFrame>

      <InputFrame>
        <TextBox fontSize="18px" fontWeight="500" color="var(--font-gray)">
          이름
        </TextBox>
        {/* 이름 입력 input */}
        <StyledInput
          type="text"
          value={name}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setName(e.target.value)
          }
        />

        <TextBoxSignup
          fontSize="18px"
          fontWeight="500"
          color="var(--font-gray)"
        >
          주민등록번호
        </TextBoxSignup>

        <Id>
          {/* 주민등록번호 앞자리 입력 input */}
          <IdInput
            type="string"
            maxLength={6}
            value={idFront}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setIdFront(e.target.value)
            }
          />
          <Bar />
          {/* 주민등록번호 뒷자리 입력 input */}
          <IdInput
            type="password"
            maxLength={7}
            value={idBack}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setIdBack(e.target.value)
            }
          />
        </Id>

        <TextBoxSignup
          fontSize="18px"
          fontWeight="500"
          color="var(--font-gray)"
        >
          주소
        </TextBoxSignup>
        {/* 주소 입력 input */}
        <StyledInput
          id="addr"
          type="text"
          value={address}
          onClick={setAddr}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setAddress(e.target.value)
          }
        />
        <PostBox ref={wrapRef} />

        <TextBoxSignup
          fontSize="18px"
          fontWeight="500"
          color="var(--font-gray)"
        >
          전화번호
        </TextBoxSignup>
        {/* 전화번호 입력 input */}
        <StyledInput
          type="string"
          maxLength={11}
          value={phoneNumber}
          placeholder='ex) 01012345678'
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setPhoneNumber(e.target.value)
          }
        />
      </InputFrame>

      <CenterDiv>
        <ConfirmButtonRenewed onClick={sendData}>확인</ConfirmButtonRenewed>
      </CenterDiv>

      {signModalOpen ? <SignupSignModal onSave={handleSave} /> : null}

      {passwordModalOpen ? (
        <SignupPasswordModal
          closePasswordModal={closePasswordModal}
          handlePassword={handlePassword}
        />
      ) : null}
    </Main>
  );
}

const TitleFrame = styled.div`
  position: relative;
  width: calc(100% - 58px);
  margin-top: 64px;
  margin-left: 28px;
`;

const InputFrame = styled(TitleFrame)`
  position: relative;
  width: calc() (100% - 56px);
  margin-top: 60px;
  margin-left: 28px;
`;

const TextBoxSignup = styled(TextBox)`
  margin-top: 46px;
`;

const StyledInput = styled.input<StyledInputProps>`
  position: relative;
  width: 100%;
  height: 30px;
  background-color: var(--whtie);
  border: none;
  border-bottom: 2px solid var(--gray);
  font-size: 18px;
  font-weight: 500;
  &:focus {
    border-bottom: 2px solid var(--huick-blue);
    outline: none;
  }
  &:active {
    border-color: var(--huick-blue);
  }
  &::-webkit-inner-spin-button,
  ::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;

const IdInput = styled(StyledInput)`
  width: calc(40%);
  margin: 0px;
`;

const Id = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const CenterDiv = styled.div`
  position: relative;
  width: calc(100% - 56px);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 80px;
  margin-left: 28px;
`;

const ConfirmButtonRenewed = styled(ConfirmButton)`
  position: relative;
  width: 100%;
  border-radius: 16px;
  font-size: 17px;
  font-weight: 600;
`;

const PostBox = styled.div`
  display: none;
  position: relative;
`;
