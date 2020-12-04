package com.gh.rgiaviti.opendata.extractors.data.economia.b3.empresas.htmlclient

object EmpresaSelectors {
    //----------------------------------------------
    // SELECTORS
    //----------------------------------------------
    const val SELECT_TABELA_EMPRESAS_POR_LETRA = "#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 > tbody > tr"
    const val SELECT_STOCK_NAME = "#accordionDados > table > tbody > tr:nth-child(1) > td:nth-child(2)"
    const val SELECT_TICKERS = "#accordionDados > table > tbody > tr:nth-child(2) > td:nth-child(2)"
    const val SELECT_CNPJ = "#accordionDados > table > tbody > tr:nth-child(3) > td:nth-child(2)"
    const val SELECT_ATIVIDADE = "#accordionDados > table > tbody > tr:nth-child(4) > td:nth-child(2)"
    const val SELECT_SETORES = "#accordionDados > table > tbody > tr:nth-child(5) > td:nth-child(2)"
    const val SELECT_SITE = "#accordionDados > table > tbody > tr:nth-child(6) > td:nth-child(2) > a"
}