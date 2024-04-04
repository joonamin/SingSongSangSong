import axios from "axios";
import { axiosInstance } from "../../hooks/api";

export const getGenreTrend = async () => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/trend/genre/`,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const getAtmosphereTrend = async () => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/trend/atmosphere`,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const getBpmTrend = async () => {
  try {
    const reponse = await axiosInstance.request({
      method: "GET",
      url: `/trend/bpm`,
    });
    return reponse;
  } catch (error) {
    console.log(error);
  }
};
