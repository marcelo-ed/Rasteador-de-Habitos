# Rastreador de Hábitos

Aplicação de linha de comando (CLI) desenvolvida em Java para registro e acompanhamento de hábitos diários. Permite cadastrar hábitos, marcar conclusões e visualizar o progresso, ajudando usuários a construir rotinas mais saudáveis e consistentes.

## Problema

Manter hábitos saudáveis é difícil sem acompanhamento. Muitas pessoas abandonam suas rotinas por falta de registro e visibilidade do próprio progresso. O Rastreador de Hábitos oferece uma forma simples e direta de registrar hábitos diários e acompanhar sequências de conclusão.

## Público-alvo

Qualquer pessoa que queira construir e manter rotinas saudáveis no dia a dia.

## Funcionalidades

- Cadastrar hábitos com nome e descrição opcional
- Listar hábitos com status de conclusão e sequência atual
- Marcar hábitos como concluídos no dia
- Deletar hábitos
- Persistência dos dados em arquivo JSON local
- Reset automático do status diário ao abrir o programa

## Tecnologias

- Java 17
- Maven
- Jackson (persistência em JSON)
- JUnit 5 (testes automatizados)
- Checkstyle (análise estática)

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

Para verificar se estão instalados:

```bash
java -version
mvn -version
```

## Instalação

```bash
git clone https://github.com/marcelo-ed/Rasteador-de-Habitos.git
cd Rasteador-de-Habitos
mvn install
```

## Execução

**Linux/macOS:**
```bash
mvn exec:java -Dexec.mainClass="com.marcelo.habit_tracker.Main"
```

**Windows (PowerShell):**
```powershell
mvn exec:java "-Dexec.mainClass=com.marcelo.habit_tracker.Main"
```

**Windows (CMD):**
```cmd
mvn exec:java -Dexec.mainClass=com.marcelo.habit_tracker.Main
```

## Testes

```bash
mvn test
```

## Lint

```bash
mvn checkstyle:check
```

## Observações

- Os dados são salvos automaticamente no arquivo `habits.json`, criado na pasta raiz do projeto na primeira execução.
- O status de conclusão dos hábitos é resetado automaticamente a cada vez que o programa é aberto, permitindo um novo ciclo diário.
- Caso o arquivo `habits.json` seja deletado, todos os hábitos serão perdidos.

## Versão

1.0.0

## Autor

Marcelo Eduardo Silva e Santos Lopes

## Repositório

https://github.com/marcelo-ed/Rasteador-de-Habitos