import React, { useEffect, useState } from "react";
import img from "./../../../sources/testimg/sectionImg.png";
import styles from "./MusicSectionIndicator.module.css";
import SectionButton from "./SectionButton";
import { useParams } from "react-router-dom";
import { useAxios } from "../../../hooks/api/useAxios";
import { axiosInstance } from "../../../hooks/api";

/**
 * 구간분석용 지표가 나오는 컴포넌트.
 * 곡상세 페이지 및, 분석결과 페이지에서 사용할 예정
 *
 * memo : 잘려진 구간들을 반복문을 사용해서(아직 적용 안함) 반복 출력해야함
 * 데이터 넣어줘야하는건 구간에대한 타입과 start,end point
 */

const MusicSectionIndicator = () => {
  const { songId } = useParams();
  const [response, setResponse] = useState<any>();
  const [sectionImg, setSectionImg] = useState<any>();
  const [isLoading, setIsloading] = useState<boolean>(true);

  useEffect(() => {
    const request = async () => {
      setIsloading(true);
      try {
        const songDetail = await axiosInstance.request({
          method: "GET",
          url: `/song/detail/${songId}`,
        });
        if (songDetail) {
          try {
            const sectionData = await axiosInstance.request({
              method: "GET",
              url: `/song/section/${songId}`,
              params: {
                spectrumImageId: songDetail.data.data.spectrumImageId,
              },
            });
            setResponse(sectionData.data.data);
            if (sectionData) {
              try {
                const secImg = await axiosInstance.request({
                  method: "GET",
                  url: `/download/spectrum/${songDetail.data.data.spectrumImageId}`,
                  responseType: "blob",
                });
                const imgUrl = URL.createObjectURL(secImg.data);
                setSectionImg(imgUrl);
              } catch (error) {
                console.log(`error at get sectionImg`);
              }
            }
          } catch (error) {
            console.log(error);
          }
        }
      } catch (error) {
        console.log(error);
      }
      setIsloading(false);
    };
    request();
  }, []);

  if (isLoading) {
    return <p>구간 데이터를 조회중입니다.</p>;
  }

  return (
    <div className={`flex-col-center ${styles.content}`}>
      <h2>각 구간은 이런 특징을 가져요</h2>
      <div className={`${styles.imgSection}`}>
        <img src={sectionImg} alt="sectionimg" />
      </div>
      <div className={`${styles.tagSection}`}>
        <div className={`flex-row-center ${styles.innerSection}`}>
          <div className={styles.type}>
            <h3>intro</h3>
          </div>
          {response
            .filter((element: any) => element.label === "intro")
            .map((element: any) => {
              return (
                <SectionButton
                  section="intro"
                  startPoint={element.start}
                  endPoint={element.end}
                />
              );
            })}
        </div>
        <div className={`flex-row-center ${styles.innerSection}`}>
          <div className={styles.type}>
            <h3>verse</h3>
          </div>
          {response
            .filter((element: any) => element.label === "verse")
            .map((element: any) => {
              return (
                <SectionButton
                  section="verse"
                  startPoint={element.start}
                  endPoint={element.end}
                />
              );
            })}
        </div>
        <div className={`flex-row-center ${styles.innerSection}`}>
          <div className={styles.type}>
            <h3>chorus</h3>
          </div>
          {response
            .filter((element: any) => element.label === "chorus")
            .map((element: any) => {
              return (
                <SectionButton
                  section="chorus"
                  startPoint={element.start}
                  endPoint={element.end}
                />
              );
            })}
        </div>
        <div className={`flex-row-center ${styles.innerSection}`}>
          <div className={styles.type}>
            <h3>bridge</h3>
          </div>
          {response
            .filter((element: any) => element.label === "bridge")
            .map((element: any) => {
              return (
                <SectionButton
                  section="bridge"
                  startPoint={element.start}
                  endPoint={element.end}
                />
              );
            })}
        </div>
        <div className={`flex-row-center ${styles.innerSection}`}>
          <div className={styles.type}>
            <h3>outro</h3>
          </div>
          {response
            .filter((element: any) => element.label === "outro")
            .map((element: any) => {
              return (
                <SectionButton
                  section="outro"
                  startPoint={element.start}
                  endPoint={element.end}
                />
              );
            })}
        </div>
      </div>
    </div>
  );
};

export default MusicSectionIndicator;
