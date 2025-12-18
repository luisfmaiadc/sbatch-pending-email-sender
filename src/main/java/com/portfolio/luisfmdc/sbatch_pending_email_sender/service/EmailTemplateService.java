package com.portfolio.luisfmdc.sbatch_pending_email_sender.service;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.EmailPendencias;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final TemplateEngine templateEngine;

    public String gerarHtml(EmailPendencias email) {
        Context context = new Context();
        context.setVariable("nome", email.nomeResponsavel());
        context.setVariable("total", email.pendencias().size());
        context.setVariable("pendencias", email.pendencias());

        return templateEngine.process("email-pendencias", context);
    }
}