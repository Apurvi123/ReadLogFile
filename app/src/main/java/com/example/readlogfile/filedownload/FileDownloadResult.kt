package com.example.readlogfile.filedownload

import java.io.File

interface FileDownloadResult {
    fun fileDownloadSuccess(textFile: File)
    fun fileDownloadFailure()
}