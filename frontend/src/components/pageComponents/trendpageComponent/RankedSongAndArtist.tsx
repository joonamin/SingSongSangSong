import React from "react";
import { FaHeart } from "react-icons/fa";
import { LiaHeadsetSolid } from "react-icons/lia";
import { MdOutlineFileDownload } from "react-icons/md";

import styles from "./RankedSongAndArtist.module.css";
import Album from "../../public/Album";
import Profile from "../../public/Profile";
import { useNavigate } from "react-router-dom";

type PropsType = {
  type: string;
  showIndicator?: boolean;
  resData?: any;
  songData?: any;
};

/**
 * 장르 / 분위기별 랭킹에 사용될 컴포넌트.
 * 타입으로 song이랑 author를 받아와서 각각 다르게 표현해줘야함
 * @todo 받아오는 값은 songId하나면 충분
 * @todo 곡 / 작곡가 클릭 시 상세 페이지로 이동하는 로직 작성
 * @todo 데이터 들어오면 info쪽 수정
 */
const RankedSongAndArtist = ({
  type,
  showIndicator = true,
  songData,
}: PropsType) => {
  const checker = () => {};
  const navigate = useNavigate();
  // console.log(songData);
  if (!songData) {
    return <p>음악 데이터가 존재하지 않습니다</p>;
  }

  return (
    <div className={`flex-row-center ${styles.container}`}>
      <div className={`flex-col-center ${styles.album}`}>
        {type === "song" && <Album songId={songData.songId} />}
        {type === "author" && <Profile artistId={songData.artistId} />}
      </div>
      <div className={`flex-col-center ${styles.info}`}>
        {type === "song" && (
          <>
            <h2 onClick={checker}>{songData.songTitle}</h2>
            <h3>{songData.artist.nickname}</h3>
          </>
        )}
        {type === "author" && (
          <>
            <h1
              onClick={() => navigate(`/artist/${songData.artistId}`)}
              style={{ cursor: "pointer" }}
            >
              {songData.nickname}
            </h1>
          </>
        )}
      </div>
      {showIndicator && (
        <div className={`flex-col-center ${styles.indicator}`}>
          <p>
            <MdOutlineFileDownload size={"28px"} />
            {songData.downloadCount}
          </p>
          <p>
            <LiaHeadsetSolid size={"28px"} />
            {songData.playCount}
          </p>
          <p>
            <FaHeart size={"24px"} />
            {songData.likeCount}
          </p>
        </div>
      )}
    </div>
  );
};

export default RankedSongAndArtist;
