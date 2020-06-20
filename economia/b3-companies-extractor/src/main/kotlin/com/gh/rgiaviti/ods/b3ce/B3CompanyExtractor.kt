package com.gh.rgiaviti.ods.b3ce

import com.gh.rgiaviti.ods.b3ce.extractors.CompanyDetailExtractor
import com.gh.rgiaviti.ods.b3ce.extractors.CompanyResumeExtractor
import com.gh.rgiaviti.ods.b3ce.services.FileOutService
import com.gh.rgiaviti.ods.b3ce.services.JSONService
import com.gh.rgiaviti.ods.b3ce.services.MetaInfoService

object B3CompanyExtractor {

    @JvmStatic
    fun main(args: Array<String>) {
        val resumes = CompanyResumeExtractor.extractResumes()
        val companies = CompanyDetailExtractor.extractDetails(resumes)
        val metaInfo = MetaInfoService.metaInfo()
        val json = JSONService.toJson(metaInfo, companies)
        FileOutService.save(json)
    }
}