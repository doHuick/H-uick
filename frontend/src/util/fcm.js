import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const config = {
  //프로젝트 설정 > 일반 > 하단의 내앱에서 확인
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY ,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID,
  measurementId: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID,
};

const app = initializeApp(config);
const messaging = getMessaging();
console.log(messaging)
//토큰값 얻기
//   vapidKey: import.meta.env.FIREBASE_VAPID_KEY
getToken(messaging, {
    vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY
})
  .then((currentToken) => {
    if (currentToken) {
      // Send the token to your server and update the UI if necessary
      // ...
      console.log("currentToiken", currentToken);
      localStorage.setItem("device_token", currentToken);
    } else {
      // Show permission request UI
      console.log(
        "No registration token available. Request permission to generate one."
      );
      // ...
    }
  })
  .catch((err) => {
    console.log("An error occurred while retrieving token. ", err);
    // ...
  });

//포그라운드 메시지 수신
onMessage(messaging, (payload) => {
  console.log("Message received. ", payload);
  // ...
});