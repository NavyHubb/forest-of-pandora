import { useEffect, useState } from "react";
import styles from "./BoardTemp.module.css";
import { getBoardDetail } from "../../../components/Board/api";
import { useNavigate } from "react-router-dom";

const BoardTempItem = ({ item, deleteTemp }) => {
  const navigate = useNavigate();
  const [img, setImg] = useState("");
  useEffect(() => {
    setImg(
      "https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG"
    );
  });

  const originDate = item.modifiedAt;
  // Date 객체 생성
  const date = new Date(originDate);
  // +9 해야돼서 밀리초 환산
  const nineHours = 9 * 60 * 60 * 1000;
  const adjustedDate = new Date(date.getTime() + nineHours);
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    hour12: true, // 오전/오후 표시를 위해 12시간제 사용
  };
  const formattedDate = new Intl.DateTimeFormat("ko-KR", options).format(
    adjustedDate
  );

  const moveUpdate = async (id) => {
    try {
      navigate("/board/update", { state: { item: item, temp: true } });
    } catch (error) {
      console.error("Error deleting temp:", error);
    }
  };

  console.log(item);
  return (
    <div>
      <div className={styles.article}>
        <div className={styles.close}>
          <span onClick={() => deleteTemp(item.id)}>X</span>
        </div>

        <div
          onClick={() => {
            moveUpdate(item.id);
          }}
          className={styles.tempArticle}
        >
          <div className={styles.itemBox}>
            <p className={styles.content}> {item.content}</p>
            <p className={styles.content}>{formattedDate}</p>
          </div>

          <div className={styles.imgBox}>
            <div className={styles.tempImgFrame}>
              <img className={styles.tempImg} src={img} />
            </div>
            <p className={styles.imgCount}>+3</p>
          </div>
        </div>
      </div>
      <hr />
    </div>
  );
};
export default BoardTempItem;
