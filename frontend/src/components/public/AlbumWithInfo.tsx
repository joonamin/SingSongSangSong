import React, { ComponentProps } from "react";
import styles from "./Album.module.css";
import Album from "./Album";

interface AlbumWithInfoProps {
  dir: string;
}

const AlbumWithInfo: React.FC<AlbumWithInfoProps> = ({ dir, ...props }) => {
  return (
    <>
      {dir === "row" && (
        <div {...props} className={`flex-row-center ${styles.container}`}>
          <div className={styles.album}>
            <Album />
          </div>
          <div className={styles.dirRow}>
            <h2>노래제목</h2>
            <h3>김작곡</h3>
            <p>태그들</p>
          </div>
        </div>
      )}
      {dir === "col" && (
        <div {...props} className={`flex-col-center ${styles.contienr}`}>
          <div className={styles.album}>
            <Album />
          </div>
          <div className={styles.dirCol}>
            <h2>노래제목</h2>
            <h3>김작곡</h3>
            <p>태그들</p>
          </div>
        </div>
      )}
    </>
  );
};

export default AlbumWithInfo;
