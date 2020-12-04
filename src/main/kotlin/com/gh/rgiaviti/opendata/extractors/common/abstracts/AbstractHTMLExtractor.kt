package com.gh.rgiaviti.opendata.extractors.common.abstracts

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL
import java.util.concurrent.TimeUnit

abstract class AbstractHTMLExtractor {

    companion object {
        const val REQUEST_TIMEOUT = 60L // em segundos
        const val DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36"
    }

    protected fun getJsoupDocumet(url: URL, userAgent: String = DEFAULT_USER_AGENT) = this.getJsoupDocumet(url.toString(), userAgent)

    protected fun getJsoupDocumet(url: String, userAgent: String = DEFAULT_USER_AGENT): Document = Jsoup.connect(url)
            .userAgent(userAgent)
            .ignoreHttpErrors(false)
            .followRedirects(true)
            .timeout(TimeUnit.SECONDS.toMillis(REQUEST_TIMEOUT).toInt())
            .get()
}