import React from "react";
import styles from "./SongWithEmotion.module.css";
import emotion1 from "./../../../sources/imgs/emotions/emotion1.gif";
import emotion2 from "./../../../sources/imgs/emotions/emotion2.gif";
import emotion3 from "./../../../sources/imgs/emotions/emotion3.gif";
import emotion4 from "./../../../sources/imgs/emotions/emotion4.gif";
import emotion5 from "./../../../sources/imgs/emotions/emotion5.gif";
import emotion6 from "./../../../sources/imgs/emotions/emotion6.gif";
import Album from "../../public/Album";
import { useNavigate } from "react-router-dom";

type Song = {
  songId: number;
  title: string;
  artistId: number;
  artistName: string;
  lyrics: string;
  play: number;
  download: number;
  like: number;
  emotions: any;
  atmospheres: any;
};

type EmotionMap = Record<string, Song>;

const SongWithEmotion = ({ emotions }: EmotionMap) => {
  const navigate = useNavigate();
  // console.log(emotions);
  return (
    <div className={`flex-col-center ${styles.container}`}>
      <h1>감정 극대화 곡</h1>
      <div
        className={`flex-row-center p-15 w-100 bg-box b-15 shadow-box ${styles.content}`}
      >
        {Object.entries(emotions).map((element: any) => {
          // console.log(element);
          return (
            <div
              key={element[0]}
              className={`flex-col-center ${styles.songBox}`}
            >
              <div style={{ width: "100px", height: "100px" }}>
                <Album songId={element[1].songId} />
              </div>
              <div className={`flex-col-center ${styles.info}`}>
                <h2
                  style={{ cursor: "pointer" }}
                  onClick={() => navigate(`/song/${element[1].songId}`)}
                >
                  {element[1].songTitle}
                </h2>
                <h3
                  style={{ cursor: "pointer" }}
                  onClick={() => navigate(`/song/${element[1].artistId}`)}
                >
                  {element[1].artistName}
                </h3>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default SongWithEmotion;
