package com.gh.rgiaviti.opendata.extractors.infra

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Configuration
class RestClientConfig {

    companion object {
        const val CONN_TIMEOUT = 5L
        const val READ_TIMEOUT = 10L
    }

    @Bean
    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .followRedirects(followRedirects = true)
                .retryOnConnectionFailure(retryOnConnectionFailure = true)
                .followSslRedirects(followProtocolRedirects = true)
                .connectTimeout(timeout = CONN_TIMEOUT, unit = TimeUnit.SECONDS)
                .readTimeout(timeout = READ_TIMEOUT, unit = TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory = this.sslSocketFactory(), trustManager = this.trustAllCertificates()[0] as X509TrustManager)
                .hostnameVerifier(hostnameVerifier = { _, _ -> true })
                .build()
    }

    private fun trustAllCertificates(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                // Do not use
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                // Do not use
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
    }

    private fun sslSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, this.trustAllCertificates(), java.security.SecureRandom())
        return sslContext.socketFactory
    }
}