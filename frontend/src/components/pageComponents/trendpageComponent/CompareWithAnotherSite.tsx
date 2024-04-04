import React from "react";

import styles from "./CompareWithAnotherSite.module.css";
import ChartWithMelonBillboard from "./ChartWithMelonBillboard";

type tempType = {
  korean: any;
  world: any;
};

/**
 * 멜론차트와 빌보드차트를 보여줄 컴포넌트입니다
 */
const CompareWithAnotherSite = ({ korean, world }: tempType) => {
  return (
    <div className={`w-100 ${styles.container}`}>
      <div
        className={`flex-row-center w-100 bg-box b-15 shadow-box ${styles.content}`}
      >
        <div className={`flex-row-center ${styles.billboard}`}>
          <ChartWithMelonBillboard data={world} type="billboard" />
        </div>
        <div className={`flex-row-center ${styles.melon}`}>
          <ChartWithMelonBillboard data={korean} type="melon" />
        </div>
      </div>
    </div>
  );
};

export default CompareWithAnotherSite;
