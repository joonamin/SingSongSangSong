import React, { useEffect } from "react";
import axios from "axios";
import {
  defer,
  useLoaderData,
  useNavigate,
  useSearchParams,
} from "react-router-dom";

import { SearchType, SearchParmasType } from "../../utils/types";
import MusicTable from "../../components/public/music/MusicTable";
import styles from "./SearchResultPage.module.css";
import { getSearchResult } from "../../utils/api/api";
import { useAxios } from "../../hooks/api/useAxios";
import TestMusicTable from "../../components/public/music/TestMusicTable";
import Profile from "../../components/public/Profile";

const SearchResultPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const keyword = searchParams.get("keyword");
  const genre = searchParams.get("genre");
  const bpm = searchParams.get("bpm");
  const atmosphere = searchParams.get("atmosphere");
  const sort = searchParams.get("sort");

  const userSearchParams: SearchParmasType = {
    keyword,
    genre,
    bpm,
    atmosphere,
    sort,
  };

  const { response, refetch, isLoading } = useAxios({
    method: "GET",
    url: "/music-playlist/search",
    params: {
      keyword,
      genre,
      atmosphere,
      bpm,
      sort,
    },
  });
  // console.log("검색인자", keyword, genre, atmosphere, bpm, sort);
  // console.log("검색결과", response);
  const navigate = useNavigate();
  // useEffect(() => {
  //   console.log(userSearchParams);
  //   console.log(getSearchResult(userSearchParams));
  // }, []);

  if (isLoading) {
    return <p>검색결과를 가져오고 있어요.</p>;
  }

  if (!response) {
    return <p>일치하는 검색 결과가 없습니다.</p>;
  }

  return (
    <div className={`w-100 flex-col px-main my-main gap-15`}>
      <h1>검색 결과</h1>
      <div className={`w-100 flex-col py-15 gap-15`}>
        <h2 className={`${styles.borderBottom}`}>아티스트</h2>
        <div className={`flex-row-center gap-15 ${styles.profileBox}`}>
          {response.artistInfoDtoList.map((element: any) => {
            return (
              <div
                className={`flex-col-center`}
                style={{ width: "200px", height: "150px" }}
              >
                <div style={{ width: "100px", height: "100px" }}>
                  <Profile artistId={element.artistId} />
                </div>
                <h2
                  style={{ cursor: "pointer" }}
                  onClick={() => navigate(`/artist/${element.artistId}`)}
                >
                  {element.nickname}
                </h2>
              </div>
            );
          })}
        </div>
      </div>
      <div className={`w-100 flex-col py-15 gap-15`}>
        <h2 className={`${styles.borderBottom}`}>음악</h2>
        <TestMusicTable musicData={response.songBriefDtoList} />
      </div>
    </div>
  );
};

export default SearchResultPage;
