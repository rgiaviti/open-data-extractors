package com.gh.rgiaviti.ods.b3ce

import com.gh.rgiaviti.ods.b3ce.configs.Config

object B3CompanyExtractor {

    @JvmStatic
    fun main(args: Array<String>){
        println(Config.getConfig(Config.Key.COMPANY_DETAIL_URL))
    }
}