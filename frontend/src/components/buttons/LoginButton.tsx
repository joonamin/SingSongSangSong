import React, { useEffect } from "react";
import styles from "./LoginButton.module.css";
import { axiosInstance } from "../../hooks/api";
import axios from "axios";
import { getCookie } from "../../utils/cookie";

type ProspsType = {
  name: string;
  url: string;
  img: string;
};

const LoginButton = ({ name, url, img }: ProspsType) => {
  const redirectUrl = `${process.env.REACT_APP_API_URL}oauth2/authorization/${url}`;
  // const test = async () => {
  //   try {
  //     const response = axios({
  //       method: "GET",
  //       url: `${process.env.REACT_APP_API_URL}oauth2/authorization/${url}`,
  //     });
  //     console.log(response);
  //   } catch (error) {
  //     console.log(error);
  //   }
  // };
  // useEffect(() => {
  //   console.log(getCookie("accessToken"));
  // }, []);

  const handleRedirect = () => {
    window.location.href = redirectUrl;
  };

  return (
    <div className={`${styles.loginBtn}`} onClick={handleRedirect}>
      <img src={img} alt={name} />
    </div>
  );
};

export default LoginButton;
