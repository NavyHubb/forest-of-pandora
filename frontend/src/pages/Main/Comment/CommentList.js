import { useEffect, useState } from "react";
import axios from "axios";

import CommentItem from "./CommentItem";
import style from "./CommentList.module.css";

const CommentList = ({ articleId, item }) => {
  const token = localStorage.getItem("access_token");
  const refreshToken = localStorage.getItem("refresh_token");

  const [commentList, SetCommentList] = useState([]);

  const page = 0; // test - 수정 필요

  useEffect(() => {
    getCommentList();
  }, []);

  // axios : 댓글 목록 조회
  const getCommentList = () => {
    axios
      .get(`/api/articles/${articleId}/comments`, {
        params: {
          page,
        },
        headers: {
          authorization: `Bearer ${token}`,
          refreshtoken: refreshToken,
        },
      })
      .then((response) => {
        console.log("1. get comment : ", response.data.data.content); // test
        SetCommentList(response.data.data.content);
      })
      .catch((err) => {
        console.log("fail to get comment : ", err);
      });
  };

  return (
    <div className={`${style.CommentList}`}>
      {commentList.map((comment) => (
        <CommentItem
          key={comment.commentId}
          {...comment}
          articleId={articleId}
        />
      ))}
    </div>
  );
};

export default CommentList;
