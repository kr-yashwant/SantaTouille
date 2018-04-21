package `in`.nfn8y.santatouille

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity: AppCompatActivity() {
    private val tag: String = "SignupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_signup.setOnClickListener({signup()})
    }

    private fun signup() {
        Log.d(tag, "Signup")

        if(!validate()) {
            onSignupFailed()
            return
        }

        btn_signup.isEnabled = false
        var progressDialog : ProgressDialog = ProgressDialog(this, R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage(getString(`in`.nfn8y.santatouille.R.string.progress_dialog_authenticating_message))
        progressDialog.show()

        Handler().postDelayed(Runnable {
            onSignupSuccess()
            progressDialog.dismiss()
        }, 3000)
    }

    fun onSignupSuccess() {
        btn_signup.isEnabled = true
        setResult(RESULT_OK, null)
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, getString(`in`.nfn8y.santatouille.R.string.error_login_failed), Toast.LENGTH_LONG).show()
        btn_signup.isEnabled = true
    }

    fun validate(): Boolean {
        var valid: Boolean = true

        var name:String = input_name.text.toString()
        var address:String = input_address.text.toString()
        var email:String = input_email.text.toString()
        var mobile:String = input_mobile.text.toString()
        var password:String = input_password.text.toString()
        var reEnterPassword:String = input_reEnterPassword.text.toString()

        if (name.isEmpty() || name.length < 3) {
            input_name.error = "at least 3 characters"
            valid = false
        } else {
            input_name.error = null
        }

        if (address.isEmpty()) {
            input_address.error = "Enter Valid Address"
            valid = false
        } else {
            input_address.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }

        if (mobile.isEmpty() || mobile.length != 10) {
            input_mobile.error = "Enter Valid Mobile Number"
            valid = false
        } else {
            input_mobile.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_password.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            input_reEnterPassword.error = "Password Do not match"
            valid = false
        } else {
            input_reEnterPassword.error = null
        }

        return valid
    }
}