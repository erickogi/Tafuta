package ke.co.calista.tafuta.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.custom.Status
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import ke.co.calista.tafuta.ui.MainActivity
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.storage.PrefrenceManager
import ke.co.calista.tafuta.utils.Validator
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel

        backtoauth.setOnClickListener({goBackToAuth()})
        signin.setOnClickListener({ signIn() })
        signinback.setOnClickListener({goBackToAuth()})
        forgotPassword.setOnClickListener({forgotPassword()})

        viewModel.observeSignIn().observe(this, Observer { data->
            run {
                setStatus(data)
                if(data.status== Status.SUCCESS&&data.data!=null){
                    context?.let { PrefrenceManager(it).setLoginStatus(1) }
                    viewModel.saveProfile(data.data)
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()

                }


            }
        })
    }

    private fun setStatus(data: Resource<Oauth>) {

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

    fun goBackToAuth(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, AuthFragment()).commit()

    }

    fun forgotPassword(){
        activity!!.supportFragmentManager.beginTransaction().add(R.id.container, ForgotPasswordFragment()).addToBackStack("forgotPassword").commit()

    }

    fun signIn(){
        if(validate()) {
            viewModel.signIn(getParameters())
        }

    }

    private fun validate(): Boolean {

        if(!Validator.isValidEmail(email)){
            return false
        }
        if(!Validator.isValidPassword(password)){
            return false
        }

        return true
    }

    private fun getParameters(): Oauth {

        return Oauth(Profile(email.text.toString(), password.text.toString()))


    }


}
