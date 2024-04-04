import React, { useEffect, useState } from "react";
import styles from "./SimilarSong.module.css";
import RankedSongAndArtist from "../../pageComponents/trendpageComponent/RankedSongAndArtist";
import Album from "../Album";
import { getSongSimilarity } from "../../../utils/api/songDetailApi";
import { useParams } from "react-router-dom";
import { axiosInstance } from "../../../hooks/api";

const DUMMY = [
  { artist: "test1", song: "testmusic1" },
  { artist: "test2", song: "testmusic2" },
  { artist: "test3", song: "testmusic3" },
  { artist: "test4", song: "testmusic4" },
];

const SimilarSong = () => {
  const { songId } = useParams();
  const [selectIndex, setSelectIndex] = useState<number>(0);
  const [responseData, setResponseData] = useState<any>();
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const changeIndex = (index: number) => {
    setSelectIndex(index);
  };

  useEffect(() => {
    const request = async () => {
      setIsLoading(true);
      try {
        const response = await axiosInstance.request({
          method: "GET",
          url: `/song/similarity/${songId}`,
        });
        // console.log(response.data.data);
        setResponseData(response.data.data);
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };
    request();
  }, []);

  if (isLoading) {
    return <p>데이터를 로딩중입니다</p>;
  }
  if (!responseData) {
    return <p>데이터가 존재하지 않습니다</p>;
  }
  return (
    <div className={`flex-col-center ${styles.container}`}>
      <h3>비슷한 노래에는 이런 노래가 있어요</h3>
      <div className={`flex-row-center ${styles.content}`}>
        <div className={`flex-row-center ${styles.similarSong}`}>
          {responseData.comparison.map((element: any, index: any) => {
            return (
              <div
                key={index}
                onClick={() => changeIndex(index)}
                className={`flex-row-center ${styles.box} ${
                  selectIndex === index ? styles.selceted : ""
                }`}
              >
                <div style={{ width: "150px", height: "150px" }}>
                  <Album songId={element.target.songId} />
                </div>
                {/* <RankedSongAndArtist type={"song"} showIndicator={false} /> */}
              </div>
            );
          })}
        </div>
        <div className={`flex-col-center ${styles.compareSongSection}`}>
          <p>비교할 곡과의 </p>
          <h2 style={{ paddingBottom: "2px", borderBottom: "3px solid black" }}>
            유사도는{" "}
            {`${Math.floor(
              responseData.comparison[selectIndex].correlation * 100
            )}%`}{" "}
            입니다
          </h2>
          <div className={`flex-row-center ${styles.compareSong}`}>
            <div className={`flex-col-center gap-15 ${styles.mySong}`}>
              <div
                style={{ width: "100px", height: "100px",  }}
              >
                <Album songId={songId} />
              </div>
              <h3>
                {responseData.title ? responseData.title : "제목이 없습니다"}
              </h3>
              <p>나의 노래</p>
            </div>
            <div className={`flex-col-center gap-15 ${styles.selectedSong}`}>
              <div style={{ width: "100px", height: "100px" }}>
                <Album
                  songId={responseData.comparison[selectIndex].target.songId}
                />
              </div>
              <h3>{responseData.comparison[selectIndex].target.title}</h3>
              <p>비교 노래</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SimilarSong;
