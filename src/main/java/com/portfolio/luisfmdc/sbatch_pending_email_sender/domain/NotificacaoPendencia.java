package com.portfolio.luisfmdc.sbatch_pending_email_sender.domain;

public record NotificacaoPendencia (
        String responsavel,
        String assunto,
        String descricao,
        int diasEmAberto
) {}