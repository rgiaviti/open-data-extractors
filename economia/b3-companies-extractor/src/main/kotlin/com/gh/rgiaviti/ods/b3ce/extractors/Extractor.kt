package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.configs.Config
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class Extractor {

    /**
     * Efetua o request para a URL do parâmetro e retorna o objeto Document que representa
     * a página retornada em HTML pronta para ser parseada.
     *
     * @param url é a url que será feito o request
     * @return documento html para parsing
     */
    protected fun html(url: String): Document {
        val userAgent = Config.getConfig(Config.Key.BROWSER_USER_AGENT)
        val timeout = Config.getConfig(Config.Key.CONNECTION_TIMEOUT).toInt()

        return Jsoup
            .connect(url)
            .userAgent(userAgent)
            .timeout(timeout)
            .ignoreHttpErrors(false)
            .followRedirects(true)
            .get()
    }
}