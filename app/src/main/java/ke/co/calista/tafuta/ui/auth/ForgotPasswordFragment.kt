package ke.co.calista.tafuta.ui.auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.utils.Validator
import kotlinx.android.synthetic.main.forgot_password.*

class ForgotPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        recoverPassword.setOnClickListener {forgotPassword()}
    }

    private fun forgotPassword(){
        if(validate()) {
            viewModel.recoverPassword(getParameters())
        }

    }

    private fun validate(): Boolean {

        if(!Validator.isValidEmail(email)){
            return false
        }


        return true
    }

    private fun getParameters(): Oauth {

        return Oauth(Profile(email.text.toString()))


    }



}
