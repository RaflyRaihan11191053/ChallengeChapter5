package com.example.challengechapter5
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.example.challengechapter5.databinding.ActivityPostBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class PostActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityPostBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnPost.setOnClickListener {
//            postItem()
//        }
//    }
//
//    private fun postItem() {
//        val request = PostAnItemRequest(
//            binding.etTitleItem.text.toString(),
//            binding.etCategoryItem.text.toString(),
//            binding.etDescriptionItem.text.toString(),
//            binding.etPriceItem.text.toString().toDouble(),
//            binding.etLinkImage.text.toString()
//        )
//        ApiClient.instance.postItem(request)
//            .enqueue(object : Callback<PostAnItemResponse> {
//                override fun onResponse(
//                    call: Call<PostAnItemResponse>,
//                    response: Response<PostAnItemResponse>
//                ) {
//                    val body = response.body()
//                    val code = response.code()
//                    if (code == 200){
//                        Toast.makeText(this@PostActivity, "Berhasil posting item", Toast.LENGTH_SHORT).show()
//                        finish()
//                    } else {
//                        Toast.makeText(this@PostActivity, "Gagal posting item", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<PostAnItemResponse>, t: Throwable) {
//                    Toast.makeText(this@PostActivity, "Gagal posting item", Toast.LENGTH_SHORT).show()
//                }
//
//            })
//    }
//}