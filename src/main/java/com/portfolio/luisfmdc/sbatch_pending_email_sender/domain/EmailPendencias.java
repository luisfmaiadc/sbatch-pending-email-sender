package com.portfolio.luisfmdc.sbatch_pending_email_sender.domain;

import java.util.List;

public record EmailPendencias(
        String nomeResponsavel,
        String emailResponsavel,
        List<NotificacaoPendencia> pendencias
) {}