import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Mainpage from '../pages/MainPage/Mainpage';
import SignUppage from '../pages/SignUppage/SignUppage';
import MyPage from '../pages/MyPage/MyPage';
import ChatbotPage from '../pages/ChatbotPage/ChatbotPage';
import LoginPage from '../pages/LoginPage/LoginPage';
import KakaoAuthHandler from '../pages/LoginPage/KakaoAuthHandler';
import PrivateRoute from './PrivateRoute';
import Welcomepage from '../pages/SignUppage/Welcomepage';
import AuthenticatedRoute from './AuthenticatedRoute';
import SharePage from '../pages/SharePage/SharePage';
import NotFoundPage from '../pages/NotFoundPage/NotFoundPage';

export default function Router() {
  return (
    <BrowserRouter>

      <Routes>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/oauth/redirect" element={<KakaoAuthHandler />}/>
        
        <Route element={<PrivateRoute />}>
          <Route path="/" element={<Mainpage />}></Route>
          {/* <Route element={<AuthenticatedRoute />}> */}
            <Route path="/signup" element={<SignUppage />}></Route>
            <Route path="/welcome" element={<Welcomepage/>}></Route>
          {/* </Route> */}
          <Route path="/mypage" element={<MyPage />}></Route>
          <Route path="/chatbot" element={<ChatbotPage />}></Route>
          <Route path="/share/:contractId" element={<SharePage />}></Route>
          <Route path="/404" element={<NotFoundPage />}></Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
