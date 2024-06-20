package com.example.ch17_storage

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.example.ch17_storage.databinding.ActivityAddBinding
import java.io.File
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 우리가 만들어둔 셰어드프리페런스에 선택한 값 넣기

        val color = sharedPreference.getString("color", "#00ff00")
        // 만약 선택한게 없다면 #00ff00을 디폴트로 가겟다

        binding.date.setTextColor(Color.parseColor(color)) // 우리가 불러온 컬러를 text 컬러로 설정



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            // 저장하기 위한 변수 선언
            val edt_srt = binding.addEditView.text.toString()


            val intent = intent
            intent.putExtra("result", edt_srt)
            setResult(Activity.RESULT_OK, intent)


            // db에 저장하기

            // 1. db 불러오기
            val db = DBHelper(this).writableDatabase

            // 2. edt_srt값을 table에 넣기
            db.execSQL("insert into todo_tb (todo) values (?) ", arrayOf<String>(edt_srt))

            // 3. 테이블 닫기
            db.close()



            // 파일 저장하기
            val dataFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss") // 년 월 일 시 분 초

            // 파일을 저장하기 위해서는 포인터가 필요
            // filesDir -> 내장 공간에 대한 폴더 제공
            val file = File(filesDir, "test.txt")

            // 파일을 쓰기 위해서는 write
            val writestream: OutputStreamWriter = file.writer()

            // 파일에 대한 writestream 가져와서 글 적겠다 현재 시간
            writestream.write(dataFormat.format(System.currentTimeMillis()))

            // 최종적으로 flush 해줘야 적힘
            writestream.flush()



            finish()
            true
        }
    } // onCreate()

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

    // menu 화면에 보여지게
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // menu 선택 시 어떠한 행동이 보여지게
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_main_setting){
            // 만약 아이디가 저거인 걸 누르면
            // 설정 창으로 가겠다를 보여줘야함
            // 세팅액티비티 호출하기 -> 인텐트 이용!
            val intent = Intent(this, SettingActivity::class.java)
            // 특별하게 전달되는 값이 없으므로
            // 바로 start

            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        // 색상 바꾸고 액티비티에 바로 반영하게 해줌

        super.onResume()


        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 우리가 만들어둔 셰어드프리페런스에 선택한 값 넣기

        val color = sharedPreference.getString("color", "#00ff00")
        // 만약 선택한게 없다면 #00ff00을 디폴트로 가겟다

        binding.date.setTextColor(Color.parseColor(color)) // 우리가 불러온 컬러를 text 컬러로 설정

    }


}