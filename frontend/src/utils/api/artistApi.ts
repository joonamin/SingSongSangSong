import axios, { AxiosResponse } from "axios";
import { axiosInstance } from "../../hooks/api";
import { getCookie } from "../cookie";
import { useAxios } from "../../hooks/api/useAxios";

export const getArtist = async (artistId: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/artist/${artistId}`,
    });
    // console.log("getArtist : ", response);
    return response;
  } catch (error) {
    console.log("error occur at getArtist");
  }
};

/**
 * 아티스트 팔로우 기능
 * @param artistId 팔로우할 아티스트 아이디
 * @param followingArtistId 팔로워 아티스트 아이디
 */
export const followArtist = async (artistId: any) => {
  try {
    const reponse = await axiosInstance.request({
      method: "POST",
      url: `/artist/follow/${artistId}`,
    });
  } catch (error) {
    console.log(error);
  }
};

/**
 * 아티스가 게시한 곡들을 가져오는 함수
 * @param artistId
 * @returns 곡 배열
 */
export const getSongList = async (artistId: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/artist/song/${artistId}`,
    });
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};

/**
 * 아티스트의 모든 곡들에 대한 emotions을 가져옴
 * @param artistId
 * @returns emotion 배열
 */
export const getEmotions = async (artistId: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/artist/emotions/${artistId}`,
    });
    return response?.data?.data;
  } catch (error) {
    console.log(error);
  }
};

/**
 * 사용자 프로필 변경 요청 axios
 * @param file 프로필 이미지를 설정할 이미지 파일
 */
export const addProfileImage = async (file: File) => {
  const accessToken = getCookie("accessToken");
  const formData = new FormData();
  console.log(file);
  formData.append("file", file);

  try {
    const response = await axios({
      method: "POST",
      url: "/upload/image",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log("프로필 변경 요청 응답", response);
  } catch (error) {
    console.log(error);
  }
};

/**
 * 해당 아티스트의 팔로워 수들을 가져오는 axios 요청
 * @param artistId 아티스트 아이디
 * @returns 팔로워 수
 */
export const getFollowerCount = async (artistId: any) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/artist/followers/${artistId}/count`,
    });
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
};
