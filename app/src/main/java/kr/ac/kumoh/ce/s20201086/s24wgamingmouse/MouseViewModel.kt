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
    private val _mouseList = MutableLiveData<List<Mouse>>()
    val mouseList: LiveData<List<Mouse>>
    get() = _mouseList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MouseApiConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mouseApi = retrofit.create(MouseApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        // Coroutine 사용
        viewModelScope.launch {
            try {
                val response = mouseApi.getMouses()
                _mouseList.value = response
            } catch(e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}