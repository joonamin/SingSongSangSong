import { createSlice } from "@reduxjs/toolkit";
import { isoParse } from "d3-time-format";
import { playSong } from "../utils/api/songDetailApi";

interface MusicState {
  isPlaying: boolean;
  musicList: any;
  songId: any;
}

const musicSlice = createSlice({
  name: "music",
  initialState: {
    isPlaying: false,
    songId: null,
    musicList: null,
  } as MusicState,
  reducers: {
    changeState: (state) => {
      state.isPlaying = !state.isPlaying;
    },
    addMusicList: (state, action) => {
      state.musicList = action.payload;
    },
    setSongId: (state, action) => {
      state.songId = action.payload;
    },
  },
});

export const musicAction = musicSlice.actions;
export default musicSlice;
