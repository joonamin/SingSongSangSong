import React, { Fragment, useState } from "react";
import { FaArrowLeft, FaArrowRight, FaHeart } from "react-icons/fa";
import { LiaHeadsetSolid } from "react-icons/lia";
import { MdOutlineFileDownload } from "react-icons/md";

import img from "./../../../sources/testimg/cover.png";
import styles from "./WeeklySingsongChart.module.css";
import MoodTag from "../../moodTag/MoodTag";
import EmotionBox from "../../public/emotionBox/EmotionBox";
import RaderChart from "../../public/chart/raderChart/RaderChart";

const DUMMY_DATA = [
  {
    title: "1",
    author: "heun",
    tag: [1, 2, 3, 4],
    emotion: [1, 2, 3, 4, 5],
    coverImg: img,
  },
  {
    title: "2",
    author: "ohasdassdddd",
    tag: [1, 2, 3, 4],
    emotion: [1, 2, 3, 4, 5],
  },
  {
    title: "3",
    author: "lee",
    tag: [1, 2, 3, 4],
    emotion: [1, 2, 3, 4, 5],
  },
];
/** 금주의 싱송차트 주간 데이터 받아와서 여기서 조회해야하나?
 * @todo 3개의 곡데이터 받아와서 처리하고 변수에 넣어줘야함(Raderchart에)
 * @todo element.~~~ 으로 되어있는 부분이 전부 처리해야 할 부분
 */
const WeeklySingsongChart = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const handleNextIndex = () => {
    setCurrentIndex((prev) => (prev + 1) % 3);
  };

  const handlePrevIndex = () => {
    setCurrentIndex((prev) => (prev + 2) % 3);
  };

  return (
    <div className={`flex-col-center w-100 gap-15 ${styles.container}`}>
      <div className={`flex-row-center ${styles.headerContainer}`}>
        <h2>금주의 싱송차트</h2>
      </div>
      <div className={styles.chartContainer}>
        {DUMMY_DATA.map((element, index) => {
          return (
            index === currentIndex && (
              <Fragment key={element.title}>
                <div className={styles.leftArrow}>
                  <span onClick={handlePrevIndex}>
                    <FaArrowLeft />
                  </span>
                </div>
                <div className={styles.wrapper}>
                  <div className={styles.leftBox}>
                    <div className={styles.leftTopBox}>
                      <div className={styles.albumCover}>
                        <img src={img} alt="" />
                      </div>
                      <div className={`flex-col-center ${styles.musicBox}`}>
                        <h2>{element.title}</h2>
                        <p>{element.author}</p>
                      </div>
                      <div className={styles.topIndicator}>
                        <span>
                          <LiaHeadsetSolid size={24} />
                          <p style={{ display: "inline" }}>24</p>
                        </span>
                        <span>
                          <MdOutlineFileDownload size={24} />
                          <p style={{ display: "inline" }}>24</p>
                        </span>
                        <span>
                          <FaHeart size={20} />
                          <p style={{ display: "inline" }}>24</p>
                        </span>
                      </div>
                    </div>
                    <div className={`flex-col-center ${styles.songInfoBox}`}>
                      <p>
                        Lorem ipsum dolor sit amet consectetur adipisicing elit.
                        Beatae in cum nesciunt assumenda tempora eligendi fuga
                        iste aut voluptates optio, accusamus delectus labore
                        quae soluta velit harum, illo at molestias?
                      </p>
                    </div>
                    <div className={styles.tagBox}>
                      <MoodTag mood={"활기찬"} />
                      <MoodTag mood={"활기찬"} />
                      <MoodTag mood={"활기찬"} />
                      <MoodTag mood={"활기찬"} />
                    </div>
                    <div className={styles.emotionBox}>
                      <EmotionBox />
                    </div>
                  </div>
                  <div className={styles.rightBox}>
                    <RaderChart />
                  </div>
                </div>
                <div className={styles.rightArrow}>
                  <span onClick={handleNextIndex}>
                    <FaArrowRight />
                  </span>
                </div>
              </Fragment>
            )
          );
        })}
      </div>
    </div>
  );
};

export default WeeklySingsongChart;
