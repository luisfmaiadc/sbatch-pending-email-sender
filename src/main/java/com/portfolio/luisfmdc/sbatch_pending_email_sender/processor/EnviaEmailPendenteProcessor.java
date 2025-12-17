package com.portfolio.luisfmdc.sbatch_pending_email_sender.processor;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.NotificacaoPendencia;
import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.Pendencia;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@StepScope
@Component
public class EnviaEmailPendenteProcessor implements ItemProcessor<Pendencia, NotificacaoPendencia> {

    private final LocalDateTime dataReferencia;

    public EnviaEmailPendenteProcessor(
            @Value("#{jobExecution.startTime}") Date startTime) {
        this.dataReferencia = startTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public NotificacaoPendencia process(Pendencia item) throws Exception {
        int diasEmAberto = calcularDiasEmAberto(item.getDataCriacao());
        return new NotificacaoPendencia(item.getResponsavel(), item.getAssunto(), item.getDescricao(), diasEmAberto);
    }

    private int calcularDiasEmAberto(LocalDateTime dataCriacao) {
        return (int) ChronoUnit.DAYS.between(dataCriacao, dataReferencia);
    }
}