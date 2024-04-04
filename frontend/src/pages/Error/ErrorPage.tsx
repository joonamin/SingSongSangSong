import React from "react";
import { useNavigate, useRouteError } from "react-router-dom";

import styles from "./ErrorPage.module.css";
import errorImg from "./../../sources/imgs/errorImg.jpg";

const ErrorPage = () => {
  const error: any = useRouteError();
  const navigate = useNavigate();

  console.log(error);
  return (
    <div className={`w-100 h-100 flex-col-center`}>
      <div className={`flex-col ${styles.errorImgBox}`}>
        <h1 className={`${styles.errorText}`}>
          {error.status} {error.statusText}!
          <p
            style={{ fontSize: "24px", cursor: "pointer" }}
            onClick={() => navigate("/")}
          >
            메인페이지로 돌아가기
          </p>
        </h1>
        <img src={errorImg} alt="errorImg" className={`${styles.errorImg}`} />
      </div>
    </div>
  );
};

export default ErrorPage;
