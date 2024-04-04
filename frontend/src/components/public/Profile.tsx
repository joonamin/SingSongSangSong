import React, { useEffect, useState } from "react";
import { getAlbumImg } from "../../utils/api/downloadFileApi";
import { getArtist } from "../../utils/api/artistApi";
import loader from "./../../sources/imgs/spinner.gif";
/**
 * imgUrl을 props로 받음
 * 유저의 이미지를 표시할 컴포넌트 추후 상세 페이지로 이동 기능 추가 필요
 */

//"https://t3.ftcdn.net/jpg/03/53/11/00/360_F_353110097_nbpmfn9iHlxef4EDIhXB1tdTD0lcWhG9.jpg"
type PropsType = {
  artistId?: any;
};

const Profile = ({ artistId }: PropsType) => {
  const [artistImg, setArtistImg] = useState<any>();
  const [imgLoading, setImgLoading] = useState<boolean>(false);
  useEffect(() => {
    const request = async () => {
      setImgLoading(true);
      try {
        const artistInfo = await getArtist(artistId);
        const url = await getAlbumImg(
          artistInfo?.data.data.artistInfoDto.profileImage.originalFileName
        );
        setArtistImg(url);
      } catch (error) {
        console.log(error);
      }
      setImgLoading(false);
    };
    request();
  }, []);

  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        borderRadius: "50px",
        backgroundSize: "cover",
      }}
    >
      {imgLoading && (
        <img
          style={{
            width: "100%",
            height: "100%",
            borderRadius: "50px",
          }}
          src={loader}
          alt=""
        />
      )}
      {!imgLoading && (
        <img
          style={{
            width: "100%",
            height: "100%",
            borderRadius: "50px",
          }}
          src={artistImg}
          alt=""
        />
      )}
    </div>
  );
};

export default Profile;
