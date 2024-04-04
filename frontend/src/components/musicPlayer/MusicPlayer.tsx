import React, { useEffect, useRef, useState } from "react";

import AudioPlayer from "react-h5-audio-player";
import "react-h5-audio-player/lib/styles.css";

import styles from "./MusicPlayer.module.css";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { playSong } from "../../utils/api/songDetailApi";

const MusicPlayer = () => {
  const trackList = useSelector((state: RootState) => state.music);
  const player = useRef<any>();
  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const [currentMusic, setCurrentMusic] = useState();

  useEffect(() => {
    // console.log(player.current.audio);
    player.current.audio.current.addEventListener("ended", (event: any) => {
      // console.log("ended");
      setIsPlaying(false);
    });
    player.current.audio.current.addEventListener("play", (event: any) => {
      // console.log("playing");
      setIsPlaying(true);
    });
  }, []);

  useEffect(() => {}, [trackList.isPlaying]);

  return (
    <div
      className={`w-100 ${styles.container} ${isPlaying ? styles.show : ""}`}
    >
      <AudioPlayer src={trackList.musicList} ref={player} volume={0.5} />
    </div>
  );
};

export default MusicPlayer;
