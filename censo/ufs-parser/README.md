# UFs Parser

Pequeno aplicativo escrito em Kotlin que faz parse de uma planilha CSV previamente estruturada e convencionada para que possamos, a partir desse CSV, gerar dados em JSON das UFs.

## Exemplo do CSV

Você pode ver como é o CSV em `/extras/exemplo_datasource_uf.csv`. Esse CSV deve ser montado a partir de dados obtidos de orgãos oficiais como o IBGE. Se a estrutura do CSV se modificar, iremos precisar atualizar o aplicativo do Kotlin.

## Utilizando o Parser

### Requisitos

- JDK 11;
- Gradle;

Clone o projeto:

### Clonando e Buildando

```bash
$git clone git@github.com:rgiaviti/open-data-scripts.git
$cd open-data-scripts
$gradle clean build
```