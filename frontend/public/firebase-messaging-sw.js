importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js");

const config = {
  //프로젝트 설정 > 일반 > 하단의 내 앱 부분 복사
  apiKey: "AIzaSyAxrW-Pjkwzwa8UJ8rkqRkK9_86-PYFHzI",
  authDomain: "huick-a408.firebaseapp.com",
  projectId: "huick-a408",
  storageBucket: "huick-a408.appspot.com",
  messagingSenderId: "843755551001",
  appId: "1:843755551001:web:7411b1dfde44b5e4dded88",
  measurementId: "G-GWKML16ELJ"
};

// Initialize Firebase
firebase.initializeApp(config);

const messaging = firebase.messaging();

//백그라운드 서비스워커 설정
messaging.onBackgroundMessage(messaging, (payload) => {
  console.log(
    "[firebase-messaging-sw.js] Received background message ",
    payload
  );
  
  // Customize notification here
  const notificationTitle = "Background Message Title";
  const notificationOptions = {
    body: payload,
    icon: "/firebase-logo.png",
  };
  
  self.registration.showNotification(notificationTitle, notificationOptions);
});