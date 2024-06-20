package com.example.midpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.midpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        // 뷰바인딩 설정 : 이렇게 해야 xml에 있는 걸 코틀린으로 가져올 수 있음

        setContentView(binding.root)

    }
}