package com.portfolio.luisfmdc.sbatch_pending_email_sender.reader;

import com.portfolio.luisfmdc.sbatch_pending_email_sender.domain.Pendencia;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EnviaEmailPendenteReaderConfig {

    private static final String SQL_QUERY = """
        SELECT *
        FROM TbPendencia tp
        WHERE tp.status = 'PENDENTE'
        ORDER BY tp.responsavel, tp.id
        """;
    @Bean
    public JdbcCursorItemReader<Pendencia> itemReader(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Pendencia>()
                .name("enviaEmailPendenteReaderConfig")
                .dataSource(dataSource)
                .sql(SQL_QUERY)
                .beanRowMapper(Pendencia.class)
                .build();
    }
}