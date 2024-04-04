package com.ssafy.singsongsangsong;

import com.ssafy.singsongsangsong.constants.Role;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.File;

public enum ArtistFixture {

	NO_PROFILE_USER(999L, "validUser", "id999", "password1", null, 27, 'M', "안녕 난 fixture라고 해!");

	private final Long id;
	private final String nickname;
	private final String username;
	private final String password;
	private final File profileImage;
	private final int age;
	private final char sex;
	private final String introduction;

	ArtistFixture(Long id, String nickname, String username, String password, File profileImage, int age, char sex,
		String introduction) {
		this.id = id;
		this.nickname = nickname;
		this.username = username;
		this.password = password;
		this.profileImage = profileImage;
		this.age = age;
		this.sex = sex;
		this.introduction = introduction;
	}

	public Artist getArtist() {
		return new Artist(999L, "validUser", "id999", "password1", null, 27, 'M', "안녕 난 fixture라고 해!", Role.USER);
	}

	public File getProfileImage() {
		return new File(999L, "default.jpg", "default.jpg", 999L);
	}

}
