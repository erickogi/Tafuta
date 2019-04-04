package ke.co.calista.tafuta.ui.addAsset

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.model.asset.NewAsset
import ke.co.calista.tafuta.model.asset.RelatedData
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.main.MainViewModel
import kotlinx.android.synthetic.main.add_item_fragment2.*


class AddItemFragment2 : Fragment(),BlockingStep {
    var lId = 0
    lateinit var array: Array<View?>

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        //

        linear_parent.removeAllViews()
        linear_parent.setBackgroundColor(resources.getColor(R.color.transparent))
        linear_parent.invalidate()
        callback?.goToPrevStep()
    }

    override fun onSelected() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity?.actionBar?.hide()

        linear_parent.removeAllViews()

        linear_parent.setBackgroundColor(resources.getColor(R.color.transparent))
        linear_parent.invalidate()

        asset = (activity as AddAssetActivity).asset

        lId = 0
        if (asset != null && asset?.relatedData != null) {
            array = arrayOfNulls((asset?.relatedData?.size) as Int)


            for (a in asset?.relatedData as ArrayList<RelatedData>) {
                a.inputType?.let { addView(a.label, a.value, it) }
            }
        }
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {

        var relatedList: ArrayList<RelatedData> = ArrayList()
        for (a in array) {

            var edt: EditText = (a as EditText)
            var relatedData = RelatedData(edt.hint.toString(), edt.text.toString(), edt.inputType)

            relatedList.add(relatedData)
        }

        asset?.relatedData = relatedList

        (activity as AddAssetActivity).asset?.relatedData = relatedList
        (activity as AddAssetActivity).asset = asset



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
    var asset: NewAsset? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(ke.co.calista.tafuta.R.layout.add_item_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


    }

    fun addView(label: String?, value: String?, inputType: Int) {


        val et = TextInputEditText(context)
        val p = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        p.setMargins(0, 10, 0, 0)
        et.layoutParams = p
        et.id = lId
        et.inputType = inputType
        et.setText(value)
        et.hint = label
        //et.id = numberOfLines + 1
        linear_parent.addView(et)

        array[lId] = et
        lId++
    }


}
