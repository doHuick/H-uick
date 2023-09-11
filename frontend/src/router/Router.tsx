import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Mainpage from '../pages/MainPage/Mainpage';
import SignUppage from '../pages/SignUppage/SignUppage';
import MyPage from '../pages/MyPage/MyPage';
import ChatbotPage from '../pages/ChatbotPage/ChatbotPage';

export default function Router() {
  return (
    <BrowserRouter>
      <Routes>
      <Route path="/main" element={<Mainpage />}></Route>
        <Route path="/signup" element={<SignUppage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
        <Route path="/chatbot" element={<ChatbotPage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
