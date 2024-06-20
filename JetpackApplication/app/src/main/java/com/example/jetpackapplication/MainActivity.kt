package com.example.jetpackapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jetpackapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    class MyFragmentPagerAdapter(activity:FragmentActivity): FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment()) // 총 3개의 fregment를 리스트로 담고 있다
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

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)  // 뷰페이저 포함해주는 역할을 하는 adapter

        // 뷰페이저랑 텝레이아웃 연결
        TabLayoutMediator(binding.tabs, binding.viewpager){
            tab, position ->
            tab.text = "TAB ${position+1}"
        }.attach()


    }
}