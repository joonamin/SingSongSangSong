import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Outlet, useLocation, useNavigate, Navigate } from "react-router-dom";

import styles from "./MainLayout.module.css";
import Sidebar from "../../components/sidebar/Sidebar";
import MusicPlayer from "../../components/musicPlayer/MusicPlayer";
import ScrollToTop from "../../utils/ScrollToTop";
import { getCookie, setCookie } from "../../utils/cookie";
import { userAction } from "../../store/userSlice";
import { RootState } from "../../store";
import { axiosInstance } from "../../hooks/api";

const MainLayout = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let login = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();

  useEffect(() => {
    const getUserInfo = async () => {
      try {
        const response = await axiosInstance.request({
          url: "/artist/profile/me",
          method: "GET",
        });
        if (response) {
          dispatch(userAction.setLogin(response?.data?.data));
        }
      } catch (error) {
        console.log(error);
      }
    };

    const searchParams = new URLSearchParams(location.search);
    const accessToken = searchParams.get("accessToken");
    if (accessToken) {
      setCookie("accessToken", accessToken, {
        path: "/",
        secure: true,
        sameSite: "none",
      });
      getUserInfo();
    }
  }, []);

  useEffect(() => {
    const getUserInfo = async () => {
      try {
        const response = await axiosInstance.request({
          url: "/artist/profile/me",
          method: "GET",
        });
        if (response) {
          dispatch(userAction.setLogin(response?.data?.data));
        }
      } catch (error) {
        console.log(error);
      }
    };
    const accessToken = getCookie("accessToken");
    if (accessToken && !login.isLogin) {
      getUserInfo();
    }
  }, []);

  if (!login.isLogin) {
    return <Navigate to="/login" />;
  }

  return (
    <>
      <ScrollToTop />
      <Sidebar />
      <main className={styles.container}>
        <Outlet />
        <MusicPlayer />
      </main>
    </>
  );
};

export default MainLayout;
