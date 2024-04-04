import React, { useEffect, useState } from "react";
import { FaQuestionCircle } from "react-icons/fa";

import styles from "./AnalysisPage.module.css";
import testimg from "./../../sources/testimg/image 42.png";
import MusicSectionIndicator from "../../components/public/analysis/MusicSectionIndicator";
import MoodAndGenre from "../../components/public/analysis/MoodAndGenre";
import SimilarSong from "../../components/public/analysis/SimilarSong";
import Button from "../../components/buttons/Button";
import { useAxios } from "../../hooks/api/useAxios";
import axios from "axios";

import { AnalyzedResultType } from "../../utils/types";
import { useNavigate, useParams } from "react-router";
import { axiosInstance } from "../../hooks/api";

const AnalysisPage = () => {
  const navigate = useNavigate();
  const { songId } = useParams();
  const [mfccImg, setMfccImg] = useState<any>();
  const [resultResponse, setResultResponse] = useState<any>();
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const request = async () => {
      setIsLoading(true);
      try {
        const songDetail = await axiosInstance.request({
          method: "GET",
          url: `/song/detail/${songId}`,
        });
        console.log(songDetail.data.data);
        setResultResponse(songDetail.data.data);
        if (songDetail) {
          try {
            const mfccImage = await axiosInstance.request({
              method: "GET",
              url: `/download/mfcc/${songDetail.data.data.mfccImageId}`,
              responseType: "blob",
            });
            const imgURL = URL.createObjectURL(mfccImage.data);
            setMfccImg(imgURL);
          } catch (error) {
            console.log(error);
          }
        }
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };
    request();
  }, []);

  const navigatePostPage = () => {
    navigate("post");
  };

  if (isLoading) {
    return <p>결과 화면을 로딩중입니다</p>;
  }
  return (
    <div className={`w-100 px-main ${styles.container}`}>
      <div className={`${styles.header}`}>
        <h1>분석 결과</h1>
      </div>
      <div className={`flex-row-center border-box py-15`}>
        <h2>{resultResponse.songTitle}</h2>
      </div>
      <div className={`flex-col gap-15}`}>
        <h2 className={`flex-row gap-15`}>
          MFCC 결과 <FaQuestionCircle />
        </h2>
        {/* songMfcc */}
        <div className={`border-box bg-box flex-col-center ${styles.imgBox}`}>
          <img src={mfccImg} alt="mfccimg" />
        </div>
      </div>
      <div className={`flex-col gap-15`}>
        <h2>구간 분석 결과</h2>
        <div className={`flex-row gap-15`}>
          <div
            className={`border-box bg-box flex-col-center gap-15 ${styles.imgBox}`}
          >
            <MusicSectionIndicator />
          </div>
        </div>
      </div>
      {/* <MoodAndGenre /> */}
      <div className={"border-box bg-box py-15"}>
        <SimilarSong />
      </div>
      <div className={"border-box bg-box py-15 gap-15 flex-col-center"}>
        <h2>곡을 게시하고, 트랜드에 참여하세요!</h2>
        <Button onClick={navigatePostPage}>게시하기</Button>
      </div>
    </div>
  );
};

export default AnalysisPage;
