package co.wangun.uvs.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import co.wangun.uvs.R
import co.wangun.uvs.utils.SessionManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val sessionManager = SessionManager(this)

        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (name.text.toString().isBlank()) {
                    nextBtn.setBackgroundColor(0xFF9E9E9E.toInt())
                } else {
                    nextBtn.setBackgroundColor(0xFF1165B3.toInt())
                }
            }
        })

        backBtn.setOnClickListener {
            onBackPressed()
        }

        nextBtn.setOnClickListener {
            sessionManager.putName(name.text.toString())
            val intent = Intent(this, FaceActivity::class.java)
            intent.putExtra("from", "register")
            startActivity(intent)
        }
    }
}
