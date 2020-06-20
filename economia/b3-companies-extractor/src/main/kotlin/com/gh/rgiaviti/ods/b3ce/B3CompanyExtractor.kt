package com.gh.rgiaviti.ods.b3ce

import com.gh.rgiaviti.ods.b3ce.extractors.CompanyDetailExtractor
import com.gh.rgiaviti.ods.b3ce.extractors.dtos.CompanyResume

object B3CompanyExtractor {

    @JvmStatic
    fun main(args: Array<String>) {

        val details = CompanyDetailExtractor.extractDetails(
            mutableListOf(
                CompanyResume(
                    "TAESA",
                    "222",
                    "http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM=20257"
                )
            )
        )

        println(details)

        //println(Random.nextLong(1000,3000))
        //val resumes = CompanyResumeExtractor.extractResumes()
        //resumes.forEach { c -> println(c.name) }
        //println(resumes.size)
    }
}