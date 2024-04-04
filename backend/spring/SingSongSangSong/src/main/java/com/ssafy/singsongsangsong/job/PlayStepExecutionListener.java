package com.ssafy.singsongsangsong.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.ssafy.singsongsangsong.dto.AtmosphereCountDto;
import com.ssafy.singsongsangsong.dto.GenreCountDto;

@Component
public class PlayStepExecutionListener implements StepExecutionListener {
	
	private final MongoTemplate mongoTemplate;
    
    public PlayStepExecutionListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        
        String[] ages = {"10", "20", "30", "40", "50"};
		String[] sexs = {"M", "F"};
		
		for (int i = 0; i < ages.length; i++) {
			for (int j = 0; j < sexs.length; j++) {
				String ageSex = ages[i].concat(sexs[j]);
				
				if (executionContext.get(ageSex.concat("/g")) == null) executionContext.put(ageSex.concat("/g"), new GenreCountDto());
				if (executionContext.get(ageSex.concat("/a")) == null) executionContext.put(ageSex.concat("/a"), new AtmosphereCountDto());
			}
		}
		
		System.out.println("Job 실행 전");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        
        mongoTemplate.save(executionContext.get("10M/g"));
        
        return null;
    }

}
