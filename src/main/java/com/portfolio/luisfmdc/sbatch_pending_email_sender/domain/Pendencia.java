package com.portfolio.luisfmdc.sbatch_pending_email_sender.domain;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pendencia {

    private int id;
    private String assunto;
    private String descricao;
    private String status;
    private String responsavel;
    private LocalDateTime dataCriacao;

    public String getNome() {
        return responsavel.split("@")[0].toUpperCase();
    }
}