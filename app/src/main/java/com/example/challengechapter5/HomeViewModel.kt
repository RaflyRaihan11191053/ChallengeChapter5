package com.example.challengechapter5

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    val item: MutableLiveData<List<GetAllItemSell>> = MutableLiveData()
    val id = item

    fun getAllItem() {
        ApiClient.instance.getAllItems()
            .enqueue(object : Callback<List<GetAllItemSell>> {
                override fun onResponse(
                    call: Call<List<GetAllItemSell>>,
                    response: Response<List<GetAllItemSell>>
                ) {
                    val code = response.code()
                    if (code == 200) {

                    }
                    response.body()?.let {
                        data -> item.postValue(data)
                    }
                }
                override fun onFailure(call: Call<List<GetAllItemSell>>, t: Throwable) {
                    ApiClient.instance.getAllItems().cancel()
                }
            })
    }

    val user: MutableLiveData<User> = MutableLiveData()

    fun getDataUser(data: User) {
        user.postValue(data)
    }

}