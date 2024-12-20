package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import retrofit2.http.GET
import retrofit2.http.Query

interface MouseApi {
    @GET("mouse")
    suspend fun getMouses(
        @Query("apikey") apiKey: String = MouseApiConfig.API_KEY
    ): List<Mouse>
}