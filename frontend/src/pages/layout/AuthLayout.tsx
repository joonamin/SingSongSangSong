import { Link, Outlet, useLocation, useNavigate, Navigate } from "react-router-dom";
import { useEffect } from "react";

import titleImg from "./../../sources/imgs/title/logo_투명.png";
import styles from "./AuthLayout.module.css";
import Notice from "../../components/auth/Notice";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { getCookie } from "../../utils/cookie";
import { axiosInstance } from "../../hooks/api";
import { userAction } from "../../store/userSlice";

const AuthLayout = () => {
  const { pathname } = useLocation();
  const naviagte = useNavigate();
  const userSlice = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();

  useEffect(() => {
    const checkUser = async () => {
      try {
        const response = await axiosInstance.request({
          method: "GET",
          url: "/artist/profile/me",
        });
        dispatch(userAction.setLogin(response?.data?.data));
        naviagte("/trend");
      } catch (error) {
        console.log(error);
      }
    };
    if (!userSlice.isLogin) {
      checkUser();
    }
  });

  if(userSlice.isLogin) {
    return <Navigate to="/" />;
  }

  return (
    <>
      <div className={styles.container}>
        <div className={styles.title}>
          <Link to="/">
            <img src={titleImg} alt="" />
          </Link>
        </div>
        <Notice pathname={pathname} />
        <Outlet />
      </div>
    </>
  );
};

export default AuthLayout;
