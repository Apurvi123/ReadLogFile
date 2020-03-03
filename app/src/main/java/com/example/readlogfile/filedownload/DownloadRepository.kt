package com.example.readlogfile.filedownload

import com.example.downloadfilesample.ApiInterface
import com.example.readlogfile.util.SaveFile
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DownloadRepository(
    private val saveFile: SaveFile,
    private val apiInterface: ApiInterface
) {
    fun downloadFile(fileDownloadResult: FileDownloadResult) {
        val call: Call<ResponseBody> = apiInterface.downloadFileWithFixedUrl()
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    saveFile.writeResponseBodyToDisk(response.body()!!, fileDownloadResult)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {}
        })
    }


}