import React, { useEffect, useState } from "react";
import styles from "./TopRankSongPage.module.css";
import MusicTable from "../../components/public/music/MusicTable";
import { useParams } from "react-router";
import { useDispatch } from "react-redux";
import { musicAction } from "../../store/musicSlice";
import { useAxios } from "../../hooks/api/useAxios";
import { title } from "process";
import { getAtmosphereList, getGenreList } from "../../utils/api/discoverApi";
import TestMusicTable from "../../components/public/music/TestMusicTable";

const GENRE_COMMENT = [
  {
    keyword: "electronic",
    top: "열정의",
    middle: "일렉트로닉",
  },
  {
    keyword: "Rock",
    top: "단단한",
    middle: "바위",
  },
  {
    keyword: "hiphop",
    top: "시그니컬",
    middle: "근본 힙합",
  },
  {
    keyword: "pop",
    top: "톡톡튀는",
    middle: "인기 팝",
  },
  {
    keyword: "jazz",
    top: "리드미컬",
    middle: "재즈",
  },
  {
    keyword: "classic",
    top: "안정을 주는",
    middle: "클래식",
  },
  {
    keyword: "ballade",
    top: "서정적인",
    middle: "발라드",
  },
];

const MOOD_COMENT = [
  {
    keyword: "happy",
    top: "행복한",
    middle: "오늘하루",
  },
  {
    keyword: "sad",
    top: "울적해진",
    middle: "나에게",
  },
  {
    keyword: "calm",
    top: "휴식이",
    middle: "필요할 때",
  },
  {
    keyword: "exciting",
    top: "열정이",
    middle: "솟아오르는",
  },
  {
    keyword: "love",
    top: "두근두근",
    middle: "떨리는",
  },
  {
    keyword: "inspiring",
    top: "맑고 깨끗한",
    middle: "마음가짐",
  },
  {
    keyword: "nostalgia",
    top: "감성적인",
    middle: "지난 기억들",
  },
];

/**
 * 분위기 / 장르별 상위 10개의 음악들을 보여줄 페이지.
 */
const TopRankSongPage = () => {
  const { type, keyword } = useParams();
  const dispatch = useDispatch();
  const [comment, setComment] = useState({
    top: "",
    middle: "",
  });
  const [musicData, setMusicData] = useState<any>();

  const randNum = Math.floor(Math.random() * 5) + 1;
  const header = require(`./../../sources/imgs/header/headerimg${randNum}.jpg`);

  useEffect(() => {
    if (type === "genre") {
      let temp = GENRE_COMMENT.find((element) => element.keyword === keyword);
      setComment((prev) => {
        return {
          top: temp?.top as string,
          middle: temp?.middle as string,
        };
      });
    } else if (type === "mood") {
      let temp = MOOD_COMENT.find((element) => element.keyword === keyword);
      setComment((prev) => {
        return {
          top: temp?.top as string,
          middle: temp?.middle as string,
        };
      });
    }
  }, []);

  useEffect(() => {
    const genre = async () => {
      const res = await getGenreList(keyword);
      // console.log("장르별 데이터", res);
      setMusicData(res);
    };
    const atom = async () => {
      const res = await getAtmosphereList(keyword);
      setMusicData(res);
    };
    if (type === "genre") {
      genre();
    } else if (type === "mood") {
      atom();
    }
  }, []);

  // console.log(musicData);

  return (
    <div className={`w-100 flex-col gap-30`}>
      <div className={`w-100 flex-row-center ${styles.header}`}>
        <div className={`w-100 ${styles.headerBackground}`}>
          <img src={header} alt="headerImg" />
        </div>
        <div className={`mt-auto mr-auto flex-col gap-15 ${styles.content}`}>
          <h1>{comment.top}</h1>
          <h1>{comment.middle}</h1>
          <h1>TOP 10</h1>
        </div>
      </div>
      <TestMusicTable musicData={musicData} />
    </div>
  );
};

export default TopRankSongPage;
