package com.example.mid20220994

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.mid20220994.databinding.FragmentOneBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        // 날짜 입력 selectdate, text1
        binding.selectDate.setOnClickListener{
            // 색깔 바꾸기
            DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener{
                // requirecontext() -> DatePickerDialog 첫번째 인자 설정
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    // 버튼에 사용자가 바꾼 날짜 대입
                    binding.text1.text = "$year 년 ${month+1} 월 $dayOfMonth 일"
                    binding.text1.textSize = 24f
                    binding.text1.setTextColor(Color.parseColor("#00ffff"))

                    Toast.makeText(requireContext(), "$year 년 ${month+1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()

                }
            }, 2024, 3,27).show()
            // 2024.5.1 -> 버튼 눌렀을 때 보이는 날짜 설정해두기
            // month 부분 -> 안드로이드에서는 0부터 시작 (0~11) -> 4월이면 3이라고 적어야해
            // listener 부분에는 null이 아니라
        }

        // time -> text2
        binding.selectTime.setOnClickListener{
            TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute:Int){

                    binding.text2.text = "$hourOfDay 시 $minute 분"

                    binding.text2.textSize = 24f
                    binding.text2.setTextColor(Color.parseColor("#ffff00"))

                    Toast.makeText(requireContext(), "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()

                }
            }, 14, 0, true).show()

            // 마지막을 트루로 하면 24시간제로 하겠다는 뜻
        }

        //select_eat -> text3
        val items = arrayOf<String>("많이", "보통", "적게")
        var selected = 0
        val eventHandler2 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    binding.text3.text = "${items[selected]}"
                    //이렇게 화면(button 속에 보이게)에 보이게 함

                    Toast.makeText(context, "${items[selected]} 선택", Toast.LENGTH_LONG).show()

                }
//                } else if (which == DialogInterface.BUTTON_NEGATIVE){
//                    Toast.makeText(context, "예약룸 선택이 취소되었습니다.", Toast.LENGTH_LONG).show()
//                }
            }
        }

        binding.selectEat.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("식사량 선택")
                setIcon(android.R.drawable.ic_dialog_alert)

                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("예", eventHandler2)
                setNegativeButton("아니오", eventHandler2)
                binding.text3.setTextColor(Color.parseColor("#00ffff"))
//                setNeutralButton("상세정보", null)
                show()
            }
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

        // 특이사항 -> select_anyway -> text4

        val anyway_item = arrayOf<String>("나트륨 적게", "설탕 적게")
        binding.selectAnyway.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("특이사항 선택")
                setIcon(android.R.drawable.ic_dialog_alert)

                setMultiChoiceItems(anyway_item, booleanArrayOf(false, false), object:DialogInterface.OnMultiChoiceClickListener{
                    // false, true, true, false 했어서 첨에 저거 2개만 체크되어 있는 상태로 보임
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${anyway_item[which]} ${if(isChecked) "선택" else "해제"}")



                        binding.text4.text = "${anyway_item[which]}"

                        binding.text4.setTextColor(Color.parseColor("#00ffff"))

                    }
                })

                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        binding.finishBtn.setOnClickListener {
            // EditText, Button (이름, 날짜, 시간, 스터디룸 선택한 거 체크한거)에서 텍스트 가져오기

            val date = binding.text1.text.toString()

            val time = binding.text2.text.toString()

            val eat = binding.text3.text.toString()

            val aw = binding.text4.text.toString()

            // 가져온 텍스트를 TextView에 설정
            binding.eatResult.text = "$date \n$time \n$eat  \n$aw"
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}