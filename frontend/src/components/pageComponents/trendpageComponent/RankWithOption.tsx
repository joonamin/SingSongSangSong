import React, { ChangeEvent, useEffect, useState } from "react";
import Marquee from "react-fast-marquee";
import styles from "./RankWithOption.module.css";
import RankedSongAndArtist from "./RankedSongAndArtist";
import { axiosInstance } from "../../../hooks/api";

const GENRE = [
  {
    option: "락",
    value: "Rock",
  },
  {
    option: "블루스",
    value: "Blues",
  },
  {
    option: "펑크",
    value: "Funk",
  },
  {
    option: "펑크&소울",
    value: "Funk / Soul",
  },
  {
    option: "힙합",
    value: "Hip Hop",
  },
  {
    option: "재즈",
    value: "jazz",
  },
  {
    option: "레게",
    value: "Reggae",
  },
  {
    option: "팝",
    value: "Pop",
  },
];

const ATMOSPHERE = [
  {
    option: "happy",
    value: "happy",
  },
  {
    option: "sad",
    value: "sad",
  },
  {
    option: "calm",
    value: "calm",
  },
  {
    option: "exciting",
    value: "exciting",
  },
  {
    option: "love",
    value: "love",
  },
  {
    option: "inspiring",
    value: "inspiring",
  },
  {
    option: "nostalgia",
    value: "nostalgia",
  },
  {
    option: "mystery",
    value: "mystery",
  },
  {
    option: "etc",
    value: "etc",
  },
];

/** 장르별 랭킹 / 분위기별 랭킹 컴포넌트
 * @todo 장르 / 분위기 검색할 태그 확정되면 배열만들어서 관리해야함
 * @todo marquu 안의 태그들도 관리해야해서 기억해둬야함
 */
type PropsType = {
  selectedDate: any;
};
const RankWithOption = ({ selectedDate }: PropsType) => {
  const [headerOption, setHeaderOption] = useState<string>("genre");
  const [contentOption, setContentOption] = useState<string>("Rock");
  const [responseData, setResponseData] = useState<any>();
  const [isLoading, setIsLoading] = useState<boolean>(true);

  const handleContentOption = (event: ChangeEvent<HTMLSelectElement>): void => {
    setContentOption(event.target.value);
  };

  // 기본적으로 최상단의 태그옵션으로 변경해주고 다시 데이터를 요청해야함
  const handleHeaderOption = (option: string): void => {
    setHeaderOption(option);
    if (option === "genre") {
      setContentOption("Rock");
    } else if (option === "mood") {
      setContentOption("신나는");
    }
  };

  // 태그가 장르 일 때 실행시킬 useEffect
  useEffect(() => {
    const genreAxios = async () => {
      setIsLoading(true);
      try {
        const response = await axiosInstance.request({
          method: "GET",
          url: "/trend/genre",
          params: {
            date: `${selectedDate.year}-${selectedDate.month}-${selectedDate.day}`,
            // date: `2024-03-20`,
            genre: contentOption,
          },
        });
        // console.log("asujgb aisguaeigiauegnauioe : ", response);
        setResponseData(response.data.data);
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };
    if (headerOption === "genre") {
      // console.log("이거 실행은돠냐?");
      // console.log("장르입니다");
      // console.log(responseData);
      genreAxios();
    }
  }, [headerOption, selectedDate, contentOption]);

  // 태그가 분위기 일 때 실행시킬 useEffect
  useEffect(() => {
    const moodAxios = async () => {
      setIsLoading(true);
      try {
        const response = await axiosInstance.request({
          method: "GET",
          url: "/trend/atmosphere",
          params: {
            date: `${selectedDate.year}-${selectedDate.month}-${selectedDate.day}`,
            atmosphere: contentOption,
          },
        });
        setResponseData(response.data.data);
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };
    if (headerOption === "mood") {
      moodAxios();
    }
  }, [headerOption, selectedDate, contentOption]);

  // useEffect(() => {
  //   console.log(contentOption);
  // }, [contentOption]);

  // console.log("장르 분위기 데이터", responseData);

  if (isLoading) {
    return <p>로딩중입니당</p>;
  }
  return (
    <div className={`flex-col-center gap-15 ${styles.container}`}>
      <div className={styles.header}>
        <button
          className={styles.headerButton}
          disabled={headerOption === "genre"}
          onClick={() => handleHeaderOption("genre")}
        >
          <h1>장르별 랭킹</h1>
        </button>
        <h1>&nbsp;/&nbsp;</h1>
        <button
          className={styles.headerButton}
          disabled={headerOption === "mood"}
          onClick={() => handleHeaderOption("mood")}
        >
          <h1>분위기별 랭킹</h1>
        </button>
      </div>
      {isLoading && <p>로딩중입니당</p>}
      {!isLoading && (
        <div className={`w-100 shadow-box b-15 bg-box`}>
          <Marquee
            pauseOnHover={true}
            autoFill={true}
            className={styles.marquee}
          >
            <p>슬픔 분위기의 곡이 많이 나왔어요.</p>
            <p>180-190BPM 의 곡이 많이 발매되었어요.</p>
            <p>24개의 곡이 발매되었어요.</p>
            <p>7,845번의 재생이 발생했어요.</p>
            <p>C#으로 작성된 곡이 많아요.</p>
          </Marquee>
          {/* 장르 확정되면 태그들 넣어서 바꿔둬야함 */}
          <div className={`flex-row-center ${styles.optionSelectBox}`}>
            {headerOption === "genre" && (
              <select
                className={styles.optionSelector}
                name="contentOption"
                value={contentOption}
                onChange={handleContentOption}
              >
                {GENRE.map((element: any) => {
                  return (
                    <option key={element.value} value={element.value}>
                      {element.option}
                    </option>
                  );
                })}
              </select>
            )}
            {headerOption === "mood" && (
              <select
                className={styles.optionSelector}
                name="contentOption"
                value={contentOption}
                onChange={handleContentOption}
              >
                {ATMOSPHERE.map((element: any) => {
                  return (
                    <option key={element.value} value={element.value}>
                      {element.option}
                    </option>
                  );
                })}
              </select>
            )}
          </div>
          <div className={`flex-row-center ${styles.rankSection}`}>
            <div className={`flex-col-center ${styles.songSection}`}>
              {responseData &&
                responseData.songs.map((element: any) => {
                  return (
                    <RankedSongAndArtist type={"song"} songData={element} />
                  );
                })}
            </div>
            <div className={`flex-col-center ${styles.authorSection}`}>
              {responseData &&
                responseData.artists.map((element: any) => {
                  return (
                    <RankedSongAndArtist
                      type={"author"}
                      showIndicator={false}
                      songData={element}
                    />
                  );
                })}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default RankWithOption;
