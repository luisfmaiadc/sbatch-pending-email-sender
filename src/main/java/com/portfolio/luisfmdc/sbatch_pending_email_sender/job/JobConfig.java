package com.portfolio.luisfmdc.sbatch_pending_email_sender.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final Step enviarEmailPendenciasStep;

    @Bean
    public Job enviarEmailPendenciasJob(JobRepository jobRepository) {
        return new JobBuilder("enviarEmailPendenciasJob", jobRepository)
                .start(enviarEmailPendenciasStep)
                .build();
    }
}