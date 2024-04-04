package com.ssafy.singsongsangsong.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.ssafy.singsongsangsong.entity.Play;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlayJobConfig {
	
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MongoTemplate mongoTemplate;
	
	@Bean
    public PlayStepExecutionListener playStepExecutionListener() {
        return new PlayStepExecutionListener(mongoTemplate);
    }
	
	@Bean
	public Job playJob() {
		return new JobBuilder("playJob", jobRepository)
				.start(jobStep())
				.preventRestart()
				.incrementer(new PlayJobParametersIncrementer())
				.build();
	}
	
	@Bean
	public Step jobStep() {
		Step step = new StepBuilder("jobStep", jobRepository)
				.<Play, Play>chunk(10, transactionManager)
				.reader(csvFileReader())
				.processor(new PlayProcessor())
				.writer(new PlayWriter())
				.listener(playStepExecutionListener())
				.build();
		
		return step;
	}
	
	protected static class PlayFieldSetMapper implements FieldSetMapper<Play> {
		
		@Override
	    public Play mapFieldSet(FieldSet fieldSet) {
			String age = String.valueOf((fieldSet.readInt("age") / 10) * 10);
			
	        Play play = Play.builder()
	        		.songId(fieldSet.readLong("songId"))
	        		.genre(fieldSet.readString("genre"))
	        		.atmosphere(fieldSet.readString("atmosphere"))
	        		.age(age)
	        		.sex(fieldSet.readString("sex"))
	        		.build();

	        return play;
	    }
	}
	
	@Bean
	public FlatFileItemReader<Play> csvFileReader() {
		FlatFileItemReader<Play> itemReader = new FlatFileItemReader<>();
//		itemReader.setEncoding("UTF-8");
		itemReader.setResource(new FileSystemResource("src/main/resources/log_dummy.csv"));
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"songId", "genre", "atmosphere", "age", "sex"});
		
		DefaultLineMapper<Play> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new PlayFieldSetMapper());
		
		itemReader.setLineMapper(lineMapper);
		itemReader.setLinesToSkip(1);
		
		return itemReader;
	}

}
