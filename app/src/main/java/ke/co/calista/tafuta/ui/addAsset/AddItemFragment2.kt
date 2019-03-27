package ke.co.calista.tafuta.ui.addAsset

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.R
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
        return inflater.inflate(R.layout.add_item_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

    }

}
