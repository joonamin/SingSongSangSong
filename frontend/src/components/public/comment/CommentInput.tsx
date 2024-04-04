import React, { ChangeEvent, useState } from "react";
import { ActionFunction, Form, useParams } from "react-router-dom";

import styles from "./CommentInput.module.css";
import axios from "axios";
import { axiosInstance } from "../../../hooks/api";
import { postComment } from "../../../utils/api/songDetailApi";

/**
 * 로그인 안했을때 disalbe 상태 추가
 * song id를 props로 받아와서 요청보낼때 댓글 내용이랑 같이 보내줘야함
 * @returns
 */
type PropsType = {
  reloadComment: () => void;
};

const CommentInput = ({ reloadComment }: PropsType): JSX.Element => {
  const [comment, setcomment] = useState<string>("");
  const { songId } = useParams();

  const [isLoading, setIsLoading] = useState<boolean>(false);
  const handleCommentChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setcomment(event.target.value);
  };

  const handleSubmit = async () => {
    console.log("댓글 작성시 songId", songId);
    await postComment(songId, comment);
    reloadComment();
  };

  return (
    <div className={`flex-row-center ${styles.container}`}>
      <textarea
        placeholder="댓글로 의견을 남겨주세요"
        name="comment"
        id="comment"
        value={comment}
        onChange={handleCommentChange}
        maxLength={100}
        className={styles.textArea}
      ></textarea>
      <button
        onClick={handleSubmit}
        className={`flex-col-center ${styles.submitButton}`}
      >
        댓글등록
      </button>
    </div>
  );
};

export default CommentInput;
