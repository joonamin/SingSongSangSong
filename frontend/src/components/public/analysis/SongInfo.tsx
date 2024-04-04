import React from "react";
import { PiVinylRecord } from "react-icons/pi";
import styles from "./SongInfo.module.css";
import MoodTag from "../../moodTag/MoodTag";

// songDescription={DUMMY_DATA.songDescription}
// bpm={DUMMY_DATA.bpm}
// chord={DUMMY_DATA.chord}

/**
 * @todo 곡설명에 대한 정보와 tag에 관련된 props를 받아야함
 * @returns
 */

type PropsType = {
  songDescription: string;
  bpm: string | number;
  chord: string;
};
const SongInfo = ({ songDescription, bpm, chord }: PropsType) => {
  return (
    <div className={`flex-col-center ${styles.container}`}>
      <header>
        <h2>
          곡설명
          <PiVinylRecord color="#0085E5" size={"42"} />
        </h2>
      </header>
      <div className={`${styles.explanationBox}`}>
        {songDescription ? songDescription : <p>곡에 대한 설명이 없습니다.</p>}
      </div>
      <div className={`flex-row-center ${styles.tagBox}`}>
        <MoodTag mood={`${bpm}BPM`} />
        <MoodTag mood={chord} />
      </div>
    </div>
  );
};

export default SongInfo;
