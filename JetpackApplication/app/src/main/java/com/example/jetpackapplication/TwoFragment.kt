package com.example.jetpackapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackapplication.databinding.FragmentTwoBinding
import com.example.jetpackapplication.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas:MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 그러면 그 항목은 어떤 레이아웃을 사용할 것인가
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // holder라고 하는 애가 MyViewHolder를 말함
        // 그러면 어떻게 데이터를 집어 넣을 것인가
        // datas 하고 ItemRecycleviewBinding 하고 연결 시켜줌
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position] // position 번 째 있는 것을 넣겟다
    }

    override fun getItemCount(): Int { // 내가 전달받은 데이터의 리스트의 길이 반환
        return datas.size
    }
}

class MyItemDecoration(val context: Context) : RecyclerView.ItemDecoration(){
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 그림 -> 항목 (순서)
        super.onDraw(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            0f, 0f, null)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 항목 -> 그림 (순서)
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            500f, 500f, null)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        view.setBackgroundColor(Color.parseColor("#123456"))
        // 이건 안할거임 왜? 이건 xml에서도 충분히 할 수 있자나
    }
}

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

    // 우리는 onCreateView 만지기
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentTwoBinding.inflate(inflater, container,  false)

        var datas = mutableListOf<String>()

        for(i in 1..10){
            datas.add("Item $i")
        }

        // Adapter & Viewholder-> 반드시 설정
        var adpter = MyAdapter(datas) // 변수 설정
        binding.recyclerView.adapter = adpter

        // LayoutManager -> 반드시 설정
        val linearlayout = LinearLayoutManager(activity)
        linearlayout.orientation = LinearLayoutManager.HORIZONTAL // 가로 버전

        var gridlayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)


//        binding.recyclerView.layoutManager = linearlayout
        binding.recyclerView.layoutManager = gridlayout


        // 선택적
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        // 아까 그 버튼이 눌려졌을 때
        binding.mainFab.setOnClickListener{
            // recyclerview가 참조하는 것에 데이터 추가?
            datas.add("Add Item") // 버튼을 누를 때마다 이게 추가됨
            adpter.notifyDataSetChanged() //데이터 변경 되었음을 알림
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