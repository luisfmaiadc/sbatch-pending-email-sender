package com.portfolio.luisfmdc.sbatch_pending_email_sender.step;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.NotificacaoPendencia;
import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.Pendencia;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepConfig {

    private final ItemReader<Pendencia> enviaEmailPendenteReaderConfig;
    private final ItemProcessor<Pendencia, NotificacaoPendencia> enviaEmailPendenteProcessor;
    private final ItemWriter<NotificacaoPendencia> itemWriter;

    @Bean
    public Step enviarEmailPendenciasStep(JobRepository jobRepository) {
        return new StepBuilder("enviarEmailPendenciasStep", jobRepository)
                .chunk(10)
                .reader(enviaEmailPendenteReaderConfig)
                .processor(enviaEmailPendenteProcessor)
                .writer(itemWriter)
                .build();
    }
}