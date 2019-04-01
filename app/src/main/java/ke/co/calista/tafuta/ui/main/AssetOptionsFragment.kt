package ke.co.calista.tafuta.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.snackbar.Snackbar

import com.kogicodes.sokoni.models.custom.Resource
import com.kogicodes.sokoni.models.custom.Status
import ke.co.calista.tafuta.R
import kotlinx.android.synthetic.main.main_fragment.*


class AssetOptionsFragment : RoundedBottomSheetDialogFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_asset_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}