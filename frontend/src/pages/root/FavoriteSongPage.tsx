import React, { useEffect } from "react";
import styles from "./FavoriteSongPage.module.css";
import { useAxios } from "../../hooks/api/useAxios";
import MusicTable from "../../components/public/music/MusicTable";

const FavoriteSongPage = () => {
  const header = require(`./../../sources/imgs/header/headerimg1.jpg`);

  const { response, isLoading } = useAxios({
    method: "GET",
    url: `/music-playlist/liked-song/1`,
  });

  console.log(response);

  return (
    <div className={`w-100`}>
      <div className={`w-100 flex-row-center ${styles.header}`}>
        <div className={`w-100 ${styles.headerBackground}`}>
          <img src={header} alt="headerImg" />
        </div>
        <div className={`mt-auto mr-auto flex-col gap-15 ${styles.comment}`}>
          <h1>나의 뮤직</h1>
          <h1>스테이션</h1>
        </div>
      </div>
      <div className={`flex-col p-15 w-100`}>
      </div>
    </div>
  );
};

export default FavoriteSongPage;
