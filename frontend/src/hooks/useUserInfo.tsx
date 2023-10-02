import { useState, useEffect } from 'react';
import axios, { BASE_URL } from '../api/apiController';

export const useUserInfo = (): boolean => {
  const [nowUserInfo, setNowUserInfo] = useState<Boolean | null>();

  useEffect(() => {
    axios
      .get(`${BASE_URL}/users/me`, {
        headers: { Authorization: localStorage.getItem('access_token') },
      })
      .then((res) => {
        const data = res.data;
        if (data.name && data.rrn && data.address && data.phone_number) {
          setNowUserInfo(true);
        } else {
          setNowUserInfo(false);
        }
      });
  });

  return !!nowUserInfo;
};
