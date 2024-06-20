package com.example.practice

import android.app.Activity
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
import com.example.practice.databinding.FragmentOneBinding

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


        // view에서 관련된 여러 동작 작성
        binding.selectDate.setOnClickListener{
            // 색깔 바꾸기
            DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener{
                // requirecontext() -> DatePickerDialog 첫번째 인자 설정
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    // 버튼에 사용자가 바꾼 날짜 대입
                    binding.selectDate.text = "$year 년 ${month+1} 월 $dayOfMonth 일"
                    binding.selectDate.textSize = 24f
                    binding.selectDate.setTextColor(Color.parseColor("#00ffff"))
                }
            }, 2024, 4,1).show()
            // 2024.5.1 -> 버튼 눌렀을 때 보이는 날짜 설정해두기
            // month 부분 -> 안드로이드에서는 0부터 시작 (0~11) -> 4월이면 3이라고 적어야해
            // listener 부분에는 null이 아니라
        }

        binding.selectTime.setOnClickListener{
            TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute:Int){
                    var morningornight = "오전"
                    if(hourOfDay > 12){
                        morningornight="오후"
                    }


                    binding.selectTime.text = "$morningornight $hourOfDay 시 $minute 분"

                    binding.selectTime.textSize = 24f
                    binding.selectTime.setTextColor(Color.parseColor("#ffff00"))

                }
            }, 15, 0, false).show()

            // 마지막을 트루로 하면 24시간제로 하겠다는 뜻
        }

        val items = arrayOf<String>("room1", "room2", "room3")
        var selected = 0
        val eventHandler2 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    binding.selectStudyroom.text="${items[selected]}"
                    //이렇게 화면(button 속에 보이게)에 보이게 함

                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Toast.makeText(context, "예약룸 선택이 취소되었습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.selectStudyroom.setOnClickListener{
            AlertDialog.Builder(requireContext()).run(){
                setTitle("알림창 - Single")
                setIcon(android.R.drawable.ic_dialog_alert)

                setSingleChoiceItems(items, 0, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("OK", eventHandler2)
                setNegativeButton("cancle", eventHandler2)
                binding.selectStudyroom.setTextColor(Color.parseColor("#00ffff"))
//                setNeutralButton("상세정보", null)
                show()
            }
        }

        binding.finishBtn.setOnClickListener {
            // EditText, Button (이름, 날짜, 시간, 스터디룸 선택한 거 체크한거)에서 텍스트 가져오기
            val name = binding.addEditView.text.toString()

            val date = binding.selectDate.text.toString()

            val time = binding.selectTime.text.toString()

            val studyroomnumber = binding.selectStudyroom.text.toString()

            // 가져온 텍스트를 TextView에 설정
            binding.studyroomResult.text = "예약자 이름은 $name\n예약 날짜는 $date $time \n예약룸은 $studyroomnumber"
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