package com.example.eventapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.example.eventapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
//        setContentView(R.layout.activity_main)
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // 이렇게 해야 xml에 있는 걸 코틀린으로 가져올 수 있음
        
        setContentView(binding.root)
        
        // 이렇게 두 줄은 코틀린 코드 작성시 무조건 들어감

        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f
        // 이렇게 xml에 있는 위젯 속성 바꾸기 가능

        var prevTime = 0L


        binding.startButton.setOnClickListener{
            // it은 startButton 나타냄

            binding.chronometer.base=SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()

            binding.startButton.isEnabled=false
            binding.stopButton.isEnabled=true
            binding.resetButton.isEnabled=true

        }
        binding.stopButton.setOnClickListener{
            prevTime = binding.chronometer.base-SystemClock.elapsedRealtime()
            // start에서 stop까지 시간이 저장된다
            binding.chronometer.stop()

            binding.stopButton.isEnabled=false
            binding.startButton.isEnabled=true
            binding.resetButton.isEnabled=false

        }
        binding.resetButton.setOnClickListener {
            prevTime = 0L
            binding.chronometer.stop()


            binding.resetButton.isEnabled=false
            binding.startButton.isEnabled=true
            binding.stopButton.isEnabled=true

        }

    }

    // 키보드만 처리해줄 수 있는 함수 <- AppCompatActivity()가 상속해줌
    // Override로 처리해줘야함
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {
                if(System.currentTimeMillis() - initTime > 3000) { // 3초를 의미함

                    Log.d("mobileapp", "BACK KEY가 눌렸어요.. 종료하려면 한번 더 누르시던가!")
                    initTime = System.currentTimeMillis() // 처음 Back을 누른 시간이 저장됨

                    Toast.makeText(this, "back key가 눌렸어요.. 종료하려면 한번 더 누르세요", Toast.LENGTH_LONG).show()


                    return true // back 키에 대한 처리는 하되 프로그램 종료는 하지 않겠다

                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp", "VOLUME_UP KEY가 눌렸어요..")
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp", "VOLUME_UP DOWN가 눌렸어요..")
        }
        return super.onKeyDown(keyCode, event)
    }
}