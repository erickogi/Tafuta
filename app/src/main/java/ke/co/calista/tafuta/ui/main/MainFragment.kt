package ke.co.calista.tafuta.ui.main

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.snackbar.Snackbar
import ke.co.calista.tafuta.ui.AddAssetActivity
import ke.co.calista.tafuta.ui.MainActivity
import ke.co.calista.tafuta.R

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


        add.setOnClickListener { addNew() }
        scan.setOnClickListener { scan() }
        list.setOnClickListener { list() }
    }

    private fun scan() {
        if (checkPerrmission()) {
            startScan()
        } else {
            requestPermissions()
        }
    }
    private fun list(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, AssetListFragment.newInstance())
            ?.commitNow()
    }
    private fun startScan() {

        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
            .withActivity(activity as MainActivity)
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

               // view?.let { Snackbar.make(it, ""+barcode, Snackbar.LENGTH_LONG).setAction("Re-Scan") { startScan() }.show() }


                loadAsset()

            }
            .build()
        materialBarcodeScanner.startScan()

    }

    fun loadAsset(){
       // childFragmentManager.beginTransaction()
           // .replace(R.id.container, AssetFragment.newInstance())
          //  .commitNow()
        viewModel.loadAsset()
    }


    private fun addNew(){

        val intent = Intent(activity, AddAssetActivity::class.java)
        startActivity(intent)
//        activity!!.supportFragmentManager.beginTransaction().add(R.id.container,
//            AddItemFragment1()
//        ).addToBackStack("signIn").commit()
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
