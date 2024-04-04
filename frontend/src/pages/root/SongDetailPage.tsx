import React from "react";
import { useParams } from "react-router";

// import testArtist from "./../../sources/testimg/artistProfile.jpg";
// import Album from "../../components/public/Album";
import SongDetails from "../../components/pageComponents/songDetailpageComponent/SongDetails";
import SongInfo from "../../components/public/analysis/SongInfo";
// import EmotionBox from "../../components/public/emotionBox/EmotionBox";
import styles from "./SongDetailPage.module.css";
import CommentForm from "../../components/public/comment/CommentForm";
import SongHeader from "../../components/pageComponents/songDetailpageComponent/SongHeader";
import { useAxios } from "../../hooks/api/useAxios";
import MusicEmotionBox from "../../components/public/emotionBox/MusicEmotionBox";

// const DUMMY_DATA = {
//   songTitle: "test1",
//   artist: {
//     artistId: "1",
//     nickname: "testName1",
//     userName: "testName1",
//     profileImageFileName: "test1",
//     introduction: "소개입니당",
//   },
//   songFileName: "filename1",
//   albumImageFileName: "albumImageFilename",
//   songDescription: "곡 소개 입니당.",
//   movedEmotionCount: 13,
//   likeEmotionCount: 15,
//   excitedEmotionCount: 16,
//   energizedEmotionCount: 72,
//   funnyEmotionCount: 65,
//   sadEmotionCount: 34,

//   lyrics: "가사입니다",
//   chord: "C#",
//   bpm: 160,

//   likeCount: 53,
//   downloadCount: 53,
//   playCount: 3213,

//   comments: {
//     artistId: 23,
//     nickname: "commentNick",
//     content: "댓글내용인데용",
//     profileImageName: "응애",
//     createdAt: "2024-04-01",
//   },
// };

const SongDetailPage = () => {
  const { songId } = useParams();
  console.log(songId);

  const { response, isLoading, refetch } = useAxios({
    method: "GET",
    url: `/song/detail/${songId}`,
  });

  // console.log(response);

  if (isLoading) {
    return <p>로딩중입니다</p>;
  }
  if (!response) {
    return <p>해당곡에 대한 데이터가 없습니다</p>;
  }
  return (
    <div className={`w-100 flex-col-center gap-30 ${styles.container}`}>
      <SongHeader
        songtitle={response.songTitle}
        artist={response.artist}
        likeCount={response.likeCount}
        playCount={response.playCount}
        downloadCount={response.downloadCount}
        songFileName={response.songFileName}
        albumImageFileName={response.albumImageFileName}
      />
      <div className={`w-100 flex-col-center gap-30 ${styles.content}`}>
        <SongInfo
          songDescription={response.songDescription}
          bpm={response.bpm}
          chord={response.chord}
        />
        <SongDetails lyrics={response.lyrics} />
        <div className={`flex-col-center  ${styles.emotionBox}`}>
          <p>사람들은 이 곡에서</p>
          <p>이러한 느낌을 받았어요</p>
          <MusicEmotionBox
            songId={songId}
            energizedEmotionCount={response.energizedEmotionCount}
            excitedEmotionCount={response.excitedEmotionCount}
            funnyEmotionCount={response.funnyEmotionCount}
            movedEmotionCount={response.movedEmotionCount}
            likeEmotionCount={response.likeEmotionCount}
            sadEmotionCount={response.sadEmotionCount}
            refetch={refetch}
          />
        </div>
        <CommentForm />
      </div>
    </div>
  );
};

export default SongDetailPage;
