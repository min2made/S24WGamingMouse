package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MouseViewModel : ViewModel() {
    private val mouseApi: MouseApi

    // 마우스 리스트
    private val _mouseList = MutableLiveData<List<Mouse>>()
    val mouseList: LiveData<List<Mouse>>
    get() = _mouseList

    // 사이트 리스트
    private val _siteList = MutableLiveData<List<Site>>()
    val siteList: LiveData<List<Site>>
        get() = _siteList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MouseApiConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mouseApi = retrofit.create(MouseApi::class.java)
        fetchMouseData()
        fetchSiteData()
    }

    private fun fetchMouseData() {
        // Coroutine 사용
        viewModelScope.launch {
            try {
                val response = mouseApi.getMouses()
                // id 기준으로 정렬
                val sortedResponse = response.sortedBy { it.id }
                _mouseList.value = sortedResponse
            } catch(e: Exception) {
                Log.e("fetchMouseData()", e.toString())
            }
        }
    }

    private fun fetchSiteData() {
        // Coroutine 사용
        viewModelScope.launch {
            try {
                val response = mouseApi.getSites()
                // id 기준으로 정렬
                val sortedResponse = response.sortedBy { it.id }
                _siteList.value = sortedResponse
            } catch(e: Exception) {
                Log.e("fetchSiteData()", e.toString())
            }
        }
    }
}