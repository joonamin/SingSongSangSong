import axios, { AxiosResponse } from "axios";

import { getCookie } from "../../utils/cookie";

export const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: false,
});

axiosInstance.interceptors.request.use(
  async (config) => {
    const accessToken = await getCookie("accessToken");
    // console.log("is there?", accessToken);
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    // console.log("axios config : ", config);
    return config;
  },
  (error) => {
    console.log("axios error : ", error);
    return Promise.reject(error);
  }
);

// axios.interceptors.response.use(
//   async (response: AxiosResponse) => {
//     return response;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );
