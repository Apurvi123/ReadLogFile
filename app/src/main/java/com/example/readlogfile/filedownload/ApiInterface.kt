package com.example.downloadfilesample

import androidx.annotation.Keep
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


@Keep
interface ApiInterface {

    @GET("dealerinspire/ios-code-challenge/raw/master/Apache.log")
    fun downloadFileWithFixedUrl(): Call<ResponseBody>

}