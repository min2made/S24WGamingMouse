package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import retrofit2.http.GET
import retrofit2.http.Query

interface MouseApi {
    // 마우스 테이블
    @GET("mouse")
    suspend fun getMouses(
        @Query("apikey") apiKey: String = MouseApiConfig.API_KEY
    ): List<Mouse>

    // 사이트 테이블
    @GET("site")
    suspend fun getSites(
        @Query("apikey") apiKey: String = MouseApiConfig.API_KEY
    ): List<Site>
}