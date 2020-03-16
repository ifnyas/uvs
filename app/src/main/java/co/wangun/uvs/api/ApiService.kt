package co.wangun.uvs.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("api/result")
    fun postResult(
        @Header("authorization") auth: String,
        @Part("username") username: String,
        @Part("lat") lat: String,
        @Part("lng") lng: String,
        @Part("status") status: Int,
        @Part img: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("api/send-message?")
    fun sendMessage(
        @Query("token") token: String,
        @Query("phone") phone: String,
        @Query("message") message: String
    ): Call<ResponseBody>
}