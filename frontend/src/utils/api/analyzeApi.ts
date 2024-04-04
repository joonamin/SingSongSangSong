import axios from "axios";
import { axiosInstance } from "../../hooks/api";
import { getCookie } from "../cookie";

/**
 *  분석 페이지 최초 진입 시 실행시킬 함수
 *  현재 useAxios사용해서 요청을 보내고 있을 수도 있습니다
 */
export const getAnalyzeState = async () => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: "/analyze",
    });
  } catch (error) {
    console.log(error);
  }
};

/**
 * 곡 분석을 위한 업로드 axios요청
 * @param inputFile 노래 업로드 파일
 * @returns
 */
export const handleStartAnalyze = async (inputFile: File) => {
  const formData = new FormData();
  console.log(inputFile);
  formData.append(inputFile.type, inputFile);
  const accessToken = getCookie("accessToken");
  try {
    const result = await axios({
      url: `${process.env.REACT_APP_API_URL}upload/audio`,
      method: "POST",
      data: { fileData: inputFile },
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    if (result.status === 200) {
      try {
        const req = await axiosInstance.request({
          method: "POST",
          url: `/analyze/${result.data.data.songId}`,
        });
        return req.data.data;
      } catch (error) {
        console.log(error);
      }
    }
  } catch (error) {
    return error;
  }
};

/**
 * 해당 곡에 대한 분석 요청을 시작
 * @param songId
 */
export const requestAnalyze = async (songId: string | undefined) => {
  try {
    const response = await axiosInstance.request({
      method: "POST",
      url: `/analyze/${songId}`,
    });
  } catch (error) {
    console.log(error);
  }
};

/**
 * 해당 곡에 대한 곡 분석 결과를 요청할 함수
 */
export const getUploadedResult = async (songId: number) => {
  try {
    const response = await axiosInstance.request({
      method: "GET",
      url: `/analyze${songId}`,
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};
