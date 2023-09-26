import { useState } from 'react';
import styled from 'styled-components';
import { TextBox } from '../../components/TextBox/TextBox';
import { Main } from '../../style';
import NavBar from '../../components/NavBar/NavBar';
import { ReactComponent as Bar } from '../../assets/icons/bar.svg';
import { ConfirmButton } from '../../components/Button/Button';
import axios, { BASE_URL } from '../../api/apiController';
import { useNavigate } from 'react-router-dom';

interface StyledInputProps {
  value: string;
}

export default function SignUppage() {
  const [name, setName] = useState<string>('');
  const [idFront, setIdFront] = useState<string>('');
  const [idBack, setIdBack] = useState<string>('');
  const [address, setAddress] = useState<string>('');
  const navigate = useNavigate();

  const sendData = async () => {
    try {
      await axios.post(
        `${BASE_URL}/users/rrn`,
        {
          name: `${name}`,
          rrn: `${idFront}${idBack}`,
          address: `${address}`,
        },
        {
          headers: { Authorization: localStorage.getItem('access_token') },
        },
      );
      navigate('/welcome');
    } catch (error) {
      console.error('서버 요청 실패:', error);
    }
  };

  return (
    <Main>
      <TextBox fontSize="22px" fontWeight="700" margin="32px 0 0 35px">
        회원님, <br />
        정보가 제대로 입력되었나요?
      </TextBox>
      <TextBox
        fontSize="18px"
        fontWeight="700"
        margin="20px 0 0 35px"
        color="var(--font-gray)"
      >
        틀림없이 정확히 기입되었는지 확인해주세요. <br />
        오기입된 정보는 수정해주시기 바랍니다.
      </TextBox>
      <TextBox
        fontSize="18px"
        fontWeight="700"
        margin="50px 0 17px 35px"
        color="var(--font-gray)"
      >
        이름
      </TextBox>
      {/* 이름 입력 input */}
      <StyledInput
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <TextBox
        fontSize="18px"
        fontWeight="700"
        margin="32px 0 17px 35px"
        color="var(--font-gray)"
      >
        주민등록번호
      </TextBox>
      <Id>
        {/* 주민등록번호 앞자리 입력 input */}
        <IdInput
          type="text"
          value={idFront}
          onChange={(e) => setIdFront(e.target.value)}
        />
        <Bar />
        {/* 주민등록번호 뒷자리 입력 input */}
        <IdInput
          type="text"
          value={idBack}
          onChange={(e) => setIdBack(e.target.value)}
        />
      </Id>
      <TextBox
        fontSize="18px"
        fontWeight="700"
        margin="32px 0 17px 35px"
        color="var(--font-gray)"
      >
        주소
      </TextBox>
      {/* 주소 입력 input */}
      <StyledInput
        type="text"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
      />
      <CenterDiv>
        <ConfirmButton onClick={sendData}>확인</ConfirmButton>
      </CenterDiv>
      <NavBar />
    </Main>
  );
}

const StyledInput = styled.input<StyledInputProps>`
  width: 323px;
  height: 30px;
  background-color: var(--whtie);
  margin-left: 35px;
  border: none;
  border-bottom: 3px solid var(--gray);
  font-size: 18px;
  font-weight: 500;
  &:focus {
    border-bottom: 3px solid var(--huick-blue);
    outline: none;
  }
  &:active {
    border-color: var(--huick-blue);
  }
`;

const IdInput = styled(StyledInput)`
  width: 133px;
  margin: 0px;
`;

const Id = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
`;

// [23-09-12] 임시 가운데 정렬 용도 - 향후 수정 필
const CenterDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 70px;
`;
