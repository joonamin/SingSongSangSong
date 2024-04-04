import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import titleIcon from "./../../sources/imgs/title/logo_small_투명.png";
import title from "./../../sources/imgs/title/logo_투명.png";
import { FaChartLine, FaMusic, FaRegQuestionCircle } from "react-icons/fa";
import { TbReportAnalytics } from "react-icons/tb";
import { RxPerson } from "react-icons/rx";
import { MdOutlineLogin, MdOutlineLogout } from "react-icons/md";
import { GiPoisonGas } from "react-icons/gi";

import styles from "./Sidebar.module.css";
import { RootState } from "../../store";
import { userAction } from "../../store/userSlice";

const Sidebar = () => {
  const navigate = useNavigate();
  const userSlice = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();

  let isLogin = userSlice.isLogin;
  const handleLogout = () => {
    dispatch(userAction.setLogout());
  };

  const navigateMain = () => {
    navigate("/");
  };

  return (
    <div className={styles.mainContainer}>
      <div className={styles.title} onClick={navigateMain}>
        <img src={titleIcon} className={styles.titleIcon} alt="titleImg" />
        <div className={`p-15 ${styles.titleImg}`}>
          <img src={title} alt="title" />
        </div>
      </div>
      <div className={styles.sidebarLinksSection}>
        <label htmlFor="sidebarLinks">Menu</label>
        <NavLink
          to="/trend"
          className={({ isActive }) => (isActive ? styles.active : undefined)}
        >
          <FaChartLine size="20px" /> <span>트렌드 확인</span>
        </NavLink>
        <NavLink
          to="/upload"
          className={({ isActive }) => (isActive ? styles.active : undefined)}
        >
          <TbReportAnalytics size="20px" /> <span>분석하기</span>
        </NavLink>
        <NavLink
          to="/discover"
          className={({ isActive }) => (isActive ? styles.active : undefined)}
        >
          <FaMusic size="20px" /> <span>둘러보기</span>
        </NavLink>
      </div>
      <div className={styles.accountSection}>
        <label htmlFor="user">Account</label>
        {isLogin && (
          <>
            <NavLink
              to={`/artist/${userSlice.userId}`}
              className={({ isActive }) =>
                isActive ? styles.active : undefined
              }
            >
              <RxPerson size="20px" /> <span>Profile</span>
            </NavLink>
            <NavLink
              onClick={() => {
                handleLogout();
              }}
              to="/login"
            >
              <MdOutlineLogout size="20px" /> <span>Logout</span>
            </NavLink>
          </>
        )}
        {!isLogin && (
          <>
            <NavLink
              to="/select-sign-up"
              className={({ isActive }) =>
                isActive ? styles.active : undefined
              }
            >
              <RxPerson size="20px" /> <span>Sign Up</span>
            </NavLink>
            <NavLink
              to="/login"
              className={({ isActive }) =>
                isActive ? styles.active : undefined
              }
            >
              <MdOutlineLogin size="20px" /> <span>Login</span>
            </NavLink>
          </>
        )}
      </div>
      <div className={styles.aboutSection}>
        <label htmlFor="aboutSection">About</label>
        <NavLink to="/about">
          <FaRegQuestionCircle size="20px" /> <span>about</span>
        </NavLink>
      </div>
    </div>
  );
};

export default Sidebar;
