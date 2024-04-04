package com.ssafy.singsongsangsong.repository.maria.comments;

import static com.ssafy.singsongsangsong.entity.QComments.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Comments;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentsRepositoryCustomImpl
	implements CommentsRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Comments> findBySongId(Long songId) {
		return jpaQueryFactory.selectFrom(comments)
			.where(comments.song.id.eq(songId))
			.fetch();
	}
}
