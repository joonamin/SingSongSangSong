package com.ssafy.singsongsangsong.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Song extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "artistId")
	private Artist artist;
	@OneToOne
	@JoinColumn(name = "mfccImageId")
	private File mfccImage;
	@OneToOne
	@JoinColumn(name = "spectrumImageId")
	private File spectrumImage;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "albumImageId")
	private File albumImage;

	private String title;
	private String songDescription;
	private String lyrics;

	private String customGenre; // 사용자가 직접 지정할 수 있고, 지정하지 않는 경우 분석 결과를 기반으로 자동으로 결정됩니다
	private String chord;
	private String themes; // 분석 결과에서 제일 연관성(correlation)이 높은 분위기(atmosphere)의 상위 분류(테마)가 곡의 테마로 결정됩니다

	private int bpm;
	private int duration;

	private int playCount;
	private int downloadCount;
	private int likeCount;

	private int weeklyPlayCount;
	private int weeklyDownloadCount;
	private int weeklyLikeCount;

	@ColumnDefault("false")
	private boolean isPublished;

	private String musicFileName;

	@ColumnDefault("0")
	private int movedEmotionCount;
	@ColumnDefault("0")
	private int likeEmotionCount;
	@ColumnDefault("0")
	private int excitedEmotionCount;

	@ColumnDefault("0")
	private int energizedEmotionCount;
	@ColumnDefault("0")
	private int funnyEmotionCount;
	@ColumnDefault("0")
	private int sadEmotionCount;

	@ColumnDefault("false")
	private boolean isAnalyzed;
}
