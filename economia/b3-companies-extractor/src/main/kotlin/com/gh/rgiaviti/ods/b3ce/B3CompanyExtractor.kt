package com.gh.rgiaviti.ods.b3ce

import com.gh.rgiaviti.ods.b3ce.extractors.CompanyResumeExtractor

object B3CompanyExtractor {

    @JvmStatic
    fun main(args: Array<String>) {
        val resumes = CompanyResumeExtractor.extractResumes()
        resumes.forEach { c -> println(c.name) }
        println(resumes.size)
    }
}