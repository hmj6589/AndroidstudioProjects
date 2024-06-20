package com.example.ch18_image2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ch18_image2.databinding.ItemHinfoBinding

class PhpViewHolder(val binding: ItemHinfoBinding) : RecyclerView.ViewHolder(binding.root)

class PhpAdapter (val context: Context, val itemList: ArrayList<HinfoData>): RecyclerView.Adapter<PhpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhpViewHolder(ItemHinfoBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PhpViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            // data와 viewHolder 연결해주는 작업
            tvName.text = data.name
            tvAge.text = data.Age.toString()
            tvAddr.text = data.addr

            // 전체를 클릭했을 때 -> 어떤 반응 설정
            root.setOnClickListener{
                Toast.makeText(context, "Root", Toast.LENGTH_LONG).show()
                
                // 다른 액티비티 부르는 방법 : intent 사용
                Intent(context, HumanActivity::class.java).apply { 
                    // 요 어댑터에서 액티비티 쪽으로 이름 나이 주소 전달
                    putExtra("name", tvName.text)
                    putExtra("age", tvAge.text)
                    putExtra("addr", tvAddr.text)
                }.run{
                    // 리턴 타입 없는 경우 보내는 방법
                    // startActivity(this)
                    
                    // 어댑터에서 클릭 리스너 붙여서 갈 것이므로 앞에 context 추가
                    context.startActivity(this)
                }
            }

            // 이름를 클릭했을 때 -> 어떤 반응 설정
            tvName.setOnClickListener {
                Toast.makeText(context, "Name", Toast.LENGTH_LONG).show()
            }

            // 사진을 클릭했을 때 -> 어떤 반응 설정
            imageView.setOnClickListener {
                Toast.makeText(context, "Image", Toast.LENGTH_LONG).show()
            }
        }
    }
}