import React, {
  DragEvent,
  ChangeEvent,
  useState,
  useRef,
  useEffect,
} from "react";

import { addProfileImage } from "../../utils/api/artistApi";
import { GrPowerReset } from "react-icons/gr";

import styles from "./PostPage.module.css";
import Button from "../../components/buttons/Button";

type ImgType = {
  file: File;
  img: string;
  type: string;
};

const PostPage = () => {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [albumImg, setAlbumImg] = useState<ImgType | null>(null);
  const [songTitle, setSongTitle] = useState<string>("");
  const [songInfo, setSongInfo] = useState<string>("");
  const [songLyrics, setSongLyrics] = useState<string>("");
  const [isHover, setIsHover] = useState<boolean>(false);

  const handleDragEnter = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    console.log("handleDragEnter");
  };

  const handleDragOver = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    console.log("handleDragOver");
  };

  const handleDragLeave = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    if (!event.currentTarget.contains(event.relatedTarget as Node)) {
      console.log("handleDragLeave");
    }
  };

  const handleDrop = (event: DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    event.stopPropagation();
    const data = event.dataTransfer.files;
    if (data && data[0].type.startsWith("image/")) {
      const url = URL.createObjectURL(data[0]);
      console.log(url);
      setAlbumImg({
        file: data[0],
        img: url,
        type: data[0].type,
      });
    }
  };

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSongTitle(event.target.value);
  };

  const handleInfoChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setSongInfo(event.target.value);
  };

  const handleLyricsChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setSongLyrics(event.target.value);
  };

  const handleFileUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    console.log(files);
    if (files && files[0] && files[0].type.startsWith("image/")) {
      const url = URL.createObjectURL(files[0]);
      setAlbumImg({
        file: files[0],
        img: url,
        type: files[0].type,
      });
    }
  };

  const handleMouseEnter = () => {
    setIsHover(true);
  };

  const handleMouseLeave = () => {
    setIsHover(false);
  };

  const handleReset = () => {
    setAlbumImg(null);
  };

  const handleSubmit = () => {};

  useEffect(() => {
    console.log(albumImg);
  }, [albumImg]);

  return (
    <div className={`w-100 px-main flex-col gap-15 ${styles.container}`}>
      <div className={`flex-col ${styles.header}`}>
        <h1>곡 게시</h1>
        <h3>곡 정보 설정</h3>
      </div>
      <div className={`w-100 flex-col gap-30 ${styles.content}`}>
        <div className={`w-100 flex-row py-15`}>
          {albumImg ? (
            <div
              className={`${styles.albumCover}`}
              onMouseEnter={handleMouseEnter}
              onMouseLeave={handleMouseLeave}
            >
              <img
                style={{
                  width: "200px",
                  height: "200px",
                  borderRadius: "15px",
                }}
                src={albumImg.img}
                alt="albumImg"
              ></img>
              {isHover ? (
                <div className={`flex-col-center ${styles.innerResetBtn}`}>
                  <GrPowerReset
                    size={32}
                    style={{ cursor: "pointer" }}
                    onClick={handleReset}
                  />
                </div>
              ) : null}
            </div>
          ) : (
            <label
              htmlFor="albumImg"
              className={`flex-col-center p-15 ${styles.inputForm}`}
              onDragEnter={(event) => handleDragEnter(event)}
              onDragOver={(event) => handleDragOver(event)}
              onDragLeave={(event) => handleDragLeave(event)}
              onDrop={(event) => handleDrop(event)}
            >
              <input
                type="file"
                id="albumImg"
                className={`${styles.imgInput}`}
                accept="image/jpeg, image/png, image/jpg"
                ref={fileInputRef}
                onChange={handleFileUpload}
                required
              />
              <h3>앨범 사진을 업로드 해주세요</h3>
            </label>
          )}
          <div className={`flex-col gap-15 px-15 ${styles.inputRightBox}`}>
            <h2>곡 이름</h2>
            <input
              type="text"
              placeholder="곡 제목을 입력해주세요"
              className={`border-box p-15 ${styles.titleInput}`}
              maxLength={32}
              value={songTitle}
              onChange={handleTitleChange}
            />
          </div>
        </div>
        <div className={`w-100 flex-col gap-15 py-15`}>
          <h2>곡설명</h2>
          <textarea
            name="songInfo"
            id="songInfo"
            cols={30}
            rows={5}
            placeholder="곡에대한 설명을 입력해주세요"
            className={`p-15 border-box ${styles.postTextarea}`}
            onChange={handleInfoChange}
            value={songInfo}
          ></textarea>
        </div>
        <div className={`w-100 flex-col gap-15 py-15`}>
          <h2>가사</h2>
          <textarea
            name="songLyrics"
            id="songLyrics"
            cols={30}
            rows={5}
            placeholder="곡의 가사를 입력해주세요"
            className={`p-15 border-box ${styles.postTextarea}`}
            value={songLyrics}
            onChange={handleLyricsChange}
          ></textarea>
        </div>
      </div>
      <Button onClick={handleSubmit}>게시하기</Button>
    </div>
  );
};

export default PostPage;
