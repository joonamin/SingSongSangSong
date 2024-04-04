import { useState, useEffect, useRef, useCallback } from "react";
import { json } from "react-router-dom";
import { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";

import { axiosInstance } from ".";

// 참고 사이트 : https://velog.io/@sorin44/useAxios-axios-instance
// 참고 사이트 : https://sugarsyrup.tistory.com/entry/Axios-Custom-Hook-TypeScript-%ED%99%98%EA%B2%BD%EC%9C%BC%EB%A1%9C-%EB%A7%8C%EB%93%A4%EA%B8%B0
// 토큰 인증 관련 : https://khys.tistory.com/56
// 참고 사이트 : https://velog.io/@xmun74/axios-interceptors-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

/**
 * @param axiosParams {object} { url : string, method : string }
 * @returns {obejct} { isLoading : boolean, reponse : Response, error : Error }
 */

export const useAxios = <D = any>(axiosParams: AxiosRequestConfig<D>) => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [trigger, setTrigger] = useState(0);
  const [response, setResponse] = useState<AxiosResponse | null | any>(null);
  const [error, setError] = useState<AxiosError | null | any>(null);

  const axiosData = async (params: AxiosRequestConfig<D>) => {
    setIsLoading(true);
    try {
      const result = await axiosInstance.request({
        ...params,
      });
      setResponse(result?.data?.data);
      setIsLoading(false);
    } catch (error: AxiosError | any | unknown) {
      setError(error);
      setIsLoading(false);
    }
  };

  const refetch = () => {
    setIsLoading(true);
    setTrigger(Date.now());
  };

  useEffect(() => {
    axiosData(axiosParams);
  }, [trigger]);

  return { isLoading, response, error, refetch };
};
