package com.portfolio.luisfmdc.sbatch_pending_email_sender.domain;

public record NotificacaoPendencia (
        String nomeResponsavel,
        String emailResponsavel,
        String assunto,
        String descricao,
        int diasEmAberto
) {}