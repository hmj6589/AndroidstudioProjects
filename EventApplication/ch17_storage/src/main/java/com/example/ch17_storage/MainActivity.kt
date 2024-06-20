package com.example.ch17_storage

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_storage.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
//    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    lateinit var sharedPreference: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 우리가 만들어둔 셰어드프리페런스에 선택한 값 넣기

        val color = sharedPreference.getString("color", "#00ff00")
        // 만약 선택한게 없다면 #00ff00을 디폴트로 가겟다

        binding.lastsaved.setBackgroundColor(Color.parseColor(color)) // 우리가 불러온 컬러를 백그라운드 컬러로 설정

        val idstr = sharedPreference.getString("id", "")
        // 만약 선택한게 없다면 #00ff00을 디폴트로 가겟다

        binding.todoTitle.text = idstr // 우리가 불러온 컬러를 백그라운드 컬러로 설정


        val size = sharedPreference.getString("size", "16.0f")

        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())
        // null 이 아니라서 -> !!


        // mutableList empty로 초기화
        val datas = mutableListOf<String>()

        // db 가져와
        val db = DBHelper(this). readableDatabase

        // db 값들 cursor에 넣기
        val cursor = db.rawQuery("select * from todo_tb", null)

        // 그 값들 출력하기
        while(cursor.moveToNext()){
            // datas는 null 일 수도 있응 께 -> ?도 추가
            datas?.add(cursor.getString(1))
        }

        // db 항상 다 쓰면 닫아주기
        db.close()


//        datas  = savedInstanceState?.let {
//            it.getStringArrayList("datas")?.toMutableList()
//        }
//            ?: let {
//                mutableListOf<String>()
//            }

        adapter=MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))



        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data?.getStringExtra("result")?.let {// "result"에 값이 저장되어 있으면(non-null)
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()


                    // 파일 읽기 작업 필요
                    // 파일에 대한 변수 필요
                    val file = File(filesDir, "test.txt")

                    // 변수를 읽어오기 위해서 readstream (아직 읽은 건 아니고 준비 단계)
                    val readstream: BufferedReader = file.reader().buffered()

                    // binding.lastsaved에 저장하는데 한줄씩 읽어오기 위해서 readLine
                    binding.lastsaved.text = "마지막 저장 시간 : " + readstream.readLine()


                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
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

        binding.lastsaved.setBackgroundColor(Color.parseColor(color)) // 우리가 불러온 컬러를 백그라운드 컬러로 설정



        val idstr = sharedPreference.getString("id", "")
        // 만약 선택한게 없다면 #00ff00을 디폴트로 가겟다

        binding.todoTitle.text = idstr // 우리가 불러온 컬러를 백그라운드 컬러로 설정

        val size = sharedPreference.getString("size", "16.0f")

        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

    }

    //    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putStringArrayList("datas", ArrayList(datas))  // 지금까지의 datas 저장
//    }
}