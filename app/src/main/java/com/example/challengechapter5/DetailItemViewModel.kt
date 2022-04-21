package com.example.challengechapter5

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailItemViewModel: ViewModel() {
    val detail: MutableLiveData<GetAllItemSell> = MutableLiveData()

    fun getDetail(id: Int) {
        ApiClient.instance.getDetailItems(id)
            .enqueue(object : Callback<GetAllItemSell> {
                override fun onResponse(
                    call: Call<GetAllItemSell>,
                    response: Response<GetAllItemSell>
                ) {
                    response.body()?.let { data ->
                        detail.postValue(data)
                    }
                }
                override fun onFailure(call: Call<GetAllItemSell>, t: Throwable) {
                    ApiClient.instance.getDetailItems(id).cancel()
                }
            })
    }
}