package co.wangun.uvs.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.wangun.uvs.R
import co.wangun.uvs.utils.SessionManager
import kotlinx.android.synthetic.main.activity_contact.*
import java.text.SimpleDateFormat
import java.util.*

class ContactActivity : AppCompatActivity() {

    var ready = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val sessionManager = SessionManager(this)

        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (name.text.toString().contains("ron", true)) {
                    borderless.visibility = View.VISIBLE
                    notFound.visibility = View.INVISIBLE
                } else if (name.text.toString().length > 3) {
                    borderless.visibility = View.INVISIBLE
                    colored.visibility = View.INVISIBLE
                    notFound.visibility = View.VISIBLE
                }
            }
        })

        var name: String

        notFound.setOnClickListener {
            val nameEdit = EditText(this)
            AlertDialog.Builder(this)
                .setMessage("What's his/her name?")
                .setView(nameEdit)
                .setPositiveButton("NEXT") { _, _ ->
                    name = nameEdit.text.toString()
                    if (name.isNotBlank()) {
                        sessionManager.putTarget(name)
                    } else {
                        sessionManager.putTarget("Mr. Anon")
                    }
                    getPhone()
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
                .show()
        }

        borderless.setOnClickListener {
            colored.visibility = View.VISIBLE
            borderless.visibility = View.INVISIBLE
            nextBtn.setBackgroundColor(0xFF1165B3.toInt())
            sessionManager.putTarget("Mr. Ronny A. Putra")
            sessionManager.putPhone("08112166695")
            ready = true
        }

        nextBtn.setOnClickListener {

            if (ready) {
                val cal = Calendar.getInstance()

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = SimpleDateFormat("d MMMM yyyy", Locale.US).format(cal.time)
                        sessionManager.putDate(date)
                        getTime()
                    }
                DatePickerDialog(
                    this,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun getPhone() {

        val sessionManager = SessionManager(this)
        var phone: String

        val phoneEdit = EditText(this)
        AlertDialog.Builder(this)
            .setMessage("What's his/her phone?")
            .setView(phoneEdit)
            .setPositiveButton("OK") { _, _ ->
                phone = phoneEdit.text.toString()
                if (phone.isNotBlank()) {
                    sessionManager.putPhone(phone)
                    notFound.text = "${sessionManager.getTarget()} ( ${sessionManager.getPhone()} )"
                    ready = true
                    nextBtn.setBackgroundColor(0xFF1165B3.toInt())
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
    }

    private fun getTime() {

        val sessionManager = SessionManager(this)

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val time = SimpleDateFormat("HH.mm", Locale.US).format(cal.time)
            sessionManager.putTime(time)
            nextIntent()
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun nextIntent() {

        val sessionManager = SessionManager(this)

        val date = sessionManager.getDate()
        val time = sessionManager.getTime()
        var target = sessionManager.getTarget()
        val phone = sessionManager.getPhone()
        if (phone != "08112166695") {
            target = "$target ( $phone )"
        }

        AlertDialog.Builder(this)
            .setMessage("You will visit $target\n\nDate: $date\nTime: $time")
            .setPositiveButton("CONFIRM") { _, _ ->
                startActivity(Intent(this, ResultActivity::class.java))
                finish()
            }
            .setNegativeButton("CANCEL") { _, _ -> }
            .create().show()
    }
}
