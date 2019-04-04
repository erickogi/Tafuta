package ke.co.calista.tafuta.ui.main

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.models.custom.Status
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.DashboardResponse
import ke.co.calista.tafuta.model.oauth.LoginData
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.MainActivity
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val a: LoginData = viewModel.getProfilae()
        uId.text = a.names + " (" + a.email + ")"
        add.setOnClickListener { addNew() }
        scan.setOnClickListener { scan() }
        list.setOnClickListener { list() }

        viewModel.observeDashboard().observe(this, Observer {
            setStatus(it.message, it.status)
            setData(it.data)
        })
        viewModel.getDashboard()
    }

    private fun setData(data: DashboardResponse?) {
        allAssets.text = data?.data?.stats?.allAssetsCount
        allActive.text = data?.data?.stats?.activeAssetsCount
        allUnassigned.text = data?.data?.stats?.unAssignedAssetsCount

    }

    private fun setStatus(message: String?, status: Status) {

        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.smoothToHide()
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (message != null) {
                view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
            }
        }


    }


    private fun scan() {
        if (checkPerrmission()) {
            startScan()
        } else {
            requestPermissions()
        }
    }
    private fun list(){
        activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, AssetListFragment.newInstance())
            ?.addToBackStack("assetlihj")?.commit()

    }
    private fun startScan() {

        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
            .withActivity(activity as MainActivity)
            .withBackfacingCamera()
            .withCenterTracker()
            //  .withOnly2DScanning()
            .withBarcodeFormats(Barcode.AZTEC or Barcode.EAN_13 or Barcode.CODE_93)
            .withEnableAutoFocus(true)
            .withBleepEnabled(true)
            .withCenterTracker()
            // .withBarcodeFormats( Barcode.ALL_FORMATS)
            .withBackfacingCamera()
            .withText("Scanning...")
            .withResultListener { barcode ->


                loadAsset(barcode.rawValue)

            }
            .build()
        materialBarcodeScanner.startScan()

    }

    fun loadAsset(rawValue: String) {
       // childFragmentManager.beginTransaction()
           // .replace(R.id.container, AssetFragment.newInstance())
          //  .commitNow()
        viewModel.loadAsset(rawValue)
        viewModel.observeAsset().observe(this, Observer {
            setStatus(it.message, it.status)
            if (it.status == Status.SUCCESS) {
                if (it.data != null) {

                    it.data.asset?.let { it1 -> AssetFragment.newInstance(it1) }?.let { it2 ->
                        activity?.supportFragmentManager?.beginTransaction()?.add(
                            R.id.container,
                            it2
                        )?.addToBackStack("")?.commit()


                    }

                }
            }
        })
    }


    private fun addNew(){

        val intent = Intent(activity, AddAssetActivity::class.java)
        startActivity(intent)

    }
    private fun checkPerrmission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(context!!,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.CAMERA), 100)
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
}
