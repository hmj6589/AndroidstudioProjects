package com.example.studentsapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.studentsapplication.databinding.ActivityMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val random = Random()
        val num = random.nextInt(5)


        // 애니메이션
        binding.btnTranslate.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.translate)
            binding.btnTranslate.startAnimation(anim)
        }
        binding.btnScale.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.scale)
            binding.btnScale.startAnimation(anim)
        }
        binding.btnRotate.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
            binding.btnRotate.startAnimation(anim)
        }
        binding.btnAlpha.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
            binding.btnAlpha.startAnimation(anim)
        }
        binding.btnWave.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.wave)
            binding.btnWave.startAnimation(anim)
        }

        var rocketAnimation : AnimationDrawable

        val rocketImage = binding.rocketImage.apply {
            // rocket.xml을 넣겠다 (ImageView에 !)
            setBackgroundResource(R.drawable.rocket)

            rocketAnimation = background as AnimationDrawable
        }
        rocketAnimation.start() // 바로 실행


        // Youtube
        lifecycle.addObserver(binding.youtubePlayerView)

        // 유튜브 플레이어 리스너 설정
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            // onReady 메소드
            override fun onReady(youTubePlayer: YouTubePlayer) {

                super.onReady(youTubePlayer)

                val videoId : String
                if(num>3) videoId="Ec0Z1v7jKDQ"
                else videoId = "f-5BqxqvknE"

                // cueVideo 메소드를 통해서 동영상 실행시키도록 함
                youTubePlayer.cueVideo(videoId, 0f) // 또는 loadVideo(videoId, 0f)
            }
        })




        // 리턴 값이 있는 intent 처리해주는 역할을 함
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() ) {
            
            if (it.all { permission -> permission.value == true }) { 
                // 사용자가 퍼미션 줬다고 하면 알림 띄우는 것
                noti()
            }
            else {
                // 사용자가 퍼미션 안줬다고(사용자가 허용 X) 하면 알림 못띄운다고 하는 것
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.notificationButton.setOnClickListener {
            // 버전이 낮으면 걍 하면 되는데
            // 버전이 높으면 확인하고 처리해야함 
            
            // 티라미수 버전보다 높으면 이 퍼미션이 정말로 잘 주어졌는지 확인하는 작업
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti() 
                }
                else {
                    // 사용자한테 퍼미션 요청할고야 하는 것
                    permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                }
            }
            else {
                noti()
            }
        } // binding.notificationButton

    } // override fun onCreate

    fun noti(){ // 알림창을 띄우는 함수
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        
        // 알림을 만들 수 있는 빌더 만들기
        val builder: NotificationCompat.Builder
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     // 26 버전 이상에서는 빌더를 만드는 방법이 달라짐
            // 알림을 줄 때 채널이라는 개념이 생김
            // 채널을 다양하게 가짐
            // 각 채널 별로 알림을 보낼 수 있음 (한 번에 여러 개의 채널에서 알림을 보낼 수 있음)
            val channelId="one-channel"
            val channelName="My Channel One"

            val channel = NotificationChannel( // 채널 만들기
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {   // 채널에 다양한 정보 설정
                description = "My Channel One Description"

                setShowBadge(true)  // 앱 런처 아이콘 상단에 숫자 배지를 표시할지 여부를 지정 (상태바에 표시할지)

                // 에뮬레이터에서 소리를 제공하지 않아서 오디오 설정 안할거면 지우기
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                val audioAttributes = AudioAttributes.Builder() // 오디오가 어떠한 특성을 가지고 있는 애를 사용할 것인가
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // 오디오에 대한 내용
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

                setSound(uri, audioAttributes) // 소리를 넣을지
                enableVibration(true) // 진동
                // 여기까지 지우기
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {  // 26 버전 미만
            // 바로 (간단하게) 빌더를 만들 수 있음
            builder = NotificationCompat.Builder(this)
        }

        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.drawable.small) // 상태바에 뜨는 작은 아이콘
            setWhen(System.currentTimeMillis()) // 알림이 뜨는 시간 - 현재 시간
            setContentTitle("홍길동")
            setContentText("안녕하세요.")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big)) // 이미지도 올리기
        }

        manager.notify(11, builder.build()) // 지금까지 만들어둔 것 실행해라
    }
}