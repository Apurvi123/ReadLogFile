package com.example.readlogfile.util

import android.content.Context
import android.util.Log
import com.example.readlogfile.filedownload.FileDownloadResult
import okhttp3.ResponseBody
import java.io.*

class SaveFile(private val context: Context) {

    fun writeResponseBodyToDisk(
        body: ResponseBody,
        fileDownloadResult: FileDownloadResult
    ): Boolean {
        return try {
            val textFile =
                File(context.getExternalFilesDir(null).toString() + File.separator.toString() + "sample.txt")
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(textFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(
                        "File Download: ",
                        "$fileSizeDownloaded of $fileSize"
                    )
                }
                outputStream.flush()
                fileDownloadResult.fileDownloadSuccess(textFile)
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }
}