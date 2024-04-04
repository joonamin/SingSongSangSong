import React from "react";
import styles from "./SectionButton.module.css";

type PropsType = {
  section: string;
  startPoint: number;
  endPoint: number;
};

const SectionButton = ({ section, startPoint, endPoint }: PropsType) => {
  const sectionClassName = (() => {
    switch (section) {
      case "intro":
        return styles.intro;
      case "verse":
        return styles.verse;
      case "chorus":
        return styles.chorus;
      case "bridge":
        return styles.bridge;
      case "outro":
        return styles.verse;
      default:
        return "";
    }
  })();

  return (
    <div className={`flex-col-center ${styles.container} ${sectionClassName}`}>
      <h3>{section}</h3>
      <p>
        {Math.floor(startPoint / 60)}:{startPoint % 60}~
        {Math.floor(endPoint / 60)}:{endPoint % 60}
      </p>
    </div>
  );
};

export default SectionButton;
