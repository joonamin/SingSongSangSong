import axios from "axios";
import { useDispatch } from "react-redux";
import React, {
  useState,
  ChangeEvent,
  useRef,
  FormEvent,
  useEffect,
} from "react";
import { Link, redirect, useLocation, useNavigate } from "react-router-dom";
import { GrPowerReset } from "react-icons/gr";

import styles from "./RegisterPage.module.css";
import AuthInput from "../../components/auth/AuthInput";
import Button from "../../components/buttons/Button";

import { useInput } from "../../hooks/useInput";
import { descValidator, nicknameValidator } from "../../utils/validator";
import { axiosInstance } from "../../hooks/api";
import { setCookie } from "../../utils/cookie";
import { userAction } from "../../store/userSlice";

const AGES = [
  { data: 10, text: "10대" },
  { data: 20, text: "20대" },
  { data: 30, text: "30대" },
  { data: 40, text: "40대" },
  { data: 50, text: "50대" },
  { data: 6, text: "50대 이상" },
];

const RegisterPage = () => {
  const location = useLocation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const searchParams = new URLSearchParams(location.search);
  const accessToken = searchParams.get("accessToken");

  const {
    value: nicknameValue,
    handleInputChange: handleNicknameChange,
    valueIsValid: nicknameIsValid,
  } = useInput("", nicknameValidator);

  const {
    value: descValue,
    handleInputChange: handleDescChange,
    valueIsValid: descIsValid,
  } = useInput("", descValidator);

  // 회원가입 관련 state들
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [profileImage, setProfileImage] = useState<File | null>(null);
  const [profileUrl, setProfileUrl] = useState<string>("");
  const [gender, setGender] = useState<undefined | string>(undefined);
  const [age, setAge] = useState<undefined | number>(undefined);
  // 프로필 사진 설정 시 호버했을 때 리셋 버튼을 보여줄 boolean값
  const [isHover, setIsHover] = useState<boolean>(false);

  const handleGenderChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    setGender(event.target.value);
  };

  const handleAgeChange = (event: ChangeEvent<HTMLSelectElement>): void => {
    setAge(+event.target.value);
  };

  // 프로필 이미지를 등록
  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    // console.log(files);
    // console.log(new Date());
    if (files && files[0] && files[0].type.startsWith("image/")) {
      setProfileUrl(URL.createObjectURL(files[0]));
      setProfileImage(files[0]);
    }
  };

  // 프로필 이미지를 리셋
  const handleImageReset = () => {
    setProfileImage(null);
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // console.log("제출 시 토큰값 : ", accessToken);
    try {
      const response = await axios({
        method: "POST",
        url: `${process.env.REACT_APP_API_URL}artist/join`,
        data: {
          nickname: nicknameValue,
          profileImage: profileImage,
          age,
          sex: gender,
          introduction: descValue,
        },
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: false,
      });
      // console.log(response);
      if (response.status === 200) {
        setCookie("accessToken", `${accessToken}`, {
          path: "/",
          secure: true,
          sameSite: "none",
        });
        const userInfo = await axiosInstance.request({
          method: "GET",
          url: "/artist/profile/me",
        });
        if (userInfo?.data?.data) {
          const userSliceData = userInfo.data.data;
          // console.log("들어오는 유저 슬라이스값", userSliceData);
          dispatch(userAction.setLogin(userSliceData));
        }
        navigate("/trend");
      }
    } catch (error) {
      console.log(error);
    }
  };

  // useEffect(() => {
  //   console.log(nicknameValue);
  //   console.log(descValue);
  // }, [descValue, nicknameValue]);

  return (
    <div className={styles.container}>
      <form onSubmit={handleSubmit}>
        <div className={`w-100 p-15 flex-col-center`}>
          <>
            {profileImage && (
              <div
                onMouseOver={() => setIsHover(true)}
                onMouseLeave={() => setIsHover(false)}
                className={`${styles.imageContainer}`}
              >
                <img
                  src={profileUrl}
                  className={`${styles.profileImage}`}
                  alt="profileImg"
                />
                <div
                  className={` flex-col-center ${styles.resetBtn} ${
                    isHover ? styles.active : ""
                  }`}
                >
                  <GrPowerReset
                    onClick={handleImageReset}
                    style={{ cursor: "pointer" }}
                    size={42}
                  />
                </div>
              </div>
            )}
            {!profileImage && (
              <label className={`flex-col-center p-15 ${styles.inputForm}`}>
                <input
                  type="file"
                  id="albumImg"
                  className={`${styles.imgInput}`}
                  accept="image/jpeg, image/png, image/jpg"
                  ref={fileInputRef}
                  onChange={handleFileUpload}
                  required
                />
                <h2>프로필 사진을 업로드 해주세요</h2>
              </label>
            )}
          </>
        </div>
        <AuthInput
          id="nickname"
          label="닉네임"
          name="nickname"
          type="text"
          placeholder="닉네임을 입력해주세요"
          value={nicknameValue}
          onChange={handleNicknameChange}
          required
        />
        <AuthInput
          id="intro"
          label="소개"
          name="intro"
          type="textarea"
          placeholder="자기소개를 입력해주세요"
          value={descValue}
          onChange={handleDescChange}
          required
        />

        <div className={styles.selectBox}>
          <label>성별</label>
          <select
            name="gender"
            value={gender}
            onChange={handleGenderChange}
            required
          >
            <option key="none" value={""}>
              성별을 선택해주세요
            </option>
            <option key="male" value="M">
              남성
            </option>
            <option key="female" value="F">
              여성
            </option>
          </select>

          <label>연령</label>
          <select name="age" value={age} onChange={handleAgeChange} required>
            <option value={""}>연령대를 설정해주세요</option>
            {AGES.map((element) => {
              return (
                <option key={element.data} value={element.data}>
                  {element.text}
                </option>
              );
            })}
          </select>
        </div>
        <div className={styles.bottomBox}>
          <Button>Register</Button>
          <p>
            이미 계정을 보유 중이신가요?
            <Link style={{ borderBottom: "1px solid black" }} to="/login">
              로그인
            </Link>{" "}
          </p>
        </div>
      </form>
    </div>
  );
};

export default RegisterPage;
