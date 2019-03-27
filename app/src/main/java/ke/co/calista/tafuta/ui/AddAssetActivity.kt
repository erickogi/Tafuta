package ke.co.calista.tafuta.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.adapters.AddAssetStepsAdapter
import kotlinx.android.synthetic.main.add_asset.*

class AddAssetActivity : AppCompatActivity(), StepperLayout.StepperListener {
    override fun onStepSelected(newStepPosition: Int) {

    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }

    override fun onCompleted(completeButton: View?) {
        finish()
    }

    private var mStepperAdapter: AddAssetStepsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_asset)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()


        mStepperAdapter = AddAssetStepsAdapter(supportFragmentManager, this)
        stepperLayout.adapter = mStepperAdapter!!
        stepperLayout.setListener(this)
        stepperLayout.setOffscreenPageLimit(1)


    }
    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }
}
