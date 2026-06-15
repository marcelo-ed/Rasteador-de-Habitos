# Rastreador de Hábitos

Aplicação de linha de comando (CLI) em Java para registro e acompanhamento de hábitos diários.

Versão: 1.1.2

---

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Execução](#execução)
- [Execução via Docker](#execução-via-docker)
- [Testes e Qualidade de Código](#testes-e-qualidade-de-código)
- [Observações](#observações)
- [Equipe](#equipe)
- [Licença](#licença)

---

## Sobre o Projeto

O **Rastreador de Hábitos** permite cadastrar hábitos, marcar conclusões diárias e visualizar o progresso ao longo do tempo, ajudando os usuários a construir rotinas mais saudáveis e consistentes.

### Problema

Manter hábitos saudáveis é difícil sem acompanhamento. Muitas pessoas abandonam suas rotinas por falta de registro e visibilidade do próprio progresso. O Rastreador de Hábitos oferece uma forma simples e direta de registrar hábitos diários e acompanhar sequências de conclusão.

### Público-alvo

Qualquer pessoa que queira construir e manter rotinas saudáveis no dia a dia.

---

## Funcionalidades

- Cadastrar hábitos com nome e descrição opcional
- Listar hábitos com status de conclusão e sequência atual
- Marcar hábitos como concluídos no dia
- Deletar hábitos
- Exibir frase motivacional ao iniciar o programa
- Persistência dos dados em banco de dados na nuvem (Supabase)
- Reset automático do status diário ao abrir o programa

> **Aviso:** todos os usuários compartilham a mesma base de dados. O sistema não possui autenticação.

---

## Tecnologias

| Tecnologia | Finalidade |
|---|---|
| Java 17 | Linguagem principal da aplicação |
| Maven | Gerenciamento de dependências e build |
| Jackson | Serialização e desserialização JSON |
| Dotenv Java | Gerenciamento de variáveis de ambiente |
| JUnit 5 | Testes automatizados |
| Checkstyle | Análise estática de código |
| Supabase | Banco de dados PostgreSQL na nuvem |
| Docker | Containerização da aplicação |
| GitHub Actions | Integração contínua (CI) |

---

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

Para verificar se estão instalados:

```bash
java -version
mvn -version
```

---

## Instalação

```bash
git clone https://github.com/marcelo-ed/Rasteador-de-Habitos.git
cd Rasteador-de-Habitos
mvn install
```

---

## Configuração

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
SUPABASE_URL=https://zwpdixoygozaqatpowdd.supabase.co
SUPABASE_KEY=sb_publishable_Akjp_sQWXVkGJTgUfml1KA_4W9cEEoj
```

> **Nota:** em um ambiente de produção, cada usuário teria suas próprias credenciais. As credenciais acima são fornecidas apenas para fins acadêmicos.

---

## Execução

**Linux/macOS**

```bash
mvn exec:java -Dexec.mainClass="com.marcelo.habit_tracker.Main"
```

**Windows (PowerShell)**

```powershell
mvn exec:java "-Dexec.mainClass=com.marcelo.habit_tracker.Main"
```

**Windows (CMD)**

```cmd
mvn exec:java -Dexec.mainClass=com.marcelo.habit_tracker.Main
```

---

## Execução via Docker

A aplicação está disponível no Docker Hub e pode ser executada sem instalar Java ou Maven:

```bash
docker run -it marceloed/rastreador-de-habitos
```

> **Nota:** a versão Docker utiliza credenciais incorporadas na imagem e não requer configuração adicional.

Imagem pública: [marceloed/rastreador-de-habitos](https://hub.docker.com/r/marceloed/rastreador-de-habitos)

---

## Testes e Qualidade de Código

**Executar testes:**

```bash
mvn test
```

**Executar análise estática (Checkstyle):**

```bash
mvn checkstyle:check
```

---

## Observações

- Os dados são salvos no Supabase e persistem entre sessões.
- O status de conclusão dos hábitos é resetado automaticamente a cada vez que o programa é aberto, permitindo um novo ciclo diário.

---

## Equipe

| Nome |
|---|
| Marcelo Eduardo Silva e Santos Lopes |
| Victor Alves Machado |
| João Vitor Ferreira Brito |
| Pedro Pessoa Pessoa |
| Lucas Silva Martins |

---

## Licença

Este projeto ainda não possui uma licença definida. Caso deseje permitir reutilização, contribuição ou distribuição por terceiros, considere adicionar um arquivo `LICENSE` (por exemplo, MIT, Apache 2.0 ou GPL).

---

Repositório: https://github.com/marcelo-ed/Rasteador-de-Habitos