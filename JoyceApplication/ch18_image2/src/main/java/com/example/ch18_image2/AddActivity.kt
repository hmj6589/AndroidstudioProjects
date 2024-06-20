package com.example.ch18_image2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.ch18_image2.databinding.ActivityAddBinding
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddBinding

    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvId.text = MyApplication.email // 현재 이메일 출력

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode === android.app.Activity.RESULT_OK){
                binding.addImageView.visibility = View.VISIBLE
                Glide
                    .with(applicationContext)
                    .load(it.data?.data)
                    .override(200,150)
                    .into(binding.addImageView)

                uri = it.data?.data!!
            }
        }

        binding.uploadButton.setOnClickListener {
            // 이미지 올린 것 처리
            val intent = Intent(Intent.ACTION_PICK)

            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

            // 이미지를 픽 해서 왔을 때 어떤 처ㄹ리를 할 것인가
            requestLauncher.launch(intent)
        }

        binding.saveButton.setOnClickListener {  // 저장하기 버튼 누르면
            if(binding.input.text.isNotEmpty()){ // 만약 사용자가 edit text에 입력했다면
                // firesotre에 저장하기 전에 데이터(document) 만들어줘야함

                // 로그인 이메일(아이디), 스타(별표), 한줄평, 입력시간에 대한 내용 저장할꺼야
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                val data = mapOf(
                    // key value
                    "email" to MyApplication.email,
                    "stars" to binding.ratingBar.rating.toFloat(), // 별표 갯수 가져와  (float 형태로 가져와)
                    "comments" to binding.input.text.toString(), // 한줄평 -> String 형태로 가져와
                    "date_time" to dateFormat.format(System.currentTimeMillis())  // 버튼이 클릭된 현재 시간 가져와
                )

                MyApplication.db.collection("comments") // comments로 불리는 collection에 데이터 추가
                    .add(data)
                    .addOnSuccessListener { // 데이터 저장 성공했다면
                        Toast.makeText(this, "데이터 저장 성공.", Toast.LENGTH_LONG).show()
                        uploadImage(it.id)
                        finish()
                    }
                    .addOnFailureListener{ // 데이터 저장 실패했다면
                        Toast.makeText(this, "데이터 저장 실패 !.", Toast.LENGTH_LONG).show()
                    }
            }

            else{ // 만약 사용자가 edit text에 입력 안했다면
                Toast.makeText(this, "한줄평을 먼저 입력해주세요.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun uploadImage(docId : String){
        val imageRef = MyApplication.storage.reference.child("images/${docId}.jpg")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_LONG).show()
        }
    }
}