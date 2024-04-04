import * as minio from "minio";

var s3Client = new minio.Client({
  endPoint: "zip.udon.party",
  port: 40001,
  useSSL: false,
  accessKey: process.env.REACT_APP_S3_ACCESS as string,
  secretKey: process.env.REACT_APP_S3_SECRET as string,
});

export const getPresignedUrl = (bucketName: string, fileName: string) => {
  var presignedUrl = s3Client.presignedGetObject(
    bucketName,
    fileName,
    86400,
    function (e: any, presignedUrl: any) {
      if (e) return console.log(e);
      console.log(presignedUrl);
    }
  );
};
