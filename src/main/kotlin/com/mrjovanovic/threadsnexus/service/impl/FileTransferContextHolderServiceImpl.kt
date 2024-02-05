package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.service.FileTransferContextHolderService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.schedule

@Service
class FileTransferContextHolderServiceImpl : FileTransferContextHolderService {

    val filesMap: ConcurrentHashMap<String, MultipartFile> = ConcurrentHashMap()

    override fun putFileInContext(recipientId: String, file: MultipartFile) {
        filesMap[recipientId] = file
        deleteFileAfterFiveMinutes(recipientId)
    }

    private fun deleteFileAfterFiveMinutes(recipientId: String) {
        Timer("FileCleanupGuard", false).schedule(5 * 60 * 1000) {
            filesMap.remove(recipientId)
        }
    }

    override fun getFileFromContextForRecipient(recipientId: String): MultipartFile {
        // TODO How will I handle null here?
        val multipartFile = filesMap[recipientId]!!
        filesMap.remove(recipientId)
        return multipartFile
    }
}
