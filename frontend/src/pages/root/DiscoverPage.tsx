import React, { ChangeEvent, useEffect, useState } from "react";

import styles from "./DiscoverPage.module.css";
import Button from "../../components/buttons/Button";
import { useNavigate } from "react-router-dom";
import StyledSlider from "../../components/public/StyledSlider";

import rockImg from "./../../sources/imgs/playList/락.png";
import balladeImg from "./../../sources/imgs/playList/발라드.png";
import electImg from "./../../sources/imgs/playList/일렉트로닉.png";
import jazzImg from "./../../sources/imgs/playList/재즈.png";
import classicImg from "./../../sources/imgs/playList/클래식.png";
import popImg from "./../../sources/imgs/playList/팝.png";
import hiphopImg from "./../../sources/imgs/playList/힙합.png";

import calmImg from "./../../sources/imgs/playList/calm.png";
import excitingImg from "./../../sources/imgs/playList/exciting.png";
import happyImg from "./../../sources/imgs/playList/happy.png";
import inspiringImg from "./../../sources/imgs/playList/inspiring.png";
import loveImg from "./../../sources/imgs/playList/love.png";
import nosstalgiaImg from "./../../sources/imgs/playList/nosstalgia.png";
import sadImg from "./../../sources/imgs/playList/sad.png";

import singsongArtist from "./../../sources/imgs/playList/싱송아티스트.png";
import likeArtist from "./../../sources/imgs/playList/좋아아티스트.png";
import singsongSong from "./../../sources/imgs/playList/싱송음악.png";
import likeSong from "./../../sources/imgs/playList/좋아곡.png";

const SEARCH_OPTION = [
  {
    type: "장르",
    option: ["blues", "rock", "hiphop", "jazz", "pop", "reggae"],
  },
  { type: "테마", option: ["happy", "sad", "love", "mystery", "calm", "etc"] },
  { type: "BPM", option: [40, 60, 80, 100, 120, 140, 160, 180, 200] },
  { type: "정렬", option: ["view", "date", "like"] },
];

const GENRE = [
  {
    params: "electronic",
    img: electImg,
  },
  {
    params: "Rock",
    img: rockImg,
  },
  {
    params: "hiphop",
    img: hiphopImg,
  },
  {
    params: "pop",
    img: popImg,
  },
  {
    params: "jazz",
    img: jazzImg,
  },
  {
    params: "classic",
    img: classicImg,
  },
  {
    params: "ballade",
    img: balladeImg,
  },
];

const MOOD = [
  { params: "happy", img: happyImg },
  { params: "sad", img: sadImg },
  { params: "calm", img: calmImg },
  { params: "exciting", img: excitingImg },
  { params: "love", img: loveImg },
  { params: "inspiring", img: inspiringImg },
  { params: "nostalgia", img: nosstalgiaImg },
];

const DiscoverPage = () => {
  const navigate = useNavigate();
  const [searchKeyword, setSearchKeyword] = useState<string>("");
  const [option, setOption] = useState({
    genre: "",
    thema: "",
    bpm: "",
    sort: "",
  });

  const handleMoveToArtistPage = (artistId: string) => {
    navigate(`/artist/${artistId}`);
  };

  const handleKeywordChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchKeyword(event.target.value);
  };

  const handleSearchOption = (
    event: ChangeEvent<HTMLSelectElement>,
    type: string | number
  ) => {
    setOption((prev) => {
      switch (type) {
        case "장르":
          type = "genre";
          break;
        case "테마":
          type = "thema";
          break;
        case "BPM":
          type = "bpm";
          break;
        case "정렬":
          type = "sort";
          break;
      }
      return {
        ...prev,
        [type]: event.target.value,
      };
    });
  };

  useEffect(() => {
    console.log(option);
  }, [option]);

  const handleSearch = () => {
    navigate(
      `result/?keyword=${searchKeyword}&?genre=${option.genre}&bpm=${option.bpm}&atomposhpere=${option.thema}&sort=${option.sort}`
    );
  };

  const handleNavigatePlaylist = (type: string, keyword: string) => {
    navigate(`/discover/playlist/${type}/${keyword}`);
  };

  const handleNavigate = () => {};

  return (
    <div className={`px-main my-main w-100 flex-col gap-30`}>
      <h1 style={{ borderBottom: "2px solid black", paddingBottom: "10px" }}>
        둘러보기
      </h1>
      <div
        id="search"
        className={`w-100 flex-col py-15 gap-15 ${styles.searchBox}`}
      >
        <input
          type="text"
          placeholder="검색할 아티스트 / 노래 제목을 입력해주세요"
          value={searchKeyword}
          onChange={handleKeywordChange}
          className={`w-100 p-15 border-box ${styles.searchBar}`}
        />
        <div className={`flex-row gap-15 ${styles.optionBox}`}>
          {SEARCH_OPTION.map((element) => (
            <select
              key={element.type}
              className={`${styles.selector}`}
              onChange={(event) => handleSearchOption(event, element.type)}
            >
              <option value="">{element.type}</option>
              {element.option.map((options) => (
                <option key={options} value={options}>
                  {options}
                </option>
              ))}
            </select>
          ))}

          <Button id="search" onClick={handleSearch}>
            검색
          </Button>
        </div>
      </div>
      <div className={`flex-col gap-60`}>
        <div>
          <h1 style={{ paddingBottom: "10px" }}>싱송생송 추천</h1>
          <div className={`w-100 flex-row gap-15 space-between`}>
            <div>
              <div
                onClick={() => navigate("/favorite/song")}
                className={`${styles.musicBox}`}
              >
                <img src={likeSong} alt="좋아요 한 곡" />
              </div>
            </div>
            <div>
              <div
                className={`${styles.musicBox}`}
                onClick={() => navigate("weekly-singsong")}
              >
                <img src={singsongSong} alt="싱송음악 top 10" />
              </div>
            </div>
            <div>
              <div
                onClick={() => navigate("weekly-singsong-artist")}
                className={`${styles.musicBox}`}
              >
                <img src={singsongArtist} alt="싱송 아티스트" />
              </div>
            </div>
          </div>
        </div>
        <div>
          <h1 style={{ paddingBottom: "10px" }}>추천 분위기 리스트</h1>
          <StyledSlider>
            {MOOD.map((element) => (
              <div
                key={element.params}
                onClick={() =>
                  handleNavigatePlaylist("mood", element.params as string)
                }
              >
                <div className={`${styles.musicBox}`}>
                  <img src={element.img} alt={element.params} />
                </div>
              </div>
            ))}
          </StyledSlider>
        </div>
        <div>
          <h1 style={{ paddingBottom: "10px" }}>추천 장르 리스트</h1>
          <StyledSlider>
            {GENRE.map((element) => {
              return (
                <div
                  key={element.params}
                  onClick={() =>
                    handleNavigatePlaylist("genre", element.params as string)
                  }
                >
                  <div className={`${styles.musicBox}`}>
                    <img src={element.img} alt={element.params} />
                  </div>
                </div>
              );
            })}
          </StyledSlider>
        </div>
      </div>
    </div>
  );
};

export default DiscoverPage;
