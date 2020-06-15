#!/usr/bin/env ruby

# Lista com as gems/dependencias usadas no script
require 'rubygems'
require 'yaml'
require 'nokogiri'
require 'rest-client'
require 'json'
require 'logger'

#===========================================
# CONFIGURACOES DO SCRIPT
#===========================================
empresas_json_path = "#{ENV['HOME']}/Desktop/empresas_bvmf.json"
setores_json_path = "#{ENV['HOME']}/Desktop/setores_bvmf.json"

# Carrega as configurações externas do script
@configs = YAML.load_file('b3_configs.yml')

# Configuracoes de Logging
@log = Logger.new(STDOUT)
@log.level = Logger::INFO
#===========================================
# FIM DAS CONFIGURACOES DO SCRIPT
#===========================================

#===========================================
# INICIO DO METODOS E CLASSES DO SCRIPT
#===========================================

def check_configuration; end

# Seletores HTMLs para obtencao dos dados das companhias listadas no site da B3. São seletores
# que passamos ao Nokogiri para extrair da página HTML os dados que precisamos das empresas.
# Todos os seletores estao como constantes nessa classe. Para chamar um seletor, basta usar
# Selector::NOME_DO_SELETOR
class Selector
  COMPANY_TABLE_LIST = '#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 > tbody > tr'.freeze
  STOCK_NAME = '#accordionDados > table > tr:nth-of-type(1) > td:nth-of-type(2)'.freeze
  CNPJ = '#accordionDados > table > tr:nth-of-type(3) > td:nth-of-type(2)'.freeze
  ATIVIDADE = '#accordionDados > table > tr:nth-of-type(4) > td:nth-of-type(2)'.freeze
  SETORES = '#accordionDados > table > tr:nth-of-type(5) > td:nth-of-type(2)'.freeze
  SITE = '#accordionDados > table > tr:nth-of-type(6) > td:nth-of-type(2)'.freeze
end

# Executa requests do tipo get para obtencao dos HTML a serem parseados
# pelo script
def parse_html(url)
  Nokogiri::HTML(RestClient.get(url))
end

# Monta a URL com a lista de empresas da B3 onde o nome da empresa começa com
# determinada letra. A letra em questao também fica localizada no arquivo
# externo de configuracao.
def build_letters_url
  start_letters = @configs['companies_scrapper']['start_letters'].split(';')
  letters_url = []

  start_letters.each do |letter|
    letter_url = @configs['companies_scrapper']['list_url']
    letters_url << "#{letter_url}#{letter}"
  end

  letters_url
end

# O metodo recebe a URL que lista as companhias da B3 que comecam com a letra
# em questao. Esse metodo faz o parse do html da lista de companhias e retorna
# os dados relevantes para o proximo passo que extrair as informacoes
# detalhadas da companhia
def companies_list(start_letter_url)
  @log.info("Scrapping empresas da pagina => #{start_letter_url}")
  company_data = []

  companies_page = parse_html(start_letter_url)
  companies_list = companies_page.css(Selector::COMPANY_TABLE_LIST)

  companies_list.each do |company_line|
    company_data << extract_company_resume(company_line)
  end

  company_data
end

# Esse metodo recebe um objeto que representa os dados resumidos da empresa
# listada na B3 e extrai o nome e o codigo CVM dessa empresa. Essas informacoes
# entram em um hash e é retornado
def extract_company_resume(company_line)
  {
    name: company_line.css('td')[0].text,
    cvm: company_line.css('td').at('a').attr('href').split('=')[1].strip
  }
end

# Cria um hash com os dados detalhados da empresa extraida do site da B3.
# Esse hash sera retornado para posterior uso.
def company_detail(company_resume)
  @log.info(" -- #{company_resume[:name]}")

  detail_url = "#{@configs['companies_scrapper']['detail_url']}#{company_resume[:cvm]}"
  company_page = parse_html(detail_url)

  {
    name: company_resume[:name],
    cvm: company_resume[:cvm],
    stock_name: extract_field(company_page, Selector::STOCK_NAME).strip,
    cnpj: extract_field(company_page, Selector::CNPJ).strip,
    atividade: extract_field(company_page, Selector::ATIVIDADE).strip,
    setores: split_setores(extract_field(company_page, Selector::SETORES).strip),
    site: extract_field(company_page, Selector::SITE).strip
  }
end

# Extrai de um elemento HTML o inner text desse elemento e remove
# os espaços em branco tanto do inicio como no fim do texto
def extract_field(page, selector)
  page.css(selector).text.strip
end

# Faz o split dos setores extraidos e transforma a String que contem os setores
# da empresa em um array de setores
def split_setores(str_setores)
  setores = []

  str_setores.split('/').each do |setor|
    setores << setor.strip
  end

  setores.uniq.sort
end

def normalize_companies(companies)
  companies.sort_by { |company| company[:name] }
end

# Depois que todas as empresa forem extraidas do site da B3, e um
# array com os hashs dessas empresas estiver correto, esse metodo
# extrai os setores e ordena em ordem alfabetica.
def normalize_setores(companies)
  setores = []
  companies.each do |company|
    setores.concat(company[:setores])
  end

  setores.sort.uniq
end

# Salva o objeto passado em formato JSON no caminho passado tambem
# por parametro
def save_to_json(obj, file_path)
  File.write(file_path, obj.to_json)
end

# Contabiliza o momento que o processamento do script inicia p/ poder
# logar o tempo utilizado
def start_processing
  Process.clock_gettime(Process::CLOCK_MONOTONIC)
end

# Finaliza o processo do script e exibe o tempo gasto rodando o
# script
def finish_processing(started_time)
  ending = Process.clock_gettime(Process::CLOCK_MONOTONIC)
  elapsed_time = Time.at(ending - started_time).utc.strftime('%H:%M:%S')
  @log.info("Time elapsed: #{elapsed_time}")
end

#===========================================
# FIM DO METODOS E CLASSES DO SCRIPT
#===========================================

#===========================================
# INICIO DO SCRIPT
#===========================================
started_processing = start_processing
companies = []
tasks = []

# Get all the the url for each start company letter
letters_url = build_letters_url
letters_url.each do |letter_url|
  tasks << Thread.new do
    companies_list(letter_url).each do |company_resume|
      companies << company_detail(company_resume)
    end
  end
end

# Inicia as threads. Uma por letra
tasks.each(&:join)

# Extrai os setores para a geracao de um JSON somente com os setores
# das companhias
companies = normalize_companies(companies)
setores = normalize_setores(companies)

# Salva os jsons para arquivo
save_to_json(companies, empresas_json_path)
save_to_json(setores, setores_json_path)

# Finaliza o processamento e loga o tempo gastos
finish_processing(started_processing)

#===========================================
# FIM DO SCRIPT
#===========================================
