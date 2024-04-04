import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { musicAction } from "../../store/musicSlice";
import styles from "./Album.module.css";
import { FaPlay } from "react-icons/fa";
import { RootState } from "../../store";
import { getAlbumImg, getMp3File } from "../../utils/api/downloadFileApi";
import { axiosInstance } from "../../hooks/api";
import { playSong } from "../../utils/api/songDetailApi";

/** 앨범 이미지를 받아와서 해당 앨범을 hover하면 재생 버튼이 보이고
 * 클릭시 음악을 재생시켜줄 컴포넌트
 */

type PropsType = {
  songId?: any;
};
const Album = ({ songId }: PropsType) => {
  const dispatch = useDispatch();
  const music = useSelector((state: RootState) => state.music);
  const [isLoading, setIsloading] = useState<boolean>(false);
  const [albumImg, setAlbumImg] = useState<any>();
  const [audioFile, setAudioFile] = useState<any>();

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
  }, [songId]);

  return (
    <div className={`flex-col-center ${styles.container}`}>
      <img src={albumImg} alt="albumImg" />
      <div className={styles.overlay}>
        <button
          onClick={() => {
            dispatch(musicAction.setSongId(songId));
            setTimeout(() => playSong(songId), 1000);
            dispatch(musicAction.addMusicList(audioFile));
          }}
        >
          <FaPlay size={"24px"} />
        </button>
        {}
      </div>
    </div>
  );
};

export default Album;
