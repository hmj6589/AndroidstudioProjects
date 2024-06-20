package com.example.ch18_image2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image2.databinding.ActivityPhpBinding
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩
        val binding = ActivityPhpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 클릭 리스너
        binding.btnPhp.setOnClickListener {
            // age에 적혀있는 글 불러오기
            // 숫자 입력 -> 그에 맞는 데이터 가져오고
            // 아무것도 입력 안하면 -> 모든 데이터 가져오고
            var age = binding.etAge.text.toString() ?: ""

            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(age) // networkservice의 getPhpList 메소드가 인자를 받는지 아닌지 잘 보기 !




            call?.enqueue(object: Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "$response")
                        Log.d("mobileApp", "${response.body()}")

                        // response body는 반환되어서 가지고 오는 것?
                        // json으로 받는 것 중 가장 먼저 앞에 오는 것은 result임, null이면 안됨
                        binding.phpRecyclerView.adapter = PhpAdapter(this@PhpActivity, response.body()?.result!!)
                        binding.phpRecyclerView.layoutManager = LinearLayoutManager(this@PhpActivity) // PhpActivity
                        binding.phpRecyclerView.addItemDecoration(DividerItemDecoration(this@PhpActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Log.d("mobileApp", "onFailure")
                }
            })
        }
    }
}