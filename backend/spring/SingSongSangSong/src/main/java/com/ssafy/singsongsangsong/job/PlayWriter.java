package com.ssafy.singsongsangsong.job;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.ssafy.singsongsangsong.entity.Play;

public class PlayWriter implements ItemWriter<Play> {

	@Override
	public void write(Chunk<? extends Play> chunk) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
