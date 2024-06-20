package com.example.ch18_image2


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image2.RetrofitConnection
import com.example.ch18_image2.XmlAdapter
import com.example.ch18_image2.XmlResponse
import com.example.ch18_image2.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
//import com.example.ch18_image2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener { // Drawer 메뉴
    lateinit var binding: ActivityMainBinding


    // DrawerLayout Toggle
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var headerView : View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 0
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // DrawerLayout Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // Drawer 메뉴
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("mobileapp", "button.setOnClickListener")

            val intent = Intent(this, AuthActivity::class.java)

            if(button.text.equals("로그인")){
                intent.putExtra("status", "logout")
            }
            else if(button.text.equals("로그아웃")){
                intent.putExtra("status", "login")
            }

            startActivity(intent)

            binding.drawer.closeDrawers()
        }



        // 본 화면 기능 작성
        binding.btnSearch.setOnClickListener{

            if(MyApplication.checkAuth()){ // 인증 통과한 사람만 가능하게
                val name = binding.edtName.text.toString()
                Log.d("mobileapp", name)

                val call: Call<XmlResponse> = RetrofitConnection.xmlNetworkService.getXmlList(
                    name,
                    1,
                    10,
                    "xml",
                    "O3hl/g6Dtviy+FkpasNjvSNu29/InnSiaEpLCXwtw8pSxQdlIiCoVyblcsV3zeckJK3lECAqcJrCE0YO7imieg==" // 일반인증키(Decoding)
                )

                call?.enqueue(object : Callback<XmlResponse> {
                    override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                        if(response.isSuccessful){
                            Log.d("mobileApp", "$response")
                            Log.d("mobileApp", "${response.body()}")
                            binding.xmlRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.xmlRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                            binding.xmlRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                        }
                    }

                    override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                        Log.d("mobileApp", "onFailure ${call.request()}")
                    }
                })

            }
            else { // 만약 인증 통과하지 못했다면 "인증 먼저 진행"해달라고 뜨게 하기
                Toast.makeText(this, "인증을 먼저 진행해주세요.", Toast.LENGTH_LONG).show()
            }

        } // binding.btnSearch.setOnClickListener


    } //onCreate()

    // DrawerLayout Toggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.item_php-> { // php 데이터 불러오기 버튼 클릭 시
                Log.d("mobileapp", "Php 메뉴")

                val intent = Intent(this, PhpActivity::class.java)

                startActivity(intent)

                binding.drawer.closeDrawers()
                // 해당 처리가 끝나면 drawerLayout을 닫아줌

                true
            }

            R.id.item_board -> { // 게시판 버튼 클릭 시
                Log.d("mobileapp", "게시판 메뉴")

                val intent = Intent(this, BoardActivity::class.java)

                startActivity(intent)

                binding.drawer.closeDrawers()
                // 해당 처리가 끝나면 drawerLayout을 닫아줌

                true
            }

            R.id.item_setting -> { // 설정 버튼 클릭 시
                Log.d("mobileapp", "설정 메뉴")

                binding.drawer.closeDrawers()
                // 해당 처리가 끝나면 drawerLayout을 닫아줌

                true
            }
        }
        return false
    }
    override fun onStart(){
        super.onStart()

        // 로그인 하고 돌아왔을 때 버튼 자체가 로그아웃으로 바뀌어있으면 좋겠어
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth()){ // 만약 로그인했을 때 승인이라면 !
            button.text = "로그아웃" // 로그인 -> 로그아웃
            // 이메일 보이게 하기
            tv.text = "${MyApplication.email}님\n반갑습니다!"
        }
        else {
            button.text = "로그인"
            tv.text = "안녕하세요!!"
        }
    }
}