package com.example.joyceapplication


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.joyceapplication.databinding.ItemMainBinding



class JsonViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)


class JsonAdapter(val datas:MutableList<myJsonItems>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JsonViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as JsonViewHolder).binding

        val model = datas!![position]

        binding.name.text = model.districtName
        binding.issue.text = model.issueDate + " " + model.issueTime
        binding.issueGbn.text = model.issueGbn
    }
}