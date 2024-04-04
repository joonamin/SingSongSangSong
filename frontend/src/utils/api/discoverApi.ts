import axios from "axios";
import { AxiosInstance } from "axios";
import { useAxios } from "../../hooks/api/useAxios";
import { axiosInstance } from "../../hooks/api";

export const getLikeSong = async (
  artistId: number,
  offset: number,
  limit: number
) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: "",
      data: {
        artistId: artistId,
        offset: offset,
        limit: limit,
      },
    });
  } catch (error) {
    console.log(error);
  }
};

export const getFollowerNumber = async (artistId: string) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/artist/followers/${artistId}/count`,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const getGenreList = async (genre: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/music-playlist/genre-hitsong/${genre}`,
      data: {
        Genre: genre,
      },
    });
    return response?.data?.data;
  } catch (error) {
    console.log(error);
  }
};

export const getAtmosphereList = async (atmosphere: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/music-playlist/atmosphere-hitsong/${atmosphere}`,
      data: {
        Atmosphere: atmosphere,
      },
    });
    return response?.data?.data;
  } catch (error) {
    console.log(error);
  }
};

export const getWeeklyArtist = async () => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/mustic-playlist/hot-artist`,
    });
    return response?.data?.data;
  } catch (error) {
    console.log(error);
  }
};

export const getWeeklySong = async () => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/music-playlist/weekly-hitsong`,
    });
    return response?.data?.data;
  } catch (error) {
    console.log(error);
  }
};
