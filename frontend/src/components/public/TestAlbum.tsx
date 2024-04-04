import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import styles from "./TestAlbum.module.css";
import { musicAction } from "../../store/musicSlice";
import { FaPlay } from "react-icons/fa";
import { RootState } from "../../store";
import { useAxios } from "../../hooks/api/useAxios";
import { axiosInstance } from "../../hooks/api";
import { getAlbumImg, getMp3File } from "../../utils/api/downloadFileApi";
import Spinner from "./../../sources/imgs/spinner.gif";

/** 앨범 이미지를 받아와서 해당 앨범을 hover하면 재생 버튼이 보이고
 * 클릭시 음악을 재생시켜줄 컴포넌트
 */

type PropsType = {
  songId: number;
};
const TestAlbum = ({ songId }: PropsType) => {
  const music = useSelector((state: RootState) => state.music);
  const [isLoading, setIsloading] = useState<boolean>(false);
  const [albumImg, setAlbumImg] = useState<any>();
  const [audioFile, setAudioFile] = useState<any>();

  const dispatch = useDispatch();
  const currentMusic = audioFile;

  useEffect(() => {
    const getFileData = async () => {
      setIsloading(true);
      try {
        const res = await axiosInstance.request({
          method: "GET",
          url: `/song/detail/${songId}`,
        });
        const songDetail = res.data.data;
        const tempImg = await getAlbumImg(songDetail.albumImageFileName);
        // const blob = new Blob([tempImg], { type: "image/png" });
        // const imageUrl = URL.createObjectURL(tempImg);
        // console.log("tempImg", tempImg);
        const tempAudio = await getMp3File(songDetail.songFileName);
        // setAlbumImg(imageUrl);
        setAlbumImg(tempImg);
        setAudioFile(tempAudio);
        setIsloading(false);
      } catch (error) {
        setIsloading(false);
        console.log(error);
      }
    };
    getFileData();
  }, []);

  if (isLoading) {
    return (
      <div
        style={{ width: "200px", height: "200px" }}
        className={`flex-row-center`}
      >
        <img
          style={{ width: "10px", height: "10px" }}
          src={Spinner}
          alt="spinner"
        />
      </div>
    );
  }
  return (
    <div className={`flex-col-center ${styles.container}`}>
      <div className={`${styles.albumImg}`}>
        <img src={albumImg} alt="" />
      </div>
      <div className={styles.overlay}>
        <button
          onClick={() => dispatch(musicAction.addMusicList(currentMusic))}
        >
          <FaPlay size={"24px"} />
        </button>
        {}
      </div>
    </div>
  );
};

export default TestAlbum;
