import axios from "axios";

import { axiosInstance } from "../../hooks/api";
import { SearchParmasType } from "../types";

export const getSearchResult = async (userSearchParams: SearchParmasType) => {
  // const response = axiosInstance.request({
  //   method: "GET",
  //   url: "",
  //   params: {
  //     keyword : userSearchParams.keyword,
  //     genre : userSearchParams.genre,
  //     atmosphere : userSearchParams.atmosphere,
  //     bpm : userSearchParams.bpm,
  //     sort : userSearchParams.sort
  //   },
  // });
};

export const followArtist = async (
  artistId: string,
  followArtistId: string
) => {
  try {
    const response = axiosInstance.request({
      url: "/artist/follow",
      data: {
        artistId,
        followArtistId,
      },
      method: "POST",
    });
  } catch (error) {
    console.log(error);
  }
};

export const updateEmotion = async () => {
  try {
    axiosInstance.request({
      method: "PUT",
      url: `/song/{songId}/{emotion}`,
    });
  } catch (error) {
    console.log(error);
  }
};
