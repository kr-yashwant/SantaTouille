package `in`.nfn8y.santatouille

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    private val tag: String = "LoginActivity"
    private val requestSignUp: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener { login() }
        link_signup.setOnClickListener({
            var intent: Intent = Intent(applicationContext, SignupActivity::class.java)
            startActivityForResult(intent, requestSignUp)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })
    }

    private fun login() {
        Log.d(tag, "Login")
        if(!validate()) {
            onLoginFailure()
            return
        }
        btn_login.isEnabled = false

        var progressDialog :ProgressDialog = ProgressDialog(this, R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage(getString(`in`.nfn8y.santatouille.R.string.progress_dialog_authenticating_message))
        progressDialog.show()

        // TODO AUTHENTICATION LOGIC

        Handler().postDelayed(Runnable {
            onLoginSuccess()
            progressDialog.dismiss()
        }, 3000)
    }

    private fun onLoginSuccess() {
        btn_login.isEnabled = true
        finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_LONG).show()
        btn_login.isEnabled = false
    }

    private fun validate() : Boolean {
        var valid: Boolean = true
        var email: String = input_email.text.toString()
        var password: String = input_password.text.toString()

        if(email.isEmpty() || !EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = getString(`in`.nfn8y.santatouille.R.string.invalid_email_address_error)
            valid = false
        } else {
            input_email.error = null
        }

        if(password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password.error = getString(`in`.nfn8y.santatouille.R.string.invalid_password_error)
            valid = false
        } else {
            input_password.error = null
        }

        return valid
    }
}