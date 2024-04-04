import React, { useEffect, useState } from "react";
import styles from "./MusicEmotionBox.module.css";

import emotion0 from "./../../../sources/imgs/emotions/emotion1.gif";
import emotion1 from "./../../../sources/imgs/emotions/emotion2.gif";
import emotion2 from "./../../../sources/imgs/emotions/emotion3.gif";
import emotion3 from "./../../../sources/imgs/emotions/emotion4.gif";
import emotion4 from "./../../../sources/imgs/emotions/emotion5.gif";
import emotion5 from "./../../../sources/imgs/emotions/emotion6.gif";
import { getEmotions } from "../../../utils/api/artistApi";
import { useParams } from "react-router-dom";
import { updateEmotion } from "../../../utils/api/songDetailApi";

type emotionType = {
  energizedEmotionCount: number;
  excitedEmotionCount: number;
  funnyEmotionCount: number;
  likeEmotionCount: number;
  movedEmotionCount: number;
  sadEmotionCount: number;
};

type PropsType = {
  songId: any;
  energizedEmotionCount: number;
  excitedEmotionCount: number;
  funnyEmotionCount: number;
  likeEmotionCount: number;
  movedEmotionCount: number;
  sadEmotionCount: number;
  refetch: () => void;
};

/**
 * @todo onClick함수 작성해서 숫자 올리는 로직 작성 필요
 * @todo 해당 곡에 대한 정보를 받아오며 각 emotion별로 숫자 입력 받아와야함
 */
const MusicEmotionBox = ({
  songId,
  energizedEmotionCount,
  excitedEmotionCount,
  funnyEmotionCount,
  likeEmotionCount,
  movedEmotionCount,
  sadEmotionCount,
  refetch,
}: PropsType) => {
  const [artistEmotion, setArtistEmotions] = useState<emotionType | any>({
    energizedEmotionCount: 0,
    excitedEmotionCount: 0,
    funnyEmotionCount: 0,
    likeEmotionCount: 0,
    movedEmotionCount: 0,
    sadEmotionCount: 0,
  });

  return (
    <div className={`flex-col-center ${styles.container}`}>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "moved");
            refetch();
          }}
          title="감동적이에요"
          name="감동적이에요"
          className={styles.emotionButton}
        >
          <img src={emotion0} alt="감동적이에요" />
        </button>
        <p>{movedEmotionCount}</p>
      </div>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "like");
            refetch();
          }}
          title="좋아요"
          name="좋아요"
          className={styles.emotionButton}
        >
          <img src={emotion1} alt="좋아요" />
        </button>
        <p>{likeEmotionCount}</p>
      </div>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "excited");
            refetch();
          }}
          title="특이해요"
          name="특이해요"
          className={styles.emotionButton}
        >
          <img src={emotion2} alt="특이해요" />
        </button>
        <p>{excitedEmotionCount}</p>
      </div>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "energized");
            refetch();
          }}
          title="힘이나요"
          name="힘이나요"
          className={styles.emotionButton}
        >
          <img src={emotion3} alt="힘이나요" />
        </button>
        <p>{energizedEmotionCount}</p>
      </div>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "funny");
            refetch();
          }}
          title="재밌어요"
          name="재밌어요"
          className={styles.emotionButton}
        >
          <img src={emotion4} alt="재밌어요" />
        </button>
        <p>{funnyEmotionCount}</p>
      </div>
      <div className={`flex-col-center ${styles.emotion}`}>
        <button
          onClick={async () => {
            await updateEmotion(songId, "sad");
            refetch();
          }}
          title="슬퍼요"
          name="슬퍼요"
          className={styles.emotionButton}
        >
          <img src={emotion5} alt="슬퍼요" />
        </button>
        <p>{sadEmotionCount}</p>
      </div>
    </div>
  );
};

export default MusicEmotionBox;
