package com.ssafy.singsongsangsong.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Follower_Following {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fromArtistId")
	private Artist from;

	@ManyToOne
	@JoinColumn(name = "toArtistId")
	private Artist to;

	public static Follower_Following of(Artist from, Artist to) {
		Follower_Following ff = new Follower_Following();
		ff.from = from;
		ff.to = to;
		return ff;
	}
}
