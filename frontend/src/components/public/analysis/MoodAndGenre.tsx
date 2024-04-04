import React from "react";
import styles from "./MoodAndGenre.module.css";
import RaderChart from "../chart/raderChart/RaderChart";

const MoodAndGenre = () => {
  return (
    <div className={`w-100 ${styles.container}`}>
      <h2>분위기 / 장르 분석 결과</h2>
      <div className={`w-100 flex-row-center ${styles.radderBox}`}>
        <div className={`w-50 border-box ${styles.moodBox}`}>
          <h2>분위기 분석</h2>
          <div className={`${styles.chartBox}`}>
            <RaderChart />
          </div>
          <div className={`flex-row-center ${styles.topFiveResult}`}>
            <div className={`flex-col-center ${styles.result}`}>
              <p>기쁨</p>
              <h3>78%</h3>
            </div>
          </div>
          <div className={`flex-col-center ${styles.summary}`}>와결과다</div>
        </div>
        <div className={`w-50 border-box ${styles.genreBox}`}>
          <h2>장르 분석</h2>
          <div className={styles.chartBox}>
            <RaderChart />
          </div>
          <div className={`flex-row-center ${styles.topFiveResult}`}>
            <div className={`flex-col-center ${styles.result}`}>
              <p>발라드</p>
              <h3>78%</h3>
            </div>
          </div>
          <div className={`flex-col-center ${styles.summary}`}>와결과다</div>
        </div>
      </div>
    </div>
  );
};

export default MoodAndGenre;
