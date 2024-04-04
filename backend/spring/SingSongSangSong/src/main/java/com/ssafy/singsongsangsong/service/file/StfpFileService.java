package com.ssafy.singsongsangsong.service.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ssafy.singsongsangsong.config.SynologyFileStationConfig;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.UploadSongDto;
import com.ssafy.singsongsangsong.repository.maria.file.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * SFTP 서버 파일 업로드를 위한 서비스 (NAS가 SFTP 포트만 허용..)
 * 리팩토링 필요
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StfpFileService implements FileService {

	private final SynologyFileStationConfig synologyFileStationConfig;
	private final FileRepository fileRepository;

	private ChannelSftp createChannel() {
		String host = synologyFileStationConfig.getHost();
		String account = synologyFileStationConfig.getAccount();
		String passwd = synologyFileStationConfig.getPasswd();

		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession(account, host, 24);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(passwd);
			session.connect();

			// sftp 채널을 open
			Channel channel = session.openChannel("sftp");
			channel.connect();

			return (ChannelSftp)channel;
		} catch (JSchException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	@Override
	public String saveFile(Long artistId, FileType fileType, MultipartFile fileData) throws IOException {

		ChannelSftp channel = createChannel();
		UUID fileIdentifier = UUID.randomUUID();
		String savedFileName = fileIdentifier.toString() + "." + fileData.getContentType().split("/")[1];

		try {
			channel.cd(synologyFileStationConfig.getBaseDir() + "/" + fileType.getName());
			channel.put(fileData.getInputStream(), savedFileName);
			fileRepository.save(
				com.ssafy.singsongsangsong.entity.File.of(fileIdentifier.toString(), fileData.getOriginalFilename(),
					artistId));
			return savedFileName;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			channel.exit();
		}
	}

	/**
	 * 파일을 가져올 때, file not found 가 나올 수 있는데, 이는 컴파일타임에 classpath에 파일이 없어서 그렇습니다.
	 * NAS와 볼륨 마운트가 이루어지기 전까지 임시로 사용하는 Service이므로, 다시 서버를 껐다가 켜주시면 파일을 인식합니다.
	 */
	@Override
	public Resource getFile(Long artistId, FileType fileType, String originalFileName) throws IOException {
		ChannelSftp channel = createChannel();
		final String ABSOLUTE_DST = "/src/main/resources" + synologyFileStationConfig.getBaseDir() + File.separator
			+ fileType.getName() + File.separator + originalFileName;
		final String RELATIVE_DST =
			synologyFileStationConfig.getBaseDir() + "/" + fileType.getName() + "/" + originalFileName;

		log.info("src: {}", originalFileName);
		log.info("dest: {}", ABSOLUTE_DST);

		try {
			channel.cd(synologyFileStationConfig.getBaseDir() + "/" + fileType.getName());

			channel.get(originalFileName, ABSOLUTE_DST);
			return new ClassPathResource(RELATIVE_DST);
		} catch (SftpException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Resource getFileViaId(Long artistId, FileType fileType, Long fileId) throws IOException {
		throw new Error("아직 구현되지 않은 메소드입니다.");
	}

	@Override
	public UploadSongDto uploadSong(Long artistId, FileType fileType, MultipartFile fileData) throws IOException {
		throw new Error("아직 구현되지 않은 메소드입니다.");
	}
}
