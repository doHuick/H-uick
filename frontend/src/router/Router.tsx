import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Mainpage from '../pages/MainPage/Mainpage';
import SignUppage from '../pages/SignUppage/SignUppage';
import MyPage from '../pages/MyPage/MyPage';
import ChatbotPage from '../pages/ChatbotPage/ChatbotPage';
import TransferPage from '../pages/TransferPage/TransferPage';
import ContractTempPage from '../pages/ContractTempPage/ContractTempPage';

export default function Router() {
  return (
    <BrowserRouter>
      <Routes>'react/jsx-runtime' 모듈 또는 해당 형식 선언을 찾을 수 
      <Route path="/main" element={<Mainpage />}></Route>
        <Route path="/signup" element={<SignUppage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
        <Route path="/chatbot" element={<ChatbotPage />}></Route>
        <Route path="/transfer" element={<TransferPage />}></Route>
        <Route path="/contractTemp" element={<ContractTempPage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
