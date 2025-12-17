package com.portfolio.luisfmdc.sbatch_pending_email_sender.writer;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.NotificacaoPendencia;
import com.portfolio.luisfmdc.sbatch_pending_email_sender.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class EnviaEmailPendenteWriter implements ItemWriter<NotificacaoPendencia>, StepExecutionListener {

    private final EmailSenderService emailSenderService;

    private String responsavelAtual;
    private final List<NotificacaoPendencia> pendenciasDoResponsavel = new ArrayList<>();

    @Override
    public void write(Chunk<? extends NotificacaoPendencia> chunk) throws Exception {
        chunk.getItems().forEach(item -> {
            if (responsavelAtual == null) {
                obterNovoResponsavel(item);
            }

            if (item.emailResponsavel().equals(responsavelAtual)) {
                pendenciasDoResponsavel.add(item);
            } else {
                enviarEmailResponsavelAtual();
                obterNovoResponsavel(item);
            }
        });
    }

    private void obterNovoResponsavel(NotificacaoPendencia item) {
        responsavelAtual = item.emailResponsavel();
        pendenciasDoResponsavel.clear();
        pendenciasDoResponsavel.add(item);
    }

    private void enviarEmailResponsavelAtual() {
        if (responsavelAtual == null || pendenciasDoResponsavel.isEmpty()) return;
        try {
            emailSenderService.enviarEmailPendencia(responsavelAtual, pendenciasDoResponsavel);
        } catch (MailSendException ex) {
            log.error("[EnviaEmailPendenteWriter] Falha ao enviar e-mail para {}.", responsavelAtual, ex);
        }
    }

    @Override
    public ExitStatus afterStep(@NonNull StepExecution stepExecution) {
        try {
            enviarEmailResponsavelAtual();
        } catch (Exception ex) {
            log.error("Erro ao enviar e-mail no afterStep.", ex);
        }
        return ExitStatus.COMPLETED;
    }
}