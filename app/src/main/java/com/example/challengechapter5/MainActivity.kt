package com.example.challengechapter5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengechapter5.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        fetchAllData()
    }

    private fun fetchAllData() {
        ApiClient.instance.getAllItems()
            .enqueue(object : Callback<List<GetAllItemSell>> {
                override fun onResponse(
                    call: Call<List<GetAllItemSell>>,
                    response: Response<List<GetAllItemSell>>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        if (body != null) {
                            showList(body)
                        }
                        binding.pbItems.visibility = View.GONE
                    } else {
                        binding.pbItems.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<List<GetAllItemSell>>, t: Throwable) {
                    binding.pbItems.visibility = View.GONE
                }

            })
    }

    private fun showList(data: List<GetAllItemSell>) {
        val adapter = AdapterItem(object : AdapterItem.OnClickListener {
            override fun onClickItem(data: GetAllItemSell) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("id", data.id)
                startActivity(intent)
            }
        })
        adapter.itemsData(data)
        binding.rvItems.adapter = adapter
    }
}