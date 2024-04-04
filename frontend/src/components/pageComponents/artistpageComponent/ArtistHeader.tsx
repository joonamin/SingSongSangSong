import React, { useEffect, useState } from "react";
import { FaRegHeart, FaShareAlt, FaHeart } from "react-icons/fa";
import { BsBookmarkPlusFill, BsFillBookmarkDashFill } from "react-icons/bs";

import { followArtist, getFollowerCount } from "../../../utils/api/artistApi";
import img from "./../../../sources/testimg/artistProfile.jpg";
import styles from "./ArtistHeader.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import { getPresignedUrl } from "../../../utils/api/minioApi";
import { fileURLToPath } from "url";
import axios from "axios";
import { axiosInstance } from "../../../hooks/api";
import { getAlbumImg } from "../../../utils/api/downloadFileApi";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";

type PropsType = {
  artistId: any;
  nickname: string;
  introduction: string;
  profileImageFileName: string;
  countPublishedSong: any;
};
/**
 * 아티스트 페이지에 사용할 헤더 컴포넌트
 * @todo 팔로우기능 hover 활성화, like기능 hover 활성화, 팔로우 / 좋아요 state는 위에서 받아와야할듯
 * @todo 조건따라서 상단 import 받아온, 이미지 사용해서 팔로우 / 언팔로우, 라이크 / 언라이크 상태 변경
 */
const ArtistHeader = ({
  artistId,
  nickname,
  introduction,
  profileImageFileName,
  countPublishedSong,
}: PropsType) => {
  const userSlice = useSelector((state: RootState) => state.user);

  const [imageURL, setImageURL] = useState();
  const [trigger, setTrigger] = useState<any>(null);
  const [artistProfile, setArtistProfile] = useState<any>();
  const [followerCount, setFollowerCount] = useState<any>();

  const handleShare = () => {
    const currentUrl = window.location.href;
    navigator.clipboard.writeText(currentUrl);
    alert("주소가 복사되었습니다");
  };

  const handleFollow = async () => {
    await followArtist(artistId);
    setTrigger(new Date());
  };

  useEffect(() => {
    const getInfo = async () => {
      const imgUrl = await getAlbumImg(profileImageFileName);
      const followers = await getFollowerCount(artistId);
      setFollowerCount(followers.followerCount);
      setArtistProfile(imgUrl);
    };
    getInfo();
  }, [trigger]);

  return (
    <div className={`flex-row-center ${styles.container}`}>
      <div className={`${styles.background}`}>
        <img src={artistProfile} alt="" />
      </div>
      <div className={`mx-auto flex-row-center ${styles.content}`}>
        <img src={artistProfile} alt="" className={styles.artistProfile} />
        <div className={`my-auto flex-col-center ${styles.artistInfo}`}>
          <h1>{nickname}</h1>
          <div className={styles.artistIntroduce}>
            <p>{introduction}</p>
            <div className={styles.contactButton}>
              {userSlice.userId == artistId ? (
                ""
              ) : (
                <p onClick={handleFollow}>
                  <BsBookmarkPlusFill /> Follow
                </p>
              )}

              {/* <p>
                <FaRegHeart /> Like
              </p> */}
              <p onClick={handleShare}>
                <FaShareAlt /> Share
              </p>
            </div>
          </div>
        </div>
        <div
          className={`mb-auto ml-auto flex-row-center ${styles.numberIndicator}`}
        >
          <span
            className={`flex-col-center`}
            style={{ borderRight: "1px solid white", paddingRight: "10px" }}
          >
            <span style={{ fontSize: "32px" }}>{followerCount}</span>
            <span style={{ fontSize: "14px" }}>FOLLOWER</span>
          </span>
          <span className={`flex-col-center`}>
            <span style={{ fontSize: "32px" }}>{countPublishedSong}</span>
            <span style={{ fontSize: "14px" }}>TRACKS</span>
          </span>
        </div>
      </div>
    </div>
  );
};

export default ArtistHeader;
