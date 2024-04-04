import React from "react";
import styles from "./ModalTag.module.css";

type PropsType = {
  mood: any;
};

const MoodTag = ({ mood }: PropsType) => {
  return (
    <div className={styles.container}>
      <p>{mood}</p>
    </div>
  );
};

export default MoodTag;
