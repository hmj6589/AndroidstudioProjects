package com.example.practice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivitySecondBinding
import com.example.practice.databinding.FragmentTwoBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("SecondActivity", "onCreate called")

        val binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val text = intent.getStringExtra("radiotext")
        binding.friendAdd.text = "$text 추가하기"

        binding.btnSave.setOnClickListener{
            val name=binding.addName.text.toString()
            // 선택된 옵션에 따라서 '[친구]' 또는 '[장소]'를 추가
            val selectedOption = intent.getStringExtra("radiotext") ?: "" // 기본값은 빈 문자열
            val nameWithType = if (selectedOption == "친구") {
                "[친구] $name"
            } else if (selectedOption == "장소") {
                "[장소] $name"
            } else {
                name // 선택된 옵션이 없는 경우에는 이름만 반환
            }

            var returnIntent = intent




            returnIntent.putExtra("friend_name", nameWithType) // 친구 이름
            returnIntent.putExtra("friend_number", binding.addNumber.text.toString())

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
            true
        }
    }
}
