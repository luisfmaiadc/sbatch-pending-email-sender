<h1 align="center">sbatch-pending-email-sender</h1>

<p align="center" style="margin-bottom: 20;">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot 4.0.0" />
  <img src="https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Batch 6.0.0" />
  <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/Mailtrap-D3302F?style=for-the-badge&logo=maildotru&logoColor=white" alt="Mailtrap" />
  <img src="https://img.shields.io/badge/apache%20maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
</p>

<p align="center">O <b>sbatch-pending-email-sender</b> é uma aplicação robusta de processamento em lote desenvolvida com <b>Java 25</b> e <b>Spring Batch 6</b>. O sistema automatiza a notificação de responsáveis por chamados pendentes, consolidando múltiplas pendências em um único e-mail formatado profissionalmente.</p>

<h2>📌 Visão Geral</h2>
<p align="justify">
Este projeto resolve o problema de comunicação em fluxos de trabalho de suporte ou service desk. Ele monitora uma base de dados MySQL, identifica chamados com status "pendente", calcula o tempo de atraso e envia um relatório por e-mail para o responsável.
</p>
<p align="justify">
O grande diferencial técnico reside na <b>otimização do envio de e-mails</b>: ao invés de disparar um e-mail por registro, o sistema utiliza um Writer customizado com estado para agrupar todos os chamados de um mesmo responsável, garantindo que ele receba apenas uma notificação consolidada contendo a lista de todas as suas pendências (agregação cross-chunk).
</p>

<h2>🚀 Tecnologias Utilizadas</h2>

* **Java 25**
* **Spring Boot 4.0.0** & **Spring Batch 6.0.0**
* **Spring JDBC**
* **Spring Mail** & **Thymeleaf** (Criação de templates HTML/CSS dinâmicos)
* **MySQL**
* **Mailtrap** (Servidor SMTP para testes e validação de e-mails)

<h2>⚙️ Fluxo de Processamento (ETL)</h2>

O Job é composto por um Step que segue o modelo Reader-Processor-Writer:

1.  **Reader (`JdbcCursorItemReader`):** Realiza uma consulta filtrando apenas os chamados com status pendente no MySQL.
2.  **Processor (`ItemProcessor`):** Calcula a diferença de dias entre a data de criação do chamado e a data atual, enriquecendo o objeto de domínio.
3.  **Writer (`Stateful Email Writer`):** * Diferente de um Writer comum, este componente agrupa os itens processados por e-mail do responsável.
    * Ao final do processamento (ou troca de responsável), utiliza o **Thymeleaf** para renderizar um template HTML e o **Spring Mail** para enviar o e-mail via **Mailtrap**.

<h2>🏗️ Estrutura do Projeto</h2>

```bash
sbatch-pending-email-sender
│-- src/main/java/com/portfolio/luisfmdc/sbatch_pending_email_sender
│   ├── config/               # Configurações de DataSources
│   ├── domain/               # Modelos de dados
│   ├── job/                  # Definição do Job
│   ├── step/                 # Definição do Step
│   ├── reader/               # Configuração da leitura JDBC
│   ├── processor/            # Lógica de cálculo de dias pendentes
│   ├── writer/               # Writer customizado para agregação e envio de e-mail
│   └── service/              # Serviço de integração com Thymeleaf e Spring Mail
│-- src/main/resources
│   ├── templates/            # Templates HTML de e-mail (Thymeleaf)
│   ├── sql/                  # Scripts de inicialização do banco
│   │   ├── create-database-and-table.sql
│   │   └── populate-table.sql
│   └── application.properties # Credenciais SMTP e Banco de Dados
```

<h2>🛠️ Configuração e Execução</h2>

<h3>📌 Pré-requisitos</h3>

- **Java 25** instalado.
- **Apache Maven** instalado.
- **MySQL Server** ativo (porta 3306).
- Conta no **Mailtrap** (ou outro servidor SMTP) para recebimento dos e-mails de teste.

<h3>🗄️ Configuração do Banco de Dados</h3>

Diferente de execuções manuais, este projeto já fornece os scripts necessários para preparar o ambiente. Localize-os em `src/main/resources/sql/` e execute-os na seguinte ordem:

1. **Criação da Estrutura:** Execute o script `create-database-and-table.sql`. Ele criará o banco de dados e a tabela de chamados.
2. **População de Dados:** Execute o script `populate-table.sql`. Ele inserirá registros pendentes e responsáveis para que o Job tenha o que processar.

<h3>📜 Configuração da Aplicação (<code>application.properties</code>)</h3>

Configure suas credenciais de banco e as chaves do **Mailtrap** no arquivo de propriedades:

```properties
spring.application.name=sbatch-pending-email-sender

spring.datasource.jdbcUrl=jdbc:mysql://localhost:3306/sbatch_execution
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.batch.jdbc.initialize-schema=always

app.datasource.jdbcUrl=jdbc:mysql://localhost:3306/sbatch_notification
app.datasource.username=${APP_DATASOURCE_USERNAME}
app.datasource.password=${APP_DATASOURCE_PASSWORD}

spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=${SPRING_MAIL_USERNAME}
spring.mail.password=${SPRING_MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

<h3>🚀 Executando o Job</h3>

1. Clone o repositório:

```bash
git clone [https://github.com/luisfmaiadc/sbatch-pending-email-sender.git](https://github.com/luisfmaiadc/sbatch-pending-email-sender.git)
cd sbatch-pending-email-sender
```

2. Compile o projeto:

```bash
mvn clean install
```

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

O Job iniciará automaticamente, processará os chamados pendentes agrupando-os por responsável e enviará os templates HTML formatados via Thymeleaf.

<h2>📚 Aprendizados</h2>

Este projeto permitiu consolidar conhecimentos avançados em processamento batch e integração de serviços:

<ul> 
  <li><b>Stateful Writing e Agregação:</b> Implementação de um Writer com estado para realizar agregação <i>cross-chunk</i>. Isso garante que o sistema envie apenas um e-mail consolidado por responsável, otimizando o uso da rede e evitando spam de notificações.</li> 
  <li><b>Templates Dinâmicos:</b> Integração do <b>Thymeleaf</b> para gerar corpos de e-mail em HTML/CSS de forma desacoplada do código Java.</li> 
  <li><b>Gestão de Pendências:</b> Lógica de processamento para cálculo de datas e filtros de registros pendentes via JDBC.</li> 
</ul>

<hr/>

<p align="center">Desenvolvido por <b>Luis Felipe Maia da Costa</b></p>