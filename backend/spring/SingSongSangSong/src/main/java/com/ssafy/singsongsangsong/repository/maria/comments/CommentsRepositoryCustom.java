package com.ssafy.singsongsangsong.repository.maria.comments;

import java.util.List;

import com.ssafy.singsongsangsong.entity.Comments;

public interface CommentsRepositoryCustom {
	List<Comments> findBySongId(Long songId);
}
