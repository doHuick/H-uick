// import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Main } from '../../style';
import HeadBar from '../../components/HeadBar/HeadBar';
import { MiniConfirmButton } from '../../components/Button/Button';
import { useNavigate, useParams } from 'react-router-dom';
// import axios, { BASE_URL } from '../../api/apiController';

// import { Worker, Viewer, LocalizationMap } from '@react-pdf-viewer/core';
// import '@react-pdf-viewer/core/lib/styles/index.css';

// import {Document, Page, pdfjs} from 'react-pdf';
// pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;

import { MobilePDFReader } from 'react-read-pdf';
import './PDFPage.css'

export default function PDFPage() {
  const params = useParams();
  const contractId = params.contractId
  const navigate = useNavigate()
  
  const pdfURL = `https://huick-bucket.s3.ap-northeast-2.amazonaws.com/contract/${contractId}.pdf`

  // useEffect(() => {
  //   axios.get(`${BASE_URL}/users/me`, {
  //     headers: { Authorization: localStorage.getItem('access_token') },
  //   }).then((res) => {
  //     setUserInfo(res.data)
  //   })
  //   .catch(() => {
  //   })
  // }, []);
  const goBack = () => {
    navigate(-1)
  }
  return (
    <Main backgroundColor='var(--white)'>
      <HeadBar pageName="차용증" />
      <PDFBackground>
        <PDFFrame>
          <MobilePDFReader url={pdfURL} />

        </PDFFrame>
      </PDFBackground>



        <ButtonFrame>
          <Button onClick={goBack}>
            확인
          </Button>
        </ButtonFrame>
    </Main>
  );
}


const PDFBackground = styled.div`
  position: relative;
  left: -50%;
  width: 200%;
  height: 75%;
  margin-top: 72px;
  background-color: var(--background);
  box-shadow: inset 0px 0px 12px rgba(0,0,0, 0.08);
  display: flex;
  justify-content: center;
`

const PDFFrame = styled.div`
  position: relative;
  width: 50%;
  height: 100%;
  overflow-y: hidden;
`

const ButtonFrame = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 28px;
`

const Button = styled(MiniConfirmButton)`
  position: relative;
  margin-left: 30px;
  margin-right: 30px;
  height: 46px;
  width: 100%;
  font-size: 18px;
  font-weight: 600;
`