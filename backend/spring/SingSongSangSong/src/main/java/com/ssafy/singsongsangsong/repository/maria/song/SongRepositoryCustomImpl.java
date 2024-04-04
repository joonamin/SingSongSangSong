package com.ssafy.singsongsangsong.repository.maria.song;

import static com.ssafy.singsongsangsong.entity.QSong.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SongRepositoryCustomImpl implements SongRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private String getEmotionColumnName(String emotionType) {
		return emotionType.toLowerCase() + "EmotionCount";
	}

	@Override
	public List<Song> getPublishedSongsByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(song).where(song.isPublished.eq(true), song.artist.id.eq(artistId)).fetch();
	}

	@Override
	public List<Song> findSongOrderByWeeklyCountDesc() {
		return jpaQueryFactory.selectFrom(song).orderBy(song.weeklyPlayCount.desc()).limit(10).fetch();
	}

	@Override
	public List<Song> findSongForGenreOrderByWeeklyCountDesc(String requestGenre) {
		return jpaQueryFactory.select(song)
			.from(song)
			.where(song.customGenre.eq(requestGenre))
			.orderBy(song.weeklyPlayCount.desc())
			.limit(10)
			.fetch();
	}

	@Override
	public List<Song> findSongForAtmosphereOrderByWeeklyCountDesc(String requestAtmosphere) {
		return jpaQueryFactory
			.selectFrom(song)
			.where(song.themes.eq(requestAtmosphere))
			.orderBy(song.weeklyPlayCount.desc())
			.limit(10)
			.fetch();
	}

	@Override
	public List<Song> findSongByBpmAndKeyword(String keyword, int startBpm, int endBpm,
		OrderSpecifier[] orderSpecifiers, String requestGenre, String requestAtmosphere) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(song.bpm.between(startBpm, endBpm));
		if (keyword != null) {
			builder.and(song.title.contains(keyword));
		}
		if (requestGenre != null) {
			builder.and(song.customGenre.eq(requestGenre));
		}
		if (requestAtmosphere != null) {
			builder.and(song.themes.eq(requestAtmosphere));
		}
		return jpaQueryFactory.select(song)
			.distinct()
			.from(song)
			.where(builder)
			.orderBy(orderSpecifiers)
			.limit(20)
			.fetch();
	}

	public Optional<Song> getSongByArtistIdAndSongId(Long songId, Long artistId) {
		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(song).where(song.id.eq(songId), song.artist.id.eq(artistId)).fetchOne());
	}

	@Override
	public long decrementEmotionCount(Long songId, String emotionName) throws NoSuchFieldException {
		String columnName = getEmotionColumnName(emotionName);
		Path<Integer> targetEmotionPath = Expressions.numberPath(Integer.class, columnName);

		return jpaQueryFactory.update(song)
			.set(targetEmotionPath, ((NumberExpression<Integer>)targetEmotionPath).subtract(1))
			.where(song.id.eq(songId))
			.execute();
	}

	@Override
	public long incrementEmotionCount(Long songId, String emotionName) {
		String columnName = getEmotionColumnName(emotionName);
		Path<Integer> targetEmotionPath = Expressions.numberPath(Integer.class, columnName);

		return jpaQueryFactory.update(song)
			.set(targetEmotionPath, ((NumberExpression<Integer>)targetEmotionPath).add(1))
			.where(song.id.eq(songId))
			.execute();
	}

	@Override
	public List<Song> findByThemeName(String themeName, int size) {
		return jpaQueryFactory.selectFrom(song)
			.where(song.themes.eq(themeName))
			.limit(size)
			.fetch();
	}

	@Override
	public void incrementPlayCount(Long songId) {
		jpaQueryFactory.update(song)
			.set(song.playCount, song.playCount.add(1))
			.where(song.id.eq(songId))
			.execute();
	}

	@Override
	public void incrementDownloadCount(Long songId) {
		jpaQueryFactory.update(song)
			.set(song.downloadCount, song.downloadCount.add(1))
			.where(song.id.eq(songId))
			.execute();
	}

	@Override
	public List<Song> findAllByArtistIdAndIsPublished(Long artistId) {
		return jpaQueryFactory.selectFrom(song)
			.where(song.artist.id.eq(artistId).and(song.isPublished))
			.fetch();
	}

	@Override
	public List<Song> getSongByArtistId(Long artistId) {
		return jpaQueryFactory.selectFrom(song)
			.where(song.artist.id.eq(artistId))
			.fetch();
	}
}
