import React from "react";
import { useAxios } from "../../hooks/api/useAxios";
import MusicTable from "../../components/public/music/MusicTable";
import TestMusicTable from "../../components/public/music/TestMusicTable";

const WeeklyMusicPage = () => {
  const { response, isLoading, refetch } = useAxios({
    method: "GET",
    url: "/music-playlist/weekly-hitsong",
  });

  // console.log(response);

  if (isLoading) {
    <p>곡정보를 로딩중입니다</p>;
  }

  return (
    <div className={`w-100 flex-col-center`}>
      <TestMusicTable musicData={response} />
    </div>
  );
};

export default WeeklyMusicPage;
