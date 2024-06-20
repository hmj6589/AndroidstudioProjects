package com.example.practice

//import SecondActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.forEach
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.FragmentOneBinding
import com.example.practice.databinding.FragmentTwoBinding
import java.text.SimpleDateFormat

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


    lateinit var binding:FragmentTwoBinding
    lateinit var adapter: MyAdapter
    var datas= mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentTwoBinding.inflate(inflater, container, false)

        // datas = mutableListOf<String>()
        datas = savedInstanceState?.getStringArrayList("datas")?.toMutableList() ?: mutableListOf()




        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        val adapter = MyAdapter(datas)


        // ActivityResultLauncher 등록
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val phoneNumber = data?.getStringExtra("friend_number")
                val name = data?.getStringExtra("friend_name")
                val combinedResult = "$name\n$phoneNumber"
                datas.add(combinedResult)
                adapter.notifyDataSetChanged() // RecyclerView 갱신
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(requireContext(), SecondActivity::class.java)

            // 라디오 그룹 내의 체크된 라디오 버튼을 찾아 해당 텍스트를 인텐트에 추가
            val radiotext = if (binding.radioButton1.isChecked) {
                binding.radioButton1.text.toString()
            } else {
                binding.radioButton2.text.toString()
            }


            intent.putExtra("radiotext", radiotext)


            // SecondActivity 실행
//            startActivity(intent)
            requestLauncher.launch(intent)
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