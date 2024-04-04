import React from "react";
import { FaHeart } from "react-icons/fa";
import { LiaHeadsetSolid } from "react-icons/lia";
import { MdOutlineFileDownload } from "react-icons/md";

import { EmotionType } from "../../../../utils/types";
import styles from "./TestWeeklySingsongChart.module.css";
import TrendSlider from "./TrendSlider";
import Album from "../../../public/Album";
import MoodTag from "../../../moodTag/MoodTag";
import EmotionBox from "../../../public/emotionBox/EmotionBox";
import RaderChart from "../../../public/chart/raderChart/RaderChart";
import MusicEmotionBox from "../../../public/emotionBox/MusicEmotionBox";
import { useAxios } from "../../../../hooks/api/useAxios";

type WeeklyData = {
  artistId: number;
  artistName: string;
  download: number;
  emtions: EmotionType[];
  like: number;
  lyrics: string;
  play: number;
  songId: number;
  title: string;
};

const TestWeeklySingsongChart = ({ weekly }: any) => {
  // console.log("Weekdata : ", weekly);

  return (
    <div className={`w-100 gap-15 flex-col-center ${styles.container}`}>
      <h1>금주의 싱송차트</h1>
      <div className={`w-100 p-15 b-15 shadow-box`}>
        <TrendSlider>
          {weekly.map((element: any) => {
            return (
              <div className={`${styles.content}`}>
                <div className={`flex-col gap-15 ${styles.leftBox}`}>
                  <div className={`w-100 flex-row gap-30 space-between`}>
                    <div style={{ width: "100px", height: "100px" }}>
                      <Album songId={element.songId} />
                    </div>
                    <div className={`flex-col`}>
                      <h1>{element.songTitle}</h1>
                      <h3>{element.artist.nickname}</h3>
                    </div>
                    <div
                      className={`flex-col border-box gap-15 p-15 ${styles.indicator}`}
                    >
                      <p className={`flex-row-center gap-15`}>
                        <FaHeart size={24} />
                        Favorites
                        <span>{element.likeCount}</span>
                      </p>
                      <p className={`flex-row-center gap-15`}>
                        <MdOutlineFileDownload size={24} />
                        Downloads
                        <span>{element.downloadCount}</span>
                      </p>
                      <p className={`flex-row-center gap-15`}>
                        <LiaHeadsetSolid size={24} />
                        Plays
                        <span>{element.playCount}</span>
                      </p>
                    </div>
                  </div>
                  <div className={`w-100 p-15 border-box`}>
                    <p>
                      {element.songDescription
                        ? element.songDescription
                        : "곡 소개가 없습니다."}
                    </p>
                  </div>
                  <div className={`w-100 flex-row space-between`}>
                    {element.analysis.atmospheres.map((ele: any) => {
                      return <MoodTag mood={ele.atmosphere} />;
                    })}
                  </div>
                  <div>
                    <MusicEmotionBox
                      songId={element.songId}
                      movedEmotionCount={element.movedEmotionCount}
                      likeEmotionCount={element.likeEmotionCount}
                      excitedEmotionCount={element.excitedEmotionCount}
                      funnyEmotionCount={element.funnyEmotionCount}
                      sadEmotionCount={element.sadEmotionCount}
                      energizedEmotionCount={element.energizedEmotionCount}
                      refetch={() => console.log("refetch")}
                    />
                  </div>
                </div>
                <div className={`${styles.rightBox}`}>
                  <div style={{ height: "400px" }} className={`w-100`}>
                    <RaderChart type="genre" data={element.analysis.genres} />
                  </div>
                </div>
              </div>
            );
          })}
        </TrendSlider>
      </div>
    </div>
  );
};

export default TestWeeklySingsongChart;
