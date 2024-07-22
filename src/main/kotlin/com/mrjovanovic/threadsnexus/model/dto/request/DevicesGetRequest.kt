package com.mrjovanovic.threadsnexus.model.dto.request

data class DevicesGetRequest(
    val groupId: String?,
    val searchText: String = ""
)
