package com.gh.rgiaviti.ods.b3ce.configs

import java.util.*

object Config {

    private const val PROPERTIES_FILE = "configs.properties"

    enum class Key(private val key: String) {
        START_LETTERS("start-letters"),
        COMPANIES_RESUME_URL("companies-resume-url"),
        COMPANY_DETAIL_URL("company-details-url"),
        TIME_BETWEEN_LETTERS("time-in-sec-between-letters"),
        TIME_BETWEEN_DETAILS("time-in-sec-between-details");


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