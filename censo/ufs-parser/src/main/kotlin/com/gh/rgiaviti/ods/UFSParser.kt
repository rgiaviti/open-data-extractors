package com.gh.rgiaviti.ods

import com.gh.rgiaviti.ods.domains.OpenDataUF
import com.gh.rgiaviti.ods.services.CSVService
import com.gh.rgiaviti.ods.services.FileOutService
import com.gh.rgiaviti.ods.services.JSONService
import com.gh.rgiaviti.ods.services.MetaInfoService

object UFSParser {

    @JvmStatic
    fun main(args: Array<String>) {
        val ufs = CSVService.parse()
        val metaInfo = MetaInfoService.metaInfo(ufs.size)
        val json = JSONService.toJSON(OpenDataUF(metaInfo, ufs))
        FileOutService.save(json)
    }
}