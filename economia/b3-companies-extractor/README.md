# Scripts utilitários referentes a B3 / BVMF

Uma coleção de scripts desenvolvidos em Ruby para extração ou tratamento de informações referentes a 33 (http://www.b3.com.br) e BVMF / Bovespa (www.bmfbovespa.com.br). Acredito que os scripts seja úteis para desenvolvedores investidores.

## Scripts disponíveis
* b3_companies_scrapper.rb

## Instalando e Rodando
### Pré requisitos:
* Ruby 2.5.X ou maior;
* Bundler (https://bundler.io) instalado

### Instruções
Faça o clone do repositório, ou baixe os arquivos. Depois instale as gems requeridas utilizando o seguinte comando:

```
bundle install
```

Depois rode o script:
```
ruby <nome_do_script>.rb
```

## Descrição dos scripts
### b3_companies_scrapper.rb
Esse script extrai do site da BVMF todas as empresas listadas na Bolsa de Valores, assim como alguns dados adicionais como CNPJ, Setores, Atividade, dentre outras. Como saída, o script gera um arquivo JSON para posterior integração com outros aplicativos. Eu mantenho um cópia dessa extração, aqui no GitHub para uso comunitário: https://github.com/rgiaviti/open-data/blob/master/bvmf/empresas.json

Além das empresas, o script também extrai separadamente, filtra os repetidos e ordena alfabeticamente os setores das empresas. Há também, aqui no GitHub, os setores disponíveis para uso comunitário: https://github.com/rgiaviti/open-data/blob/master/bvmf/empresas_setores.json

_Não há garantias sobre a veracidade, disponibilidade ou da estrutura do JSON das empresas listadas._

#### Parametros
```
-empresa_out_file
```
```
-setores_out_file
```
