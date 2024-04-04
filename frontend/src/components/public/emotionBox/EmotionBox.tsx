import React from "react";
import styles from "./EmotionBox.module.css";

import emotion0 from "./../../../sources/imgs/emotions/emotion1.gif";
import emotion1 from "./../../../sources/imgs/emotions/emotion2.gif";
import emotion2 from "./../../../sources/imgs/emotions/emotion3.gif";
import emotion3 from "./../../../sources/imgs/emotions/emotion4.gif";
import emotion4 from "./../../../sources/imgs/emotions/emotion5.gif";
import emotion5 from "./../../../sources/imgs/emotions/emotion6.gif";

const EMOTIONS = [
  {
    name: "Impressive",
    title: "감동적이에요",
    image: emotion0,
  },
  {
    name: "Like",
    title: "좋아요",
    image: emotion1,
  },
  {
    name: "Unique",
    title: "특이해요",
    image: emotion2,
  },
  {
    name: "Empowering",
    title: "힘이나요",
    image: emotion3,
  },
  {
    name: "Fun",
    title: "재밌어요",
    image: emotion4,
  },
  {
    name: "Sad",
    title: "슬퍼요",
    image: emotion5,
  },
];

/**
 * @todo onClick함수 작성해서 숫자 올리는 로직 작성 필요
 * @todo 해당 곡에 대한 정보를 받아오며 각 emotion별로 숫자 입력 받아와야함
 */
const EmotionBox = () => {
  // 해당 음악의 id
  return (
    <div className={`flex-col-center ${styles.container}`}>
      {EMOTIONS.map((element, index) => (
        <div key={element.name} className={`flex-col-center ${styles.emotion}`}>
          <button
            title={element.title}
            name={element.title}
            className={styles.emotionButton}
          >
            <img src={element.image} alt={element.name} />
          </button>
          <p>134</p>
        </div>
      ))}
    </div>
  );
};

export default EmotionBox;
