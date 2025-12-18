package com.portfolio.luisfmdc.sbatch_pending_email_sender.service;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.EmailPendencias;
import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.NotificacaoPendencia;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailTemplateService emailTemplateService;

    public void enviarEmailPendencia(String responsavel, List<NotificacaoPendencia> pendencias) {

        aguardarEnvioMailTrap();

        try {
            EmailPendencias email = new EmailPendencias(pendencias.getFirst().nomeResponsavel(), responsavel, pendencias);
            String html = emailTemplateService.gerarHtml(email);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("sbatch-pending-email-sender@luisfmaiadc.com");
            helper.setTo(responsavel);
            helper.setSubject("Chamados em Aberto para Resolução");
            helper.setText(html, true);

            log.info("[EmailSenderService] Enviando e-mail HTML para: {}", responsavel);
            javaMailSender.send(message);
        } catch (MessagingException ex) {
            log.error("[EmailSenderService] Erro ao enviar e-mail HTML", ex);
        }
    }

    private void aguardarEnvioMailTrap() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}