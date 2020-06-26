package com.gh.rgiaviti.ods.b3ce.services

import java.io.InputStreamReader
import java.util.*

object ConfigService {

    private const val PROPERTIES_FILE = "configs.properties"


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private val configs: Properties
        get() {
            javaClass.classLoader.getResourceAsStream(PROPERTIES_FILE).use {
                return Properties().apply { load(InputStreamReader(it, Charsets.UTF_8)) }
            }
        }

    fun getConfig(key: Key): String {
        return configs.getProperty(key.toString())
    }

    enum class Key(private val key: String) {
        EXECUTION_MODE("execution-mode"),
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
        METAINFO_FONTES("metainfo-fontes"),
        MAX_TRIES_REQUEST("max-tries-in-case-timeout");

        override fun toString(): String {
            return key;
        }
    }

    enum class ExecutionMode(private val execution: String) {
        PARALLEL("parallel"),
        SEQUENTIAL("sequential");

        companion object {
            fun fromString(param: String): ExecutionMode {
                values().forEach { em ->
                    if (em.execution.equals(param, true)) {
                        return em
                    }
                }

                throw IllegalArgumentException("execution not recognized")
            }
        }

        override fun toString(): String {
            return execution;
        }
    }
}