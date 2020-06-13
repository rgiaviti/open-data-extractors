package com.gh.rgiaviti.ods.b3parser

enum class Selector(val selector: String) {
  COMPANY_TABLE_LIST("#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 > tbody > tr"),
  STOCK_NAME("'#accordionDados > table > tr:nth-of-type(1) > td:nth-of-type(2)"),
  CNPJ("#accordionDados > table > tr:nth-of-type(3) > td:nth-of-type(2)"),
  ATIVIDADE("#accordionDados > table > tr:nth-of-type(4) > td:nth-of-type(2)"),
  SETORES("#accordionDados > table > tr:nth-of-type(5) > td:nth-of-type(2)"),
  SITE("#accordionDados > table > tr:nth-of-type(6) > td:nth-of-type(2)")
}