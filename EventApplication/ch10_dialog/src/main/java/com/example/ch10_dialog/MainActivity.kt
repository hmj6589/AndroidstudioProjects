package com.example.ch10_dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.UriPermission
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import com.example.ch10_dialog.databinding.ActivityMainBinding
import com.example.ch10_dialog.databinding.DialogCustomBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // lateint var 하면 변수는 만들어두는데 선언은 아래로 미룰 수 있다
    lateinit var binding : ActivityMainBinding //변수 만들고 선언은 아래로 미루기

    lateinit var toggle : ActionBarDrawerToggle // ActionBarDrawerToggle 타입을 가지고 있는 변수 선언



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // --- 토글 관련
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        // 여기서 3번째 인자는 open 했을 때 나오는 string, 4번째 인자는 close 했을 때 나오는 string

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState() // 상태


        // 이거 선택될 때 뭐??????
        // NavigationView.OnNavigationItemSelectedListener 이것도 상속받아야함
        binding.mainDrawerView.setNavigationItemSelectedListener (this)



        binding.btnDate.setOnClickListener{
            DatePickerDialog(this, object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(applicationContext, "$year 년 ${month+1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()
                    // 여기서 this 쓰면 메인엑티비티를 가리키는게 아니라서 this 쓰면 안됨

                    // 버튼에 사용자가 바꾼 날짜 대입
                    binding.btnDate.text = "$year 년 ${month+1} 월 $dayOfMonth 일"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.parseColor("#00ffff"))
                }
            }, 2024, 3,3).show()
            // month 부분 -> 안드로이드에서는 0부터 시작 (0~11) -> 4월이면 3이라고 적어야해
            // listener 부분에는 null이 아니라
        }


        binding.btnTime.setOnClickListener{
            TimePickerDialog(this, object:TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view:TimePicker, hourOfDay: Int, minute:Int){
                    Toast.makeText(applicationContext, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()

                    binding.btnTime.text = "$hourOfDay 시 $minute 분"
                    binding.btnTime.textSize = 24f
                    binding.btnTime.setTextColor(Color.parseColor("#ffff00"))

                }
            }, 15, 29, true).show()
            // 마지막을 트루로 하면 24시간제로 하겠다는 뜻
        }

        val eventHandler = object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlert.setOnClickListener{
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - 모앱")
                setIcon(android.R.drawable.ic_dialog_alert)

                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        val items = arrayOf<String>("빨강", "노랑", "파랑", "초록")
        binding.btnAlertItem.setOnClickListener{
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - 아이템")
                setIcon(android.R.drawable.ic_dialog_alert)

                setItems(items, object:DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                       Log.d("mobileapp", "${items[which]}선택")
                        binding.btnAlertItem.text="${items[which]} 선택"
                        }
                })
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        var selected = 0
        val eventHandler2 = object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    binding.btnAlertSingle.text="${items[selected]} 선택"
                    //이렇게 화면에 보이게 함

                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlertSingle.setOnClickListener{
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - Single")
                setIcon(android.R.drawable.ic_dialog_alert)

                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("예", eventHandler2)
                setNegativeButton("아니오", eventHandler2)
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        binding.btnAlertMulti.setOnClickListener{
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - 다수 선택")
                setIcon(android.R.drawable.ic_dialog_alert)

                setMultiChoiceItems(items, booleanArrayOf(false, true, true, false), object:DialogInterface.OnMultiChoiceClickListener{
                    // false, true, true, false 했어서 첨에 저거 2개만 체크되어 있는 상태로 보임
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${items[which]} ${if(isChecked) "선택" else "해제"}")
                    }
                })

                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

        val eventHandler3 = object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")

                    if(dialogBinding.rbtn1.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn1.text.toString()
                    }
                    if(dialogBinding.rbtn2.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn2.text.toString()
                    }
                    if(dialogBinding.rbtn3.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn3.text.toString()
                    }
                    if(dialogBinding.rbtn4.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn4.text.toString()
                    }

                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        var customDlg = AlertDialog.Builder(this).run(){
            setTitle("알림창 - 사용자 화면")
            setIcon(android.R.drawable.ic_dialog_alert)


            setView(dialogBinding.root)


            setPositiveButton("예", eventHandler3)
            setNegativeButton("아니오", eventHandler3)
//                setNeutralButton("상세정보", null)
            create()
        }
        binding.btnAlertCustom.setOnClickListener{
            customDlg.show()
        }

    }

    // 좌측 상단 네비게이션의 메뉴들
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // itemId 값에 따라 다르게 하겠다
            R.id.item_info -> {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksing.ac.kr"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/dir/서울역/수유역"))
                startActivity(intent)
                true
            }
            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))
                startActivity(intent)
                true
            }
            R.id.item_gallery -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
                startActivity((intent))

                true
            }
            R.id.item_call -> {
                // 전화 앱에 전화번호 보여주는 것만
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-911"))
                startActivity(intent)

                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:0994@duksung.ac.kr"))
                startActivity(intent)
                true
            }
        }
        return false // 디폴트 리턴은 펄스
    }

    // Option 메뉴를 위한 오버라이드 (menu를 다루고 있음)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        // 여기서 R은 리소스를 뜻한다.
        // 지금까지는 menu_navigation만 있으면 되는데 뷰를 가지고 있는 메뉴에 대한 부분 -> 따로 관리해줘야 함

        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        // (menu: Menu?) -> menu? 라는 뜻은 null도 가능하다는 뜻이므로 변수 선언할 때 menu?로 적어둬야 함
        // 코틀린에서 캐스팅 해줄 때는 뒤에 as ~ 로 작성해야함
        // 여기서는 serchView로 캐스팅 해줄게용!
        // 근데 SearchView는 multi class이므로 우리는 androidX에서 제공하는 SearchView로 임포트!

        // 검색(Query)하는 이벤트 처리 해주는 코드 작성해볼게요
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            // 오버라이드 작성
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색어를 입력하고 눌렀을 때 발생하는 이벤트에 대해서 처리

                Toast.makeText(applicationContext, "$query 검색합니다.", Toast.LENGTH_LONG).show()
                // 원래 첫번째 인자로 mainActivity 타입이 들어가야 하는데 현재 this는 searchView 타입임
                // 이런 경우에는 this가 아닌 applicationContext 로 대체

                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 텍스트가 바뀔 때 마다 (키보드를 입력할 때 마다) 검색어의 내용이 변경되는 이벤트 처리
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    // 오른쪽 상단바 메뉴를 클릭했을 때 작동되도록하는
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
            //토글이 가지고 있는 본래의 기능을 실행하겠다.
        }
        // 조건문 작성 when / if도 가능
        when(item.itemId){
            // itemId 값에 따라 다르게 하겠다
            R.id.item1 -> {
                Log.d("mobileapp", "OPTION MENU : 메뉴1")
                // 여기서 binding은 위에서 onCreate 함수 내에서 생성한 변수인데 여기서도 쓰고 싶으면
                // 전역 변수로 두면 됨
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item2 -> {
                Log.d("mobileapp", "OPTION MENU : 메뉴2")
                // 여기서 binding은 위에서 onCreate 함수 내에서 생성한 변수인데 여기서도 쓰고 싶으면
                // 전역 변수로 두면 됨
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item3 -> {
                Log.d("mobileapp", "OPTION MENU : 메뉴3")
                // 여기서 binding은 위에서 onCreate 함수 내에서 생성한 변수인데 여기서도 쓰고 싶으면
                // 전역 변수로 두면 됨
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item4 -> {
                Log.d("mobileapp", "OPTION MENU : 메뉴4")
                // 여기서 binding은 위에서 onCreate 함수 내에서 생성한 변수인데 여기서도 쓰고 싶으면
                // 전역 변수로 두면 됨
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}