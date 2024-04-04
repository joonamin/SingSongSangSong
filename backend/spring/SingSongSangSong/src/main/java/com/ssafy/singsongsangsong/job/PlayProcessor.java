package com.ssafy.singsongsangsong.job;

import java.util.TreeMap;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;

import com.ssafy.singsongsangsong.dto.GenreCountDto;
import com.ssafy.singsongsangsong.entity.Play;

public class PlayProcessor implements ItemProcessor<Play, Play> {
	
	private StepExecution stepExecution;
	
	@BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

	@Override
	public Play process(Play item) throws Exception {
		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		String in = item.getAge().concat(item.getSex());
		TreeMap<String, Long> genreCount = ((GenreCountDto) stepExecutionContext.get(in.concat("/g"))).getGenreCount();
		genreCount.replace(item.getGenre(), genreCount.get(item.getGenre()) + 1);
		
		return item;
	}

}
