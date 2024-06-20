package com.example.joyceapplication

data class myJsonItems(val districtName:String, val issueDate:String, val issueTime:String, val issueGbn:String)


data class myJsonRBody(val items : MutableList<myJsonItems>)


data class myJsonResponse(val body : myJsonRBody)
// 바디 안에는 4개의데이터가 있지만 다 사용하지 않아도 됨


data class JsonResponse(val response : myJsonResponse)
