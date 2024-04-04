import React, { useEffect } from "react";
import styles from "./Comments.module.css";
import Profile from "../Profile";
import { commentType } from "../../../utils/types";
import { useNavigate } from "react-router-dom";
import { getAlbumImg } from "../../../utils/api/downloadFileApi";

type CommentType = {
  authorId?: number;
  artistNickname?: any;
  content?: string;
  imgFileName?: any;
};

const Comments = ({
  authorId,
  artistNickname,
  content,
  imgFileName,
}: CommentType) => {
  useEffect(() => {
    const request = async () => {
      const imgUrl = getAlbumImg(imgFileName);
    };
  }, []);

  const navigate = useNavigate();
  return (
    <div className={`flex-row-center ${styles.container}`}>
      <div className={`flex-col-center ${styles.user}`}>
        <div className={styles.profileImg}>
          <Profile artistId={authorId} />
        </div>
        <div className={`${styles.userName}`}>
          <p
            style={{ cursor: "pointer" }}
            onClick={() => navigate(`/artist/${authorId}`)}
          >
            {artistNickname}
          </p>
        </div>
      </div>
      <div className={`${styles.textArea}`}>
        <p>{content}</p>
      </div>
    </div>
  );
};

export default Comments;
