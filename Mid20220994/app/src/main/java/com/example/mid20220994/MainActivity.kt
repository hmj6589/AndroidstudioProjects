package com.example.mid20220994

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mid20220994.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle // ActionBarDrawerToggle 타입을 가지고 있는 변수 선언


    class MyFragmentPagerAdapter(activity:FragmentActivity): FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments = listOf(OneFragment(), TwoFragment()) // 총 3개의 fregment를 리스트로 담고 있다
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
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // 뷰 바인딩 설정 : 이렇게 해야 xml에 있는 걸 코틀린으로 가져올 수 있음

        setContentView(binding.root)
        // 뷰 바인딩 설정



        // --- 토글 관련
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        // 여기서 3번째 인자는 open 했을 때 나오는 string, 4번째 인자는 close 했을 때 나오는 string

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState() // 상태


        // 이거 선택될 때 뭐??????
        // NavigationView.OnNavigationItemSelectedListener 이것도 상속받아야함
        binding.mainDrawerView.setNavigationItemSelectedListener (this)



        // 프래그먼트 어댑터 생성
        val fragmentAdapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = fragmentAdapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "TAB ${position + 2}"
        }.attach()




    }
    // 오른쪽 상단바 메뉴를 클릭했을 때 작동되도록하는
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
            //토글이 가지고 있는 본래의 기능을 실행하겠다.
        }
        return super.onOptionsItemSelected(item)
    }


    // 좌측 상단 네비게이션의 메뉴들
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // itemId 값에 따라 다르게 하겠다
            R.id.item_info -> {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksing.ac.kr"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/dir/덕성여자대학교/수유역"))
                startActivity(intent)
                true
            }
            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.65198, 127.0162"))
                startActivity(intent)
                true
            }
            R.id.item_call -> {
                // 전화 앱에 전화번호 보여주는 것만
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:911"))
                startActivity(intent)

                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kmlee@ds.ac.kr"))
                startActivity(intent)
                true
            }
        }
        return false // 디폴트 리턴은 펄스
    }


}