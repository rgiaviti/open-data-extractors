package com.gh.rgiaviti.opendata.extractors.data.economia.bacen

import com.gh.rgiaviti.opendata.extractors.core.AppProperties
import org.springframework.stereotype.Service

@Service
class BacenService(
        val appProperties: AppProperties,
        val bacenProperties: BacenProperties
) {

}