package com.ssafy.singsongsangsong.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ArtistPrincipal implements Serializable {
	private Long id;
	private String username;
	private String nickname;
}
