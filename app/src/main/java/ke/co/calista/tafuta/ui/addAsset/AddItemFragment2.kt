package ke.co.calista.tafuta.ui.addAsset

import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.ui.main.MainViewModel
import kotlinx.android.synthetic.main.add_item_fragment2.*


class AddItemFragment2 : Fragment(),BlockingStep {
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
        fun newInstance() = AddItemFragment2()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(ke.co.calista.tafuta.R.layout.add_item_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


    }

    fun addView() {

        // Add TextInputLayout to parent layout first
        val textInputLayout = TextInputLayout(context)
        val textInputLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 100f
        )


        textInputLayout.layoutParams = textInputLayoutParams
        textInputLayout.setHintTextAppearance(ke.co.calista.tafuta.R.style.TextSizeHint)
        linear_parent.addView(textInputLayout)

        // Add EditText control to TextInputLayout
        val editText = EditText(context)
        val editTextParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editTextParams.setMargins(0, 10, 0, 0)
        editText.layoutParams = editTextParams

        editText.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(ke.co.calista.tafuta.R.dimen.text_size)
        )
        editText.hint = "Enter value"
        editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        editText.isEnabled = false

        textInputLayout.addView(editText, editTextParams)

    }


}
