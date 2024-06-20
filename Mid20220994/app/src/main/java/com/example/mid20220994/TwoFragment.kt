package com.example.mid20220994

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid20220994.databinding.DialogTypeBinding
import com.example.mid20220994.databinding.FragmentTwoBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TwoFragment : Fragment() {
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


    lateinit var binding: FragmentTwoBinding
    lateinit var adapter: MyAdapter
    var datas= mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentTwoBinding.inflate(inflater, container, false)

        // datas = mutableListOf<String>()
        datas = savedInstanceState?.getStringArrayList("datas")?.toMutableList() ?: mutableListOf()
        val adapter = MyAdapter(datas)

        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )


        // ActivityResultLauncher 등록
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val ra = data?.getStringExtra("radiotext")
                val eatNumber = data?.getStringExtra("add_number")
                val name = data?.getStringExtra("add_name")
                val combinedResult = "$ra\n$name\n$eatNumber"
                datas.add(combinedResult)
                adapter.notifyDataSetChanged() // RecyclerView 갱신
            }
        }



        val dialogBinding = DialogTypeBinding.inflate(layoutInflater)

        val eventHandler3 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(requireContext(), AddActivity::class.java)

                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    // binding.btnAlertSingle.text="${items[selected]} 선택"
                    //이렇게 화면에 보이게 함
                    var radiotext=dialogBinding.rbtn1.text.toString()

                    if(dialogBinding.rbtn1.isChecked){
                        radiotext = dialogBinding.rbtn1.text.toString()
                    } else if(dialogBinding.rbtn2.isChecked){
                        radiotext = dialogBinding.rbtn2.text.toString()
                    }else{
                        radiotext = dialogBinding.rbtn3.text.toString()
                    }
                    intent.putExtra("radiotext", radiotext)


                    // SecondActivity 실행
//            startActivity(intent)
                    requestLauncher.launch(intent)


                } else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }

        var typeDlog = AlertDialog.Builder(requireContext()).run(){
            setTitle("종류 선택")
            setIcon(android.R.drawable.ic_dialog_alert)

            setView(dialogBinding.root)
            setPositiveButton("예", eventHandler3)
            setNegativeButton("아니오", eventHandler3)
//                setNeutralButton("상세정보", null)
            create()

        }
        binding.mainFab.setOnClickListener{
            typeDlog.show()
        }
        binding.recyclerView.adapter = adapter // 어댑터 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())









        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}