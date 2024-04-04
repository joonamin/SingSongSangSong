import React, { useEffect, useState } from "react";
import styles from "./RankedSong.module.css";
import img from "./../../../sources/testimg/cover.png";
import crwon from "./../../../sources/imgs/crownicon.png";
import Album from "../../public/Album";

/** 성별과 연령으로 알아보는 트렌드에서 사용할 랭킹 컴포넌트
 * @todo 데이터 넘어오는거 확인하고 윗 태그 / 작곡가 / 제목 쪽 손봐야함
 */
type PropsType = {
  data: any;
  type: string;
};
const RankedSong = ({ data, type }: PropsType) => {
  const [header, setHeader] = useState();

  useEffect(() => {
    if (type === "genre") {
      setHeader(data.genre);
    } else if (type === "atom") {
      setHeader(data.atmosphere);
    } else if (type === "artist") {
      setHeader(data.artistInfo.nickname);
    } else if (type === "song") {
      setHeader(data.song.songTitle);
    }
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <span>
          <img src={crwon} alt="" style={{ width: "36px", height: "36px" }} />
        </span>
        <p>#{header}</p>
      </div>
      <div className={`flex-row-center ${styles.content}`}>
        <div className={`${styles.albumCover}`}>
          <Album songId={data.song.songId} />
        </div>
        <div className={`flex-col-center ${styles.songInfo}`}>
          <p style={{ fontSize: "24px", fontWeight: "700" }}>
            {data.song.songTitle}
          </p>
          <p>{data.song.artist.nickname}</p>
        </div>
        <div className={styles.playCount}>
          <p>
            <span style={{ fontSize: "36px", color: "#0085E5" }}>
              {data.playCount}
            </span>
            회 재생
          </p>
        </div>
      </div>
    </div>
  );
};

export default RankedSong;
