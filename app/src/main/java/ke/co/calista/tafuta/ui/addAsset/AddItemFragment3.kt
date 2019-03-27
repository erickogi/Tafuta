package ke.co.calista.tafuta.ui.addAsset

import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.ui.main.MainViewModel
import ke.co.calista.tafuta.utils.Validator.Companion.isValidPhoneNumber
import kotlinx.android.synthetic.main.add_item_fragment3.*
import kotlinx.android.synthetic.main.custom_field.*
import kotlinx.android.synthetic.main.custom_field.view.*
import kotlinx.android.synthetic.main.toolbar.*


class AddItemFragment3 : Fragment(),BlockingStep {
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()

    }

    override fun onSelected() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity?.actionBar?.hide()
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.complete()

    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {

    }


    companion object {
        fun newInstance() = AddItemFragment3()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.add_item_fragment3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        addCustomField.setOnClickListener {newField()}
    }

    private fun newField() {

        startDialog("","")

    }

    private fun startDialog(labl: String, data: String) {

        val layoutInflaterAndroid = LayoutInflater.from(context)
        val mView = layoutInflaterAndroid.inflate(R.layout.custom_field, null)

        val alertDialogBuilderUserInput = AlertDialog.Builder(context!!)
        alertDialogBuilderUserInput.setView(mView)
        alertDialogBuilderUserInput.setTitle("Create a custom field")


        alertDialogBuilderUserInput
            .setCancelable(false)
            .setPositiveButton("Okay") { dialogBox, id ->
                // ToDo get user input here


            }

            .setNegativeButton("Dismiss"
            ) { dialogBox, id -> dialogBox.cancel() }

        val alertDialogAndroid = alertDialogBuilderUserInput.create()
        alertDialogAndroid.show()

        mView.label.setText(labl)
        mView.value.setText(data)

        val thePositive = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE)
        thePositive.visibility = View.VISIBLE
        val theNegative = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE)
        theNegative.visibility = View.VISIBLE


        thePositive.setOnClickListener(CustomListener(alertDialogAndroid, thePositive))
    }

    internal inner class CustomListener(private val dialog: Dialog, private val button: Button) : View.OnClickListener {

        override fun onClick(v: View) {




            if (TextUtils.isEmpty(dialog.label.text.toString())) {
                dialog.label.requestFocus()
                dialog.label.error=("Valid Label Required")
                return
            }
            if (TextUtils.isEmpty(dialog.value.text.toString())) {
                dialog.value.requestFocus()
                dialog.value.error=("Valid Value Required")
                return
            }

            else {


                dialog.dismiss()
            }



        }

    }


}
