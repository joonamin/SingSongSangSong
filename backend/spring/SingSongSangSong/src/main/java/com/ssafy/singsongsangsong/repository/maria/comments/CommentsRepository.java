package com.ssafy.singsongsangsong.repository.maria.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsRepositoryCustom {
}
