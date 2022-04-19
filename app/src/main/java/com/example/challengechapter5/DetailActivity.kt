package com.example.challengechapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengechapter5.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)

        getDetail(id)
    }

    private fun getDetail(id: Int) {
        ApiClient.instance.getDetailItems(id)
            .enqueue(object : Callback<GetAllItemSell> {
                override fun onResponse(
                    call: Call<GetAllItemSell>,
                    response: Response<GetAllItemSell>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        binding.tvTitle.text = body!!.title
                        binding.tvCategory.text = body!!.category
                        binding.tvDescription.text = body!!.description
                        binding.tvPrice.text = body!!.price.toString()
                    } else {
                        Toast.makeText(this@DetailActivity, "Gagal melihat detail", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllItemSell>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Gagal melihat detail", Toast.LENGTH_SHORT).show()
                }

            })
    }
}