package com.example.ch18_image2

import com.google.firebase.auth.FirebaseAuth
import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

// Dex : Dalvic Executable (64K)

// Executable라는 아이라는요~
// 코드로 작성해놓은 애들을 실행할 수 있는 파일로 만들어둔 것을 말함

// 안드로이드에서는 이 파일의 확장자를 dex라고 함
// dex는 최대 64K까지 가능함

// dex 하나 가지고는 안되고 여러개의 dex로 나누어서 실행파일을 만들어두는 것을 허용하겠다
// -> 멀티 덱스 어플리케이션

class MyApplication : MultiDexApplication() {

    companion object{
        lateinit var auth : FirebaseAuth

        // 이 auth를 통해서 현재 입력된 로그인 (이메일) 에 대한 string 을 하나 가지고 있을 필요가 있음

        var email:String? = null

        lateinit var db : FirebaseFirestore

        lateinit var storage : FirebaseStorage


        fun checkAuth():Boolean {
            var currentUser = auth.currentUser

            if(currentUser != null){ // 유효한 user라면
                // 그 유저의 이메일을 내가 로그인한 이메일의 유저로 받아들이겠다
                email = currentUser.email

                // 그리고 그 유저에 대한 verified 되어져 있다라는 상태로 반환하겠다
                return currentUser.isEmailVerified
            }
            return false
        }

    }
    override fun onCreate(){
        super.onCreate()

        auth = Firebase.auth

        db = FirebaseFirestore.getInstance() // 초기화

        storage = Firebase.storage // 초기화
    }

}