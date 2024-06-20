package com.example.ch13_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var datas: MutableList<String>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // datas = mutableListOf<String>()
        datas = savedInstanceState?.let{
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let{
            mutableListOf<String>()
        }



        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter //MyAdapter(datas)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        val requestLauncher : ActivityResultLauncher<Intent>
        = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){

                it.data!!.getStringExtra("result")?.let{
                    if(it != ""){
                        datas?.add(it)
                        adapter.notifyDataSetChanged()
                    }
                    // 입력값이 빈칸이 아니어야 recycleView에 등록ㅇ
                }
        }

        binding.mainFab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)

            val dataFormat = SimpleDateFormat("yyy-MM-dd")// 년월일
            intent.putExtra("today", dataFormat.format(System.currentTimeMillis()))
            // dataFormat을 스트링 형태로 ~

//            startActivity(intent)
            requestLauncher.launch(intent)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}