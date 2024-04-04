import React, { useEffect, useState } from "react";

import styles from "./SongHeader.module.css";
import coverimg from "./../../../sources/testimg/cover.png";
import Album from "../../public/Album";
import { useAxios } from "../../../hooks/api/useAxios";
import axios from "axios";
import { getAlbumImg } from "../../../utils/api/downloadFileApi";
import { useParams } from "react-router-dom";

type PropsType = {
  songtitle: string;
  artist: any;
  likeCount: any;
  playCount: any;
  downloadCount: any;
  songFileName: any;
  albumImageFileName: any;
};

const SongHeader = ({
  songtitle,
  artist,
  likeCount,
  playCount,
  downloadCount,
  songFileName,
  albumImageFileName,
}: PropsType) => {
  const [imgFile, setImgFile] = useState<any>();
  const { songId } = useParams();
  // const { response: imgFile, isLoading: imgLoading } = useAxios({
  //   method: "GET",
  //   url: `/download/image/${albumImageFileName}`,
  // });

  // const { response: audioFile, isLoading: audioLoading } = useAxios({
  //   method: "GET",
  //   url: `/download/image/${songFileName}`,
  // });

  useEffect(() => {
    const getAlbum = async () => {
      const imgUrl = await getAlbumImg(albumImageFileName);
      setImgFile(imgUrl);
    };
    getAlbum();
  }, []);

  return (
    <div className={`flex-col-center ${styles.container}`}>
      <div className={styles.background}>
        <img src={imgFile} alt="" />
      </div>
      <div className={`flex-row ${styles.content}`}>
        <div className={`${styles.musicAlbum}`}>
          <Album songId={songId} />
        </div>
        <div className={`flex-col gap-15`}>
          <h1>{songtitle}</h1>
          <h2>{artist.username}</h2>
        </div>
      </div>
    </div>
  );
};

export default SongHeader;
