package com.ssafy.singsongsangsong.security.oauth2.attributes;

import java.util.Map;

import com.ssafy.singsongsangsong.constants.Role;
import com.ssafy.singsongsangsong.constants.SocialType;
import com.ssafy.singsongsangsong.entity.Artist;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private String nameAttributeKey;
	private OAuth2UserInfo oAuth2UserInfo;

	@Builder
	private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oAuth2UserInfo = oAuth2UserInfo;
	}

	public static OAuthAttributes of(SocialType socialType, String userNameAttributeName,
		Map<String, Object> attributes) {
		if (socialType == SocialType.NAVER) {
			return ofNaver(userNameAttributeName, attributes);
		} else if (socialType == SocialType.KAKAO) {
			return ofKakao(userNameAttributeName, attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);

	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
			.build();
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
			.build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
			.build();
	}

	public Artist toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
		return Artist.builder()
			.username(socialType.toString() + "_" + oAuth2UserInfo.getId())
			.role(Role.GUEST)
			.build();
	}
}
