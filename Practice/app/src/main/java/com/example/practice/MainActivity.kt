package com.example.practice

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice.databinding.ActivityMainBinding
import com.example.practice.databinding.DialogCustomBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle // ActionBarDrawerToggle 타입을 가지고 있는 변수 선언



    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments =
                listOf(OneFragment(), TwoFragment(), ThreeFragment()) // 총 3개의 fregment를 리스트로 담고 있다
        }

        // 반드시 포함해야하는 오버라이드 함수 -> getItemCount()
        override fun getItemCount(): Int {
            return fragments.size
        }
        // 반드시 포함해야하는 오버라이드 함수 -> createFragment()
        override fun createFragment(position: Int): Fragment {
            // position -> 0-> onefragment 1-> two 2-> three ...
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) // 이거 있어야함!
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar) // 툴바를 액션바로 설정



        // --- 토글 관련
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        // 여기서 3번째 인자는 open 했을 때 나오는 string, 4번째 인자는 close 했을 때 나오는 string

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState() // 상태

        // NavigationView.OnNavigationItemSelectedListener 이것도 상속받아야함
        binding.mainDrawerView.setNavigationItemSelectedListener(this)



        // 프래그먼트 어댑터 생성
        val fragmentAdapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = fragmentAdapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "TAB ${position + 1}"
        }.attach()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return when (item.itemId) {
            R.id.login -> {
                val toast = Toast.makeText(this, "개발중입니다.", Toast.LENGTH_LONG)
                toast.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    // 좌측 상단 네비게이션의 메뉴들
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // itemId 값에 따라 다르게 하겠다
            R.id.item_info -> {
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksing.ac.kr"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.duksung.ac.kr"))
                startActivity(intent)
                true
            }
            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))
                startActivity(intent)
                true
            }
            R.id.item_call -> {
                // 전화 앱에 전화번호 보여주는 것만
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-911"))
                startActivity(intent)

                true
            }
        }
        return false // 디폴트 리턴은 펄스
    }


    // Option 메뉴를 위한 오버라이드 (menu를 다루고 있음)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        // 여기서 R은 리소스를 뜻한다.
        // 지금까지는 menu_navigation만 있으면 되는데 뷰를 가지고 있는 메뉴에 대한 부분 -> 따로 관리해줘야 함

        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        // (menu: Menu?) -> menu? 라는 뜻은 null도 가능하다는 뜻이므로 변수 선언할 때 menu?로 적어둬야 함
        // 코틀린에서 캐스팅 해줄 때는 뒤에 as ~ 로 작성해야함
        // 여기서는 serchView로 캐스팅 해줄게용!
        // 근데 SearchView는 multi class이므로 우리는 androidX에서 제공하는 SearchView로 임포트!

        val activityContext = this // 다이얼로그 생성 시 this 대신ㄹ 사용하기 위해서 미리 받아두기

        // 검색(Query)하는 이벤트 처리 해주는 코드 작성해볼게요
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 오버라이드 작성
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색어를 입력하고 눌렀을 때 발생하는 이벤트에 대해서 처리

                val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

                // 검색어를 나타내는 TextView들에 사용자가 입력한 검색어 설정
                dialogBinding.textview2.text = "$query"
                dialogBinding.textview2.setTextColor(Color.RED)


                // 다이얼로그 생성
                val dialog = AlertDialog.Builder(activityContext).run {
                    setTitle("검색어 확인")
                    setView(dialogBinding.root)
                    setNegativeButton("닫기", DialogInterface.OnClickListener { dialog, which ->
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            Log.d("mobileapp", "BUTTON_NEGATIVE")
                        }
                    }).create()
                }

                dialog.show()

                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 텍스트가 바뀔 때 마다 (키보드를 입력할 때 마다) 검색어의 내용이 변경되는 이벤트 처리
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}

