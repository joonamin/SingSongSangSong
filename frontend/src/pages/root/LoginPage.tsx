import React from "react";
import { Link, redirect } from "react-router-dom";

import styles from "./LoginPage.module.css";

import googleIcon from "./../../sources/imgs/auth/googleSymbol.png";
import naverIcon from "./../../sources/imgs/auth/naverIcon.png";
import kakoIcon from "./../../sources/imgs/auth/kakaoIcon.png";

import AuthInput from "../../components/auth/AuthInput";
import Button from "../../components/buttons/Button";
import { useInput } from "../../hooks/useInput";
import { idValidator, passwordValidator } from "../../utils/validator";
import { axiosInstance } from "../../hooks/api";
import LoginButton from "../../components/buttons/LoginButton";

const LoginPage = () => {
  const handleLogin = async (props: string) => {
    // console.log("실행은되니?");
    try {
      const response = await axiosInstance.request({
        method: "GET",
        url: `/oauth2/authorization/${props}`,
      });
      // console.log(response);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div className={`flex-col-center gap-30 ${styles.conatiner}`}>
        <div className={`flex-row-center gap-30 w-100 ${styles.socialBox}`}>
          <LoginButton name="kakao" url="kakao" img={kakoIcon} />
          <LoginButton name="google" url="google" img={googleIcon} />
          <LoginButton name="naver" url="naver" img={naverIcon} />
        </div>
        <p>
          계정이 없으신가요?{" "}
          <Link
            style={{ borderBottom: "1px solid black" }}
            to="/select-sign-up"
          >
            회원가입
          </Link>
        </p>
      </div>
    </>
  );
};

export default LoginPage;
