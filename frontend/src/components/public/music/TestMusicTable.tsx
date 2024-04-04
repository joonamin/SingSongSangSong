import React, { Fragment } from "react";
import { FaStar, FaRegStar } from "react-icons/fa";

import styles from "./TestMusicTable.module.css";
import Album from "../Album";
import { useNavigate } from "react-router-dom";
import TestAlbum from "../TestAlbum";

const DUMMY = [
  {
    album: "1234",
    title: "제목1",
    genre: "발라드",
    mood: "신나는",
    artist: "김작곡",
    length: "3:23",
  },
];
type PropsType = {
  musicData: any[];
};

const TestMusicTable = ({ musicData }: PropsType) => {
  const navigate = useNavigate();

  const handleNavaigateArtist = (artist: string) => {
    navigate(`/artist/${artist}`);
  };

  const handleNavaigateSong = (song: string) => {
    navigate(`/song/${song}`);
  };

  console.log("test music table에서의 뮤직 데이터", musicData);

  if (!musicData) {
    return <p>데이터가업서요</p>;
  }
  return (
    <div className={`w-100 flex-col-center`}>
      <table>
        <thead className={`${styles.head}`}>
          <tr>
            <th>&nbsp;</th>
            <th>제목</th>
            <th>장르</th>
            <th>분위기</th>
            <th>아티스트</th>
            <th>길이</th>
            <th>&nbsp;</th>
          </tr>
        </thead>
        <tbody className={`${styles.musicBody}`}>
          {musicData.map((element, index) => {
            return (
              <tr key={element.artist}>
                <td>
                  <div
                    style={{
                      width: "100px",
                      height: "100px",
                      margin: "auto",
                      border: "15px",
                    }}
                  >
                    <TestAlbum songId={element.songId} />
                  </div>
                </td>
                <td>
                  <p
                    style={{ cursor: "pointer" }}
                    onClick={() => handleNavaigateSong(element.songId)}
                  >
                    {element.title}
                  </p>
                </td>
                <td>{element.genre}</td>
                <td>{element.atmosphere ? element.atmosphere : "x"}</td>
                <td>
                  <p
                    style={{ cursor: "pointer" }}
                    onClick={() => handleNavaigateArtist(element.artistId)}
                  >
                    {element.artistName}
                  </p>
                </td>
                <td>{`${Math.floor(element.duration / 60)}:${
                  element.duration % 60
                }`}</td>
                <td>
                  <FaRegStar style={{ cursor: "pointer" }} />
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default TestMusicTable;
