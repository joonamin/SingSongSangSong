import React, { useEffect, useState } from "react";
import ReactSlider from "react-slider";
import styles from "./SongWithBPM.module.css";
import AlbumWithInfo from "../../public/AlbumWithInfo";
import RankedSongAndArtist from "./RankedSongAndArtist";
import { axiosInstance } from "../../../hooks/api";

type PropsType = {
  selectedDate: any;
};

const SongWithBPM = ({ selectedDate }: PropsType) => {
  const [bpm, setBpm] = useState<number>(120);
  const [bpmData, setBpmData] = useState<any>();
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const handleBpmChange = (value: number) => {
    // console.log(bpm);
    setBpm(value);
  };

  // 최초 로드 시 bpm 120으로 로드 그게 bpm과 selectedDate에 따라 재렌더링
  useEffect(() => {
    const request = async () => {
      setIsLoading(true);
      try {
        const response = await axiosInstance.request({
          url: `/trend/bpm`,
          method: "GET",
          params: {
            date: `${selectedDate.year}-${selectedDate.month}-${selectedDate.day}`,
            bpm: bpm,
          },
        });
        setBpmData(response.data.data);
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };
    request();
  }, [selectedDate, bpm]);
  // console.log(bpmData);

  if (isLoading) {
    return <p>bpm데이터를 로딩중입니다</p>;
  }
  if (!bpmData) {
    return <p>해당 데이터가 존재하지 않습니다</p>;
  }

  return (
    <div className={`flex-col-center ${styles.container}`}>
      <div
        className={`flex-col-center w-100 bg-box shadow-box b-15  ${styles.content}`}
      >
        <ReactSlider
          className={`flex-row-center ${styles.slider}`}
          thumbClassName={styles.sliderHead}
          trackClassName={styles.track}
          marks={[120, 130, 140, 150, 160, 170, 180, 190, 200]}
          markClassName={styles.dots}
          onChange={(e) => handleBpmChange(e)}
          value={bpm}
          min={120}
          max={200}
          step={10}
          renderThumb={(props, state) => <div {...props}>{state.valueNow}</div>}
        />
        <div className={`flex-col-center ${styles.numberOfSong}`}>
          <p>
            이번주에는 <span style={{ fontSize: "34px" }}>{bpmData.count}</span>{" "}
            개의 노래가
          </p>
          <p>
            <span>{bpm}</span>
            <span>&nbsp;~&nbsp;</span>
            <span>{bpm + 10}</span>
            BPM으로 탄생했습니다!
          </p>
        </div>
        <div className={`flex-row-center ${styles.songs}`}>
          {bpmData.songs.map((element: any) => {
            return (
              <div className={`flex-col-center gap-15`}>
                <h2>{element.bpm}</h2>
                <RankedSongAndArtist
                  type="song"
                  showIndicator={false}
                  songData={element}
                />
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default SongWithBPM;
