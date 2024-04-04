import React, { useState } from "react";

// 이미지
import trophy from "./../../../sources/imgs/trophy.png";
import medal0 from "./../../../sources/imgs/medal0.png";
import medal1 from "./../../../sources/imgs/medal1.png";
import medal2 from "./../../../sources/imgs/medal2.png";
// import받아온 컴포넌트
import RaderChart from "../../public/chart/raderChart/RaderChart";
import styles from "./ChartWithMelonBillboard.module.css";
import Album from "../../public/Album";
import MoodTag from "../../moodTag/MoodTag";
import { useNavigate } from "react-router-dom";

const DUMMY_DATA = [
  {
    songTitle: "무슨노래123",
    songAuthor: "김싸피",
    mood: ["따뜻한", "발라드", "150BPM", "C#"],
  },
  {
    songTitle: "데이터없어서",
    songAuthor: "김싸피",
    mood: ["차가운", "발라드", "120BPM", "D#"],
  },
  {
    songTitle: "이렇게넣었어요",
    songAuthor: "home",
    mood: ["신나는", "힙합", "150BPM", "F#"],
  },
];

type dataType = {
  artistId: number;
  songId: number;
  artistName: string;
  atomospheres: any;
  download: number;
  emotions: any;
  like: number;
  lyrics: string;
  play: number;
  title: string;
};

type PropsType = {
  type: string;
  data: any[];
};
/**
 * melon / billboard 두개의 상태에 따라서 보여줄 곡들을 다르게할 로직 추가 필요
 * @returns
 */
const ChartWithMelonBillboard = ({ type, data }: PropsType) => {
  const navigate = useNavigate();
  const [songIndex, setSongIndex] = useState<number>(0);
  const handleSongIndex = (index: number) => {
    setSongIndex(index);
  };
  // console.log("ckadsignweqaognbvoweiugnboewrngbwoenbgvwenv8ew9nv", data);
  return (
    <div className={`${styles.container}`}>
      <div className={`flex-row-center ${styles.header}`}>
        {type === "melon" && (
          <>
            <img src={trophy} alt="trophy" />
            <h2 style={{ margin: "0px 7px" }}>한국인이 선택한 노래</h2>
            <img src={trophy} alt="trophy" />
          </>
        )}
        {type === "billboard" && (
          <>
            <img src={trophy} alt="trophy" />
            <h2 style={{ margin: "0px 7px" }}>세계가 선택한 노래</h2>
            <img src={trophy} alt="trophy" />
          </>
        )}
      </div>
      <div className={`${styles.content}`}>
        <div className={`flex-col-center ${styles.songs}`}>
          {/* 아래쪽 코드는 반복문으로 받아온 데이터 출력해야함 */}
          {/* 또한 데이터받아오고 그거맞춰서 입력 해줘야함 */}
          {data.map((element, index) => {
            const medalImage =
              index === 0 ? medal0 : index === 1 ? medal1 : medal2;
            return (
              <div
                onClick={() => handleSongIndex(index)}
                key={element.title}
                className={`flex-row-center gap-15 p-15 b-15 ${styles.song} ${
                  songIndex === index ? styles.selected : ""
                }`}
              >
                <div
                  className={`flex-col-center ${styles.album}`}
                  style={{ width: "100px", height: "100px" }}
                >
                  <Album songId={element.songId} />
                </div>
                <div className={styles.info}>
                  <img src={medalImage} alt="" />
                  <p onClick={() => navigate(`/artist/${element.artistId}`)}>
                    {element.artist.nickname}
                  </p>
                  <h2 onClick={() => navigate(`/song/${element.songId}`)}>
                    {element.songTitle}
                  </h2>
                </div>
              </div>
            );
          })}
        </div>
        {/* <div className={`flex-row-center ${styles.tagBox}`}>
          해당 태그들의 mood값을 받아온값으로 변경하는 (selected index로 엮어서) 로직 필요
          {DUMMY_DATA[songIndex].mood.map((element) => {
            return <MoodTag key={element} mood={element} />;
          })}
        </div> */}
        <div className={`flex-row-center ${styles.chartBox}`}>
          <div className={styles.leftChart}>
            <RaderChart type="genre" data={data[songIndex].analysis.genres} />
          </div>
          <div className={styles.rightChart}>
            <RaderChart
              type="atmo"
              data={data[songIndex].analysis.atmospheres}
            />
          </div>
        </div>
        {/* <div className={styles.flowImg}>
          추후 추가필요
          </div> */}
      </div>
    </div>
  );
};

export default ChartWithMelonBillboard;
