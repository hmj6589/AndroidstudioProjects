package com.example.mid20220994

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid20220994.databinding.ActivityAddBinding
import com.example.mid20220994.databinding.ActivityMainBinding
import com.example.mid20220994.databinding.FragmentTwoBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AddActivity", "onCreate called")

        val binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val text = intent.getStringExtra("radiotext")
        binding.friendAdd.text = "$text"

        binding.btnSave.setOnClickListener{
            val name=binding.name.text.toString()
            // 선택된 옵션에 따라서 채소, 과일, 육류 추가
            val selectedOption = intent.getStringExtra("radiotext") ?: "" // 기본값은 빈 문자열
            val nameWithType = if (selectedOption == "과일") {
                "[과일] $name"
            } else if (selectedOption == "채소") {
                "[채소] $name"
            }
            else if (selectedOption == "육류") {
                "[육류] $name"
            } else {
                name // 선택된 옵션이 없는 경우에는 이름만 반환
            }

            var returnIntent = intent



            returnIntent.putExtra("radiotext", text)
            returnIntent.putExtra("add_name", nameWithType) // 종류에 맞는 품목 이름
            returnIntent.putExtra("add_number", binding.number.text.toString())

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
            true
        }
    }
}
