import React, { useEffect, useRef, useState } from "react";
import { MdLibraryMusic } from "react-icons/md";
import { GrPowerReset } from "react-icons/gr";
import AudioPlayer from "react-h5-audio-player";
import "react-h5-audio-player/lib/styles.css";
import { current } from "@reduxjs/toolkit";

import styles from "./UploadPage.module.css";
import Button from "../../components/buttons/Button";
import { useAxios } from "../../hooks/api/useAxios";
import { AnalyzedStateType } from "../../utils/types";
import { handleStartAnalyze } from "../../utils/api/analyzeApi";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";
// 참고해야할 사이트
// https://hojung-testbench.tistory.com/entry/React-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84
// https://guiyomi.tistory.com/148

type audioType = {
  file: File;
  url: string;
  name: string;
  type: string;
};

const uploadProcesses = [
  {
    title: "rumble",
    process: "result",
    songId: 200001,
  },
  {
    title: "Rumble (Extended Version)",
    process: "result",
    songId: 200004,
  },
];

const UploadPage = () => {
  const userSlice = useSelector((state: RootState) => state.user);
  const [uploadFile, setUploadFile] = useState<audioType | null>(null);
  const [viewFileName, setViewFileName] = useState<string | null>(null);
  const [isActive, setIsActive] = useState<boolean>(false);
  const fileInputRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();
  const {
    response: analyzedData,
    isLoading: analyzedDataIsLoading,
    refetch: loadAnalyzedData,
  } = useAxios({
    url: "/analyze/status",
    method: "GET",
  });

  // console.log(analyzedData);

  const handleDragEnter = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    // console.log("handleDragEnter");
  };

  const handleDragOver = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    setIsActive(true);
    // console.log("handleDragOver");
  };

  const handleDragLeave = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    if (!event.currentTarget.contains(event.relatedTarget as Node)) {
      // 마우스가 자식 요소에서 떠나면 이벤트 처리
      setIsActive(false);
      // console.log("handleDragLeave");
    }
  };

  const handleDrop = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    const file = event.dataTransfer.files;
    console.log(file[0]);
    if (file && file[0].type.startsWith("audio/")) {
      const url = URL.createObjectURL(file[0]);
      const prevFileName = file[0].name;
      // 파일 이름 앞에 유저 이름을 붙여서 저장 후 전달 (ex. USER${userId}_${musicFileName})
      let newFile = new File(
        [file[0]],
        `USER${userSlice.userId}_${prevFileName}`,
        {
          type: file[0].type,
        }
      );
      // console.log("drop이벤트 시 파일", newFile);
      setUploadFile({
        file: newFile,
        url: url,
        name: newFile.name,
        type: newFile.type,
      });
    }
    setIsActive(false);
  };

  const handleClear = () => {
    setUploadFile(null);
  };

  const handleFileInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    event.preventDefault();
    event.stopPropagation();
    const file = event.target.files;
    if (file && file[0].type.startsWith("audio/")) {
      const url = URL.createObjectURL(file[0]);
      const prevFileName = file[0].name;
      // 파일 이름 앞에 유저 이름을 붙여서 저장 후 전달 (ex. USER${userId}_${musicFileName})
      let newFile = new File(
        [file[0]],
        `USER${userSlice.userId}_${prevFileName}`,
        {
          type: file[0].type,
        }
      );
      console.log(newFile);
      setUploadFile({
        file: newFile,
        url: url,
        name: newFile.name,
        type: file[0].type,
      });
    }
    setIsActive(false);
  };

  const extractString = (str: string) => {
    const match = str.match(/_(.*?)\./);
    return match ? match[1] : null;
  };

  useEffect(() => {
    if (uploadFile !== null) {
      const name = uploadFile.name as string;
      setViewFileName(extractString(name));
    }
  }, [uploadFile]);

  if (analyzedDataIsLoading) {
    return <p>현재 데이터를 로딩중입니다</p>;
  }
  return (
    <div className={`w-100 px-main ${styles.container}`}>
      <div className={`${styles.header}`}>
        <h1>곡 분석하기</h1>
        <p>곡을 업로드하고 자세한 정보를 얻어보세요</p>
      </div>
      <div className={`w-100 ${styles.uploadSection}`}>
        {uploadFile ? (
          <div className={`bg-box flex-col-center ${styles.uploadBox}`}>
            <div className={styles.player}>
              <AudioPlayer src={uploadFile.url} volume={0.5}></AudioPlayer>
            </div>
            <h1 className="flex-row-center" style={{ gap: "15px" }}>
              {viewFileName}{" "}
              <GrPowerReset
                onClick={handleClear}
                style={{ cursor: "pointer" }}
              />
            </h1>
            <Button
              disabled={!uploadFile}
              onClick={() => {
                handleStartAnalyze(uploadFile.file);
                loadAnalyzedData();
                setUploadFile(null);
              }}
            >
              분석하기
            </Button>
          </div>
        ) : (
          <label
            id="musicFileUploader"
            className={`bg-box flex-col-center ${styles.uploadBox} ${
              isActive ? styles.active : ""
            }`}
            onDragEnter={(event) => handleDragEnter(event)}
            onDragOver={(event) => handleDragOver(event)}
            onDragLeave={(event) => handleDragLeave(event)}
            onDrop={(event) => handleDrop(event)}
          >
            <input
              disabled={isActive}
              ref={fileInputRef}
              type="file"
              accept="audio/*"
              className={`${styles.file}`}
              onChange={handleFileInput}
            />
            <MdLibraryMusic
              style={{ pointerEvents: "none" }}
              size={96}
              className={`${isActive ? styles.iconActive : ""}`}
            />
            <h3>오디오 파일을 업로드 해주세요</h3>
          </label>
        )}
      </div>
      <div className={`${styles.checkSection}`}>
        <div className={`flex-row gap-15`}>
          <h1>분석 현황</h1>
          <button
            disabled={analyzedDataIsLoading}
            onClick={loadAnalyzedData}
            className={`flex-col-center ${styles.resetBtn}`}
          >
            <GrPowerReset size={24} />
          </button>
        </div>
        <div className={`w-100 bg-box flex-col-center p-15 ${styles.checkBox}`}>
          {/* {analyzedData.uploadProcesses.length === 0 && (
            <h2>현재 분석중인 음악이 없습니다.</h2>
          )}
          {analyzedData &&
            analyzedData.uploadProcesses.map((element: AnalyzedStateType) => {
              return (
                <div className="flex-row-center gap-30">
                  <h2>{element.title ? element.title : "제목이 없습니다"}</h2>
                  {element.process === "ANALYZING" ? (
                    <h2>분석중</h2>
                  ) : (
                    <h2
                      onClick={() => navigate(`./${element.songId}`)}
                      style={{ color: "blue", cursor: "pointer" }}
                    >
                      분석완료
                    </h2>
                  )}
                </div>
              );
            })} */}
          {uploadProcesses.length === 0 && (
            <h2>현재 분석중인 음악이 없습니다.</h2>
          )}
          {uploadProcesses &&
            uploadProcesses.map((element: AnalyzedStateType) => {
              return (
                <div className="flex-row-center gap-30">
                  <h2
                    style={{
                      borderRight: "5px solid black",
                      paddingRight: "15px",
                    }}
                  >
                    {element.title}
                  </h2>
                  {element.process === "in process" ? (
                    <h2>분석중</h2>
                  ) : (
                    <h2
                      onClick={() => navigate(`./${element.songId}`)}
                      style={{ color: "blue", cursor: "pointer" }}
                    >
                      분석완료
                    </h2>
                  )}
                </div>
              );
            })}
        </div>
      </div>
    </div>
  );
};

export default UploadPage;
