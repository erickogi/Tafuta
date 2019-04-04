package ke.co.calista.tafuta.ui.addAsset

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.google.gson.Gson
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.AssetDepartment
import ke.co.calista.tafuta.model.asset.AssetTypes
import ke.co.calista.tafuta.model.asset.NewAsset
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.main.MainViewModel
import ke.co.calista.tafuta.utils.Validator
import kotlinx.android.synthetic.main.add_item_fragment1.*

class AddItemFragment1 : Fragment(), BlockingStep, AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


        val assetType: AssetTypes? = filterss?.get(p2)



        departments = assetType?.assetDepartments
        selectedType = assetType


        val array = departments?.size?.let { it1 -> arrayOfNulls<String>(it1) }
        var array_adapter: ArrayAdapter<String?>? = null


        if (array != null && departments != null && (departments as ArrayList<AssetDepartment>).size > 0) {

            for (a in 0 until (departments as ArrayList<AssetDepartment>).size) {

                array.set(a, (departments as ArrayList<AssetDepartment>)[a].names)

            }
            Log.d("adasa", Gson().toJson(array))
            array_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, array)

            array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            departmentName.adapter = array_adapter

            try {
                if (departments != null) {
                    selectedDepartment = departments!![0]
                }
            } catch (xc: Exception) {

            }


        } else {

            array_adapter?.clear()
            departmentName.adapter = array_adapter


        }


    }


    var loginData: LoginData? = null
    var filterss: ArrayList<AssetTypes>? = null
    var departments: ArrayList<AssetDepartment>? = null


    var selectedType: AssetTypes? = null
    var selectedDepartment: AssetDepartment? = null


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


        (activity as AddAssetActivity).asset = NewAsset(
            names = asset_name.text.toString(),
            scanCode = code.text.toString(), sNo = asset_serial.text.toString(),
            stateCondition = asset_condition.text.toString(),
            description = asset_description.text.toString(),
            typeId = selectedType?.id, departmentId = selectedDepartment?.id,
            relatedData = selectedDepartment?.relatedData, createdById = loginData?.id, depreciationRate = ""
        )

        callback?.goToNextStep()


    }


    override fun verifyStep(): VerificationError? {

        return if (Validator.isValidName(asset_name, true, 5)
            && Validator.isValidName(code, true, 6)
            && Validator.isValidName(asset_description, true, 6)
            && Validator.isValidName(asset_condition, true, 6)
            && Validator.isValidName(asset_serial, true, 5)
            && isSelected(typeName) && isSelected(departmentName)
        ) {
            null

        } else {
            VerificationError("Some fields are missing")
        }
    }

    private fun isSelected(spinner: Spinner?): Boolean {

        return true
    }

    override fun onError(error: VerificationError) {

    }

    companion object {
        fun newInstance() = AddItemFragment1()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.add_item_fragment1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        code.setOnClickListener { scan() }
        loginData = viewModel.getProfilae()

        if (loginData?.filters != null) {
            filterss = loginData?.filters

            val array = filterss?.size?.let { it1 -> arrayOfNulls<String>(it1) }

            try {
                if (filterss != null && (filterss as ArrayList<AssetTypes>).size > 0) {

                    for (a in 0..(filterss as ArrayList<AssetTypes>).size - 1) {
                        array?.set(a, (filterss as ArrayList<AssetTypes>)[a].names)

                    }
                    val array_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, array)
                    array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    typeName.adapter = array_adapter


                }
            } catch (e: Exception) {

            }


        }


        typeName.onItemSelectedListener = this
        departmentName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                try {
                    selectedDepartment = departments?.get(p2)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


//        viewModel.getProfile().observe(this, Observer {
//
//            var filterss: ArrayList<AssetTypes>? = null
//            if(it?.filters != null){
//                filterss=it.filters
//
//                val array = filterss?.size?.let { it1 -> arrayOfNulls<String>(it1) }
//                if (filterss != null&&filterss.size>0) {
//                    for( a in 0..filterss.size){
//
//                        array?.set(a, filterss[a]?.names)
//
//                    }
//                    val array_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, array)
//                    array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    typeName.adapter=array_adapter
//
//                }
//
//
//            }
//        })


    }

    fun scan() {
        if (checkPerrmission()) {
            startScan()
        } else {
            requestPermissions()
        }
    }

    private fun checkPerrmission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PermissionChecker.checkSelfPermission(
            context!!,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(activity as AddAssetActivity, arrayOf(Manifest.permission.CAMERA), 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan()
            } else {
                permisionDeneied()
            }


        }
    }


    private fun permisionDeneied() {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setTitle("Permission Denied")

        dialog.show()

    }

    private fun startScan() {

        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
            .withActivity(activity as AddAssetActivity)
            .withBackfacingCamera()
            .withCenterTracker()
            .withOnly2DScanning()
            .withBarcodeFormats(Barcode.AZTEC or Barcode.EAN_13 or Barcode.CODE_93)

            .withEnableAutoFocus(true)
            .withBleepEnabled(true)
            .withCenterTracker()
            // .withBarcodeFormats( Barcode.ALL_FORMATS)
            .withBackfacingCamera()
            .withText("Scanning...")
            .withResultListener { barcode ->
                code.setText("" + barcode.rawValue)
            }
            .build()
        materialBarcodeScanner.startScan()

    }

}
