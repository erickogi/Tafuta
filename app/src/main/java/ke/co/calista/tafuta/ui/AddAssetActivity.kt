package ke.co.calista.tafuta.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kogicodes.sokoni.models.custom.Status
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.adapters.AddAssetStepsAdapter
import ke.co.calista.tafuta.model.asset.NewAsset
import ke.co.calista.tafuta.ui.main.MainViewModel
import kotlinx.android.synthetic.main.add_asset.*

class AddAssetActivity : AppCompatActivity(), StepperLayout.StepperListener {
    var asset: NewAsset? = null
    private lateinit var viewModel: MainViewModel

    override fun onStepSelected(newStepPosition: Int) {

    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }

    override fun onCompleted(completeButton: View?) {

        viewModel.sendAsset(asset).observe(this, Observer {
            setStatus(it.message, it.status)
            if (it != null && it.status == Status.SUCCESS) {
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                finish()
            }
        })

    }

    private var mStepperAdapter: AddAssetStepsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_asset)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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

    private fun setStatus(message: String?, status: Status) {

        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.smoothToHide()
            avi.visibility = View.GONE
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }


    }

}
