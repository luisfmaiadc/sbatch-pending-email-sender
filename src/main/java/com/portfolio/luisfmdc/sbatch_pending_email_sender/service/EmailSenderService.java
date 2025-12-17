package com.portfolio.luisfmdc.sbatch_pending_email_sender.service;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.NotificacaoPendencia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public void enviarEmailPendencia(String responsavel, List<NotificacaoPendencia> notificacaoPendenciaList) {
        aguardarEnvioMailTrap();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("sbatch-pending-email-sender@luisfmaiadc.com");
        mailMessage.setTo(responsavel);
        mailMessage.setSubject("Chamados em Aberto para Resolução");
        mailMessage.setText(montarMensagem(notificacaoPendenciaList));
        log.info("[EmailSenderService] Enviando e-mail para: {}", responsavel);
        javaMailSender.send(mailMessage);
    }

    private String montarMensagem(List<NotificacaoPendencia> pendencias) {

        StringBuilder sb = new StringBuilder();
         sb.append("Olá, ")
                 .append(pendencias.getFirst().nomeResponsavel())
                 .append("!\n\n");
        sb.append("Você possui ")
                .append(pendencias.size())
                .append(" pendência(s) em aberto:\n\n");

        for (NotificacaoPendencia p : pendencias) {
            sb.append("* ")
                    .append(p.assunto())
                    .append(" (")
                    .append(p.diasEmAberto())
                    .append(" dias em aberto)\n")
                    .append("  - Descrição: ")
                    .append(p.descricao())
                    .append("\n\n");
        }

        sb.append("\nPor favor, verifique.\n");
        return sb.toString();
    }

    private void aguardarEnvioMailTrap() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}