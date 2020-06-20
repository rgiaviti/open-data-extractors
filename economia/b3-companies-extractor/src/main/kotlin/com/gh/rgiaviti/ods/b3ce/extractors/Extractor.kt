package com.gh.rgiaviti.ods.b3ce.extractors

import com.gh.rgiaviti.ods.b3ce.configs.Config
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class Extractor {

    protected fun html(url: String): Document {
        val userAgent = Config.getConfig(Config.Key.BROWSER_USER_AGENT)
        val timeout = Config.getConfig(Config.Key.CONNECTION_TIMEOUT).toInt()

        return Jsoup
            .connect(url)
            .userAgent(userAgent)
            .timeout(timeout)
            .followRedirects(true)
            .get()
    }
}