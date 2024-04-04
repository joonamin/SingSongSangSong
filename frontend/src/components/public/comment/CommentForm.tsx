import React, { useState } from "react";
import { FaComment } from "react-icons/fa";

import CommentInput from "./CommentInput";
import Comments from "./Comments";
import styles from "./CommentForm.module.css";
import { useAxios } from "../../../hooks/api/useAxios";
import { useParams } from "react-router-dom";

const DUMMY = [
  {
    artist: "name2",
    comment: "gdgdgd",
  },
  {
    artist: "namasdasdasde2",
    comment: "gdgdgddasdasdasdd",
  },
];

/**
 * song id를 props로 받아서 해당 음악에 대한 댓글 목록을 가져와야함
 */
const CommentForm = () => {
  const [trigger, setTrigger] = useState<number>(1);
  const { songId } = useParams();
  // console.log(`songID : `, songId);
  const {
    response: comments,
    isLoading: commentLoading,
    refetch: reloadComment,
  } = useAxios({
    method: "GET",
    url: `/song/comments/${songId}`,
  });
  // console.log("댓글댓글댓글글", comments);

  if (commentLoading) {
    return <p>댓글을 로딩중입니다</p>;
  }
  return (
    <div className={styles.container}>
      <header>
        <h1>
          댓글&nbsp;
          <FaComment size={24} />
        </h1>
      </header>
      <CommentInput reloadComment={reloadComment} />
      {comments.comments.map((element: any) => {
        return (
          <Comments
            authorId={element.authorId}
            artistNickname={element.artistNickname}
            content={element.content}
            imgFileName={element.imageFileName}
          />
        );
      })}
    </div>
  );
};

export default CommentForm;
