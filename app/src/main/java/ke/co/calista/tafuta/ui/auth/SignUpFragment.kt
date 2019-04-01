package ke.co.calista.tafuta.ui.auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.custom.Status
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.utils.Validator
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        signup.setOnClickListener {signUp()}
        backtoauth.setOnClickListener {backToAauth()}
        signupback.setOnClickListener {backToAauth()}

        viewModel.observeSignUp().observe(this, Observer { data->
            run {
                setStatus(data)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, SignInFragment()).commit()

                }


            }
        })
        val space = ' '

        mobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(s: Editable?) {


                if (s!!.length > 0 && s.length % 4 == 0) {
                    val c = s[s.length - 1]
                    if (space == c) {
                        s.delete(s.length - 1, s.length)
                    }

                }
                if (s.length > 0 && s.length % 4 == 0) {
                    val c = s[s.length - 1]
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3) {
                        s.insert(s.length - 1, space.toString())
                    }

                }
            }
        })

    }

    private fun setStatus(data: Resource<LoginData>) {

        val status: Status =data.status

        if(status== Status.LOADING){
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }else if(status== Status.ERROR||status== Status.SUCCESS){
            avi.smoothToHide()
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if(status== Status.ERROR){
            if(data.message!=null) {
                view?.let { Snackbar.make(it,data.message, Snackbar.LENGTH_LONG).show() }
            }
        }


    }


    fun backToAauth(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()

    }
    fun signUp(){

        if(validate()){
            viewModel.signUp(getParameters())
        }
    }

    private fun validate(): Boolean {
        if(!Validator.isValidName(firstName)){
            return false
        }

        if(!Validator.isValidName(lastName)){
           // return false
        }

        if(!Validator.isValidEmail(email)){
            return false
        }
        if(!Validator.isValidPassword(password)){
            return false
        }
        when {
            !TextUtils.isEmpty(mobile.text) -> if (!Validator.isValidPhoneNumber(mobile.text.toString().replace(" ", "").trim())) {
               // return false
            }
            //else -> return false
        }


        return true
    }

    private fun getParameters(): Oauth {

        return Oauth(Profile(firstName.text.toString(), email.text.toString(), password.text.toString()))

    }

}
