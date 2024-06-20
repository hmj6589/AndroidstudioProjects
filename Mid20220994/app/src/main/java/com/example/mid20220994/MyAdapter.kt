package com.example.mid20220994

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mid20220994.databinding.ItemRecyclerViewBinding

class MyViewHolder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)


class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){ // 2-1
    // ? 가 있어야 -> 내가 지금 전달할려고 하는게 null 일 수도 있다는 표현 넘기기

    override fun getItemCount(): Int {
        return datas?.size ?:0;     // 2-2
        // datas 뒤에 ?
        // ?: 는 합쳐서 사용 -> null인 경우에는 0을 넣는다.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        // binding.friendName.text = datas!![position]      // 2-3
        // !! -> datas가 null이 아닌 경우에만 넘기겠다라는 뜻
        // null 이면 넘길 게 없으

        val data = datas?.get(position) ?: return  // 데이터가 null이면 처리 중단

        val parts = data.split("\n")
        if (parts.size >= 2) {  // 적어도 2개의 파트가 있는지 확인
            binding.addName.text = parts[0]
            binding.addNumber.text = parts[1]

        }
    }
}
