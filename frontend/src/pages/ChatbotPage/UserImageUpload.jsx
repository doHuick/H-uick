import React, { useState } from "react";
import styled from "styled-components";
import axios, { BASE_URL } from '../../api/apiController';

// import './UserImageUpload.css'
// import UploadIcon from '../../assets/icons/upload-image-icon.png'
// import UploadIcon from '../../../public/upload-image-icon.png'

const UserImageUpload = (props) => {
  const [uploadedImage, setUploadedImage] = useState(null);
  const [disableUpload, setDisableUpload] = useState(false); // Add state variable

  const onChangeImage = e => {
    const file = e.target.files[0];
    const imageUrl = URL.createObjectURL(file);
    localStorage.setItem("userKakaoCaptureURL", JSON.stringify(imageUrl))
    setUploadedImage(imageUrl);
  };

  const handleUploadClick = () => {
    const url = localStorage.getItem('userKakaoCaptureURL')
    setDisableUpload(true);
    axios.post(
      `${BASE_URL}/contracts`,
      {
        user_id: 1,
        contract_tmp_key: null,
        file: url,
      },
      {
        headers: { Authorization: localStorage.getItem('access_token') },
      },
    )
    .then((res) => {
      console.log(res.data)
    })

  };

  return (
    <UploadFrame>
      <UploadChat>
        <input type="file" id="file" accept="image/*" onChange={onChangeImage} disabled={disableUpload} />
        <label htmlFor="file" className={disableUpload ? "disabled" : ""}>
        {uploadedImage ? (
            <img className="uploaded-image" src={uploadedImage} alt="프로필 없을때" />
          ) : (
            <img src="/upload-image-icon.png" alt="프로필사진" />
          )}
          </label>
      </UploadChat>
      {uploadedImage ? (
        <ChatbotButton key={1} onClick={props.actionProvider.handleUpload}><span onClick={handleUploadClick}>업로드</span></ChatbotButton>
      ) : null}
    </UploadFrame>
    )

}


export default UserImageUpload

const UploadFrame = styled.div`
  width: 100%;
  display: flex;
  align-items: flex-end;
  flex-direction: column;
`

const UploadChat = styled.div`
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.9rem;
  /* color: #585858; */
  font-weight: medium;
  position: relative;
  text-align: left;
  color: var(--black);
  margin-top: -24px;
  margin-bottom: 24px;
  width: 119.047px;
  height: 119.047px;
  display: flex;
  justify-content: center;
  align-items: center;

  input[type="file"] {
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
  border: 0;
}

.uploaded-image {
  width: 100%;
  height: auto;
}

label {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  &:hover {
    cursor: pointer;
  }
  overflow: hidden;
}

img {
  width: 50%;
  height: 50%;
}
`

const ResizedUploadIcon = styled.img`
  width: 50%;
  height: 50%;
  filter: drop-shadow(1px 1px 8px rgba(0,0,0,0.04))
`

const ChatbotButton = styled.div`
  margin-left: 12px;;
  width: auto;
  padding-left: 12px;
  padding-right: 12px;
  height: 32px;
  border: none;
  border-radius: 24px;
  background-color: #f1f1f1;
  display: flex;
  justify-content: center;
  align-items: center;
  color: var(--secondary);
  font-size: 14px;
  margin-top: -12px;
  margin-bottom: 24px;
`