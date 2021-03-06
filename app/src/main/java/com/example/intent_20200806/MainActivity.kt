package com.example.intent_20200806

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQ_FOR_NICNAME = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 전화걸기 - 통화연결까지는 X
        dialBtn.setOnClickListener {
            val inputPhoneNum = phoneNumEdt.text.toString()
            // 어디에 전화를 걸지 Uri를 이용해서 정보 저장
            val myUri = Uri.parse("tel:${inputPhoneNum}")
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(myIntent)
        }

        // 전화걸기 - 통화연결까지는 O
        callBtn.setOnClickListener {
            val inputPhoneNum = phoneNumEdt.text.toString()
            val myUri = Uri.parse("tel:${inputPhoneNum}")
            val myIntent = Intent(Intent.ACTION_CALL, myUri)
            startActivity(myIntent)

        }

        // 문자발송 - 아직 내용은 없음
        smsBtn.setOnClickListener {
            val inputPhoneNum = phoneNumEdt.text.toString()
            val myUri = Uri.parse("smsto:${inputPhoneNum}")
            val myIntent = Intent(Intent.ACTION_SENDTO, myUri)

            // 문자 전송 화면 이동 시 미리 문구를 적어서 보내기
            // myIntent를 가지고 갈 때 -> putExtra로 데이터를 담아서 보내자
            myIntent.putExtra("sms_body", "이 앱을 다운로드 받아주세요.")

            startActivity(myIntent)
        }

        // 인터넷 웹 연결하기 네이버
        naverLinkBtn.setOnClickListener {

            val myUri = Uri.parse("https://www.naver.com")
            val myIntent = Intent(Intent.ACTION_VIEW, myUri)
            startActivity(myIntent)
        }

        // 플레이 스토어 연결
        kakaoPlayStoreBtn.setOnClickListener {

            val myUri = Uri.parse("market://details?id=com.kakao.talk&hl=ko")
            val myIntent = Intent(Intent.ACTION_VIEW, myUri)
            startActivity(myIntent)
        }


        moveToFirstBtn.setOnClickListener {

            //FirstActivity로 이동 -> Intent
            val myIntent = Intent(this, FirstActivity::class.java)
            startActivity(myIntent)
        }

        moveToSecondBtn.setOnClickListener {
            //SecondActivity로 => 입력한 내용을 들고 => 이동하기
            val inputMessage = messageEdt.text.toString()
            val myIntent = Intent(this, SecondActivity::class.java)

            //만들어준 Intent 변수에 데이터를 이름표를 붙인 상태로 첨부
            myIntent.putExtra("message", inputMessage)

            startActivity(myIntent)
        }

        changeNickNameBtn.setOnClickListener {

            // 닉네임 변경화면으로 이동 => 새 닉네임을 받으러 이동한다고 명시
            val myIntent = Intent(this, EditNickNameActivity::class.java)

            // 단순 이동 X, 결과를 받으러 이동 => 어떤 결과를 받으러 가는건지 명시.
            // => 고유 숫자를 임의로 지정해서 명시. => 멤버변수로 담아서 가독성 향상.
            startActivityForResult(myIntent, REQ_FOR_NICNAME)
        }
    }

    // 결과를 받아올 때 실행되는 함수 오버라이딩
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // requestCode를 우선검사 -> 뭘가지러 다녀왔는지 확인
        // 닉네임이 맞는지? -> requestCode의 값이 1000인지?
        if(requestCode == REQ_FOR_NICNAME) {
            // 닉네임 변경은 -> 확인을 눌렀을 때만 하고싶다.
            // 확인이 눌린게 맞는지 -> resultCode의 값이 RESULT_OK인지?

            if(resultCode == Activity.RESULT_OK){

                // 돌아올 때 들고온 새 닉네임 text반영
                // data 인텐트가 -> resultIntent를 들고 있다. -> 거기(data)서 String을 뽑아내자.
                val newNickname = data?.getStringExtra("newNickName")

                nickNameTxt.text = newNickname
            }

        }
    }
}