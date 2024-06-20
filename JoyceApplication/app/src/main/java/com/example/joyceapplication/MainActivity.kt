package com.example.joyceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.joyceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonfragment = JsonFragment()
        val xmlfragment = XmlFragment()

        val bundle = Bundle() // 프레그먼트 값 넘겨주기 위해서 번들 사용

        binding.btnSearch.setOnClickListener {
            bundle.putString("searchYear", binding.edtYear.text.toString()) // 번들에 toString 이용해서 문자열로 만들고 putString으로 넣어주겠다

            // 어떤 버 클릭에 따라 어떤 프레그먼트를 이용해서 화면에 보여지게 할 것인지 결정

            if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) {
                jsonfragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, jsonfragment)
                    .commit()

            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) {
                xmlfragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }
        }
    }
}