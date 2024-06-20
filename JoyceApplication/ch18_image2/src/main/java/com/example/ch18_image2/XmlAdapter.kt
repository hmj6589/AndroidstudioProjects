package com.example.ch18_image2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch18_image2.databinding.ItemMainBinding
import com.example.ch18_image2.myXmlItem
//import com.example.ch18_image2.databinding.ItemMainBinding

class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class XmlAdapter(val datas:MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {     // 1-1
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]           // 1-2

        // 1-3
        binding.name.text = model.prdlstNm
        binding.manufacture.text = model.manufacture
        binding.nutrient.text = model.nutrient

        // model.imgurl1
        Glide.with(binding.root)
            .load(model.imgurl1)
            .override(400,300)
            .into(binding.urlImage)


        Glide.with(binding.root)
            .load(model.imgurl2)
            .override(400,300)
            .into(binding.urlImage2)
    }
}