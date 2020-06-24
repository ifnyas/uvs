package co.wangun.uvs.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.wangun.uvs.R
import co.wangun.uvs.api.ApiClient.client
import co.wangun.uvs.api.ApiService
import co.wangun.uvs.utils.SessionManager
import kotlinx.android.synthetic.main.activity_result.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {

    private var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // init values
        sessionManager = SessionManager(this)
        val img = sessionManager?.getPath() + "/recognize.jpg"
        val date = sessionManager?.getDate()
        val time = sessionManager?.getTime()
        val target = sessionManager?.getTarget()

        // init view
        nameView.text = sessionManager?.getName()
        dateView.text = "Visit Date: $date ($time)"
        statusView.text = "$target will be notified, Thank you"
        locView.text = "Registered from:\n${sessionManager?.getLoc("full")}"
        imgView.setImageBitmap(BitmapFactory.decodeFile(img))

        // init button
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        scanView.setOnClickListener {
            val intent = Intent(this, FaceActivity::class.java)
            intent.putExtra("from", "recognize")
            startActivity(intent)
            finish()
        }

        // init post
        sendResult()
    }

    private fun sendResult() {

        // init API Service
        val mApiService = client.create(ApiService::class.java)

        // init values
        val name = sessionManager?.getName()
        val phone = sessionManager?.getPhone()
        val target = sessionManager?.getTarget()
        val date = sessionManager?.getDate()
        val time = sessionManager?.getTime()
        val msg = "Good Afternoon $target, there is a guest named $name want to make " +
                "a visit at $date, $time o'clock. Thank you."

        // send message
        mApiService.sendMessage(getString(R.string.wablas), phone!!, msg)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        try {
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(c: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
