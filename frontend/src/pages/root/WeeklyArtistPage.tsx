import React from "react";
import { useNavigate, useParams } from "react-router-dom";

import styles from "./WeeklyArtistPage.module.css";
import Profile from "../../components/public/Profile";
import { useAxios } from "../../hooks/api/useAxios";

const WeeklyArtistPage = () => {
  const header = require(`./../../sources/imgs/header/headerimg1.jpg`);
  const navigate = useNavigate();
  const { response, isLoading } = useAxios({
    method: "GET",
    url: "/music-playlist/hot-artist",
  });

  // console.log(response);

  if (isLoading) {
    return <p>로딩중입니당~</p>;
  }

  return (
    <div className={`w-100`}>
      <div className={`w-100 flex-row-center ${styles.header}`}>
        <div className={`w-100 ${styles.headerBackground}`}>
          <img src={header} alt="headerImg" />
        </div>
        <div className={`mt-auto mr-auto flex-col gap-15 ${styles.comment}`}>
          <h1>주간 싱송</h1>
          <h1>아티스트</h1>
        </div>
      </div>
      <div className={`flex-row-center p-15 w-100 ${styles.content}`}>
        {response.map((element: any) => {
          return (
            <div
              className={`flex-col-center gap-15`}
              style={{
                width: "200px",
                height: "200px",
                textAlign: "center",
              }}
            >
              <div
                style={{
                  borderRadius: "50%",
                  width: "100px",
                  height: "100px",
                  textAlign: "center",
                }}
              >
                <Profile artistId={element.artistInfoDto.artistId} />
              </div>
              <h2
                style={{ cursor: "pointer" }}
                onClick={() =>
                  navigate(`/artist/${element.artistInfoDto.artistId}`)
                }
              >
                {element.artistInfoDto.nickname}
              </h2>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default WeeklyArtistPage;
