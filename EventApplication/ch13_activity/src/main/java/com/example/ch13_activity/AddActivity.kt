package com.example.ch13_activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var date = intent.getStringExtra("today")
        // String 형태의 값 넘길게

        binding.date.text=date

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // addactivity home 화면에 표시하겠다는 뜻


        binding.btnSave.setOnClickListener{
            var intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())

            setResult(Activity.RESULT_OK, intent)
            // intent 전달받고 값 넣고 그건 동일한데
            // 다시 돌아갈 때는 setresult로 돌아가겠다

            finish()
            true // 트루를 리턴
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        var intent = intent

        setResult(Activity.RESULT_OK, intent)

        finish()
        return true
    }
}