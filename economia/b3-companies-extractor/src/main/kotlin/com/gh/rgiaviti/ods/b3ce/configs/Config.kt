package com.gh.rgiaviti.ods.b3ce.configs

import java.util.*

object Config {

    private const val PROPERTIES_FILE = "configs.properties"

    enum class Key(private val key: String) {
        START_LETTERS("start-letters"),
        COMPANIES_RESUME_URL("companies-resume-url"),
        COMPANY_DETAIL_URL("company-details-url"),
        BROWSER_USER_AGENT("user-agent"),
        TIME_BETWEEN_LETTERS("time-in-millis-between-letters"),
        TIME_BETWEEN_DETAILS("time-in-millis-between-details"),
        CONNECTION_TIMEOUT("connection-in-millis-timeout"),
        JSON_FILE_OUT("json-file-out"),
        METAINFO_VERSION("metainfo-version"),
        METAINFO_SITE("metainfo-site"),
        METAINFO_FONTES("metainfo-fontes");

        override fun toString(): String {
            return key;
        }
    }

    private val configs: Properties
        get() {
            javaClass.classLoader.getResourceAsStream(PROPERTIES_FILE).use {
                return Properties().apply { load(it) }
            }
        }

    fun getConfig(key: Key): String {
        return this.configs.getProperty(key.toString())
    }
}