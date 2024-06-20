package com.example.ch18_image2

import com.example.ch18_image2.NetworkService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=10&returnType=xml&serviceKey=서비스키(일반 인증키(Encoding))

// https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3?serviceKey=O3hl%2Fg6Dtviy%2BFkpasNjvSNu29%2FInnSiaEpLCXwtw8pSxQdlIiCoVyblcsV3zeckJK3lECAqcJrCE0YO7imieg%3D%3D&prdlstReportNo=201305230193&prdlstNm=%ED%95%B4%EB%82%A8%EC%97%90%EC%84%9C%EB%A7%8C%EB%93%A0%EB%B0%98%EC%8B%9C%EA%B3%A0%EA%B5%AC%EB%A7%88&returnType=xml&pageNo=1&numOfRows=10&prdkind=%EC%84%9C%EB%A5%98%EA%B0%80%EA%B3%B5%ED%92%88&manufacture=%ED%95%B4%EB%82%A8%EA%B3%A0%EA%B5%AC%EB%A7%88%EC%8B%9D%ED%92%88%EC%A3%BC%EC%8B%9D%ED%9A%8C%EC%82%AC&allergy=%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0,%20%EB%B0%80%20%ED%95%A8%EC%9C%A0


class RetrofitConnection{

    //객체를 하나만 생성하는 싱글턴 패턴을 적용합니다.
    companion object {
        //API 서버의 주소가 BASE_URL이 됩니다.

        // http://localhost/PHP_connection.php -> localhost가 아니라 ip 주소 필요
        private const val BASE_URL_Php = "http://10.207.17.22/" // ipconfig 이용해서 base_url_php 설정

        val phpNetworkService : NetworkService

        val phpRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_Php)
                .addConverterFactory(GsonConverterFactory.create())
                .build()




        private const val BASE_URL = "https://apis.data.go.kr/B553748/CertImgListServiceV3/"

        var xmlNetworkService : NetworkService

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{// 초기화
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)

            xmlNetworkService = xmlRetrofit.create(NetworkService::class.java)
        }
    }
}