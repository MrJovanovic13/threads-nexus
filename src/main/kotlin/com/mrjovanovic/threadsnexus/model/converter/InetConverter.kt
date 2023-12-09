package com.mrjovanovic.threadsnexus.model.serializer

import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import java.net.InetAddress


@ReadingConverter
class InetConverter : Converter<Document, InetAddress?> {

    override fun convert(source: Document): InetAddress {
        val document = source["holder"] as Document
        val s = document["address"] as Int
        return InetAddress.getByName(s.toString())
    }
}
