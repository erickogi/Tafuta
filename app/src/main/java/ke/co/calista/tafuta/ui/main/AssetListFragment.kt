package ke.co.calista.tafuta.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import com.google.android.material.snackbar.Snackbar
import com.kogicodes.sokoni.adapters.V1.AssetsAdapter
import com.kogicodes.sokoni.models.custom.Status
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import ir.mirrajabi.searchdialog.core.Searchable
import ke.co.calista.tafuta.R
import ke.co.calista.tafuta.model.asset.*
import kotlinx.android.synthetic.main.assetlist_fragment.*


class AssetListFragment : Fragment() {

    companion object {
        fun newInstance() = AssetListFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var assetsAdapter: AssetsAdapter
    private lateinit var assetResponse: AssetsResponse

    private lateinit var userResponse: UsersResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.assetlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initView()
        init()
        observers()


    }
    private fun setUpAssets(data: AssetsResponse?) {
        assetResponse = data!!
        assetResponse.let { it.assets?.let { it1 -> assetsAdapter.refresh(it1) } }

    }

    private fun initView() {
        assetResponse = AssetsResponse()
        if(assetResponse.assets==null) {
            assetResponse.assets=ArrayList()
        }
            assetsAdapter = context?.let {
                activity?.let { it1 ->

                    AssetsAdapter(assetResponse.assets as ArrayList<Asset>, object : OnRecyclerViewItemClick {
                        override fun onClickListener(position: Int) {
                            activity?.supportFragmentManager?.beginTransaction()?.replace(
                                R.id.container,
                                AssetFragment.newInstance((assetResponse.assets as ArrayList<Asset>).get(position))
                            )?.addToBackStack("assedtDetails")?.commit()
                        }

                        override fun onLongClickListener(position: Int) {


                            onLongClick(position)

                        }
                    })
                }
            }!!
            recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recycler_view.itemAnimator = DefaultItemAnimator()
            recycler_view.adapter = assetsAdapter
            assetsAdapter.notifyDataSetChanged()





        refresh.setOnClickListener { init() }


    }

    private fun onLongClick(position: Int) {
        viewModel.getUsers("1000", "1").observe(this, Observer {

            setStatus(it.message, it.status)
            if (it.status == Status.SUCCESS) {

                userResponse = it.data as UsersResponse
                val searchModels = java.util.ArrayList<SampleSearchModel>()
                if (userResponse.data != null)
                    for (users in userResponse.data as ArrayList<User>) {
                        searchModels.add(SampleSearchModel(users.names))
                    }

                val s = SimpleSearchDialogCompat(context, "Search", "Here", null, searchModels,
                    SearchResultListener<SampleSearchModel> { p0, p1, p2 ->


                        var user: User = userResponse.data?.get(p2) as User
                        // p0.dismiss()
                        p0.dismiss()

                        assign(user, (assetResponse.assets as ArrayList<Asset>).get(position), p0)

                    })


                s.setCancelable(true)
                s.setCanceledOnTouchOutside(true)
                s.show()
            }
        })
    }

    private fun assign(
        user: User,
        asset: Asset,
        p0: BaseSearchDialogCompat<Searchable>
    ) {


        asset.id?.let {
            user.id?.let { it1 ->
                viewModel.assign(it, "2", it1, "Assinged to " + user.names).observe(this,
                    Observer {
                        setStatus(it.message, it.status)


                        //Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                        if (it.status == Status.SUCCESS) {
                            it.message?.let { it2 ->
                                view?.let { it3 ->
                                    Snackbar.make(it3, it2, Snackbar.LENGTH_LONG).show()
                                }
                            }

                        }

                    })
            }
        }
    }

    private fun setStatus(message: String?, status: Status) {
        main.visibility = View.GONE
        main.visibility = View.VISIBLE


        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            avi.smoothToShow()
            refresh.visibility = View.GONE


            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.smoothToHide()
            avi.visibility = View.GONE
            refresh.visibility = View.VISIBLE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (message != null) {
            //    empty_text.text = data.message
                refresh.visibility = View.VISIBLE

                view?.let { Snackbar.make(it, message.toString(), Snackbar.LENGTH_LONG).show() }
            }

           // empty_layout.visibility = View.VISIBLE
           // main.visibility = View.GONE
           // empty_button.setOnClickListener({ init() })


        }


    }

    fun init() {


        viewModel.getAssets("1000", "1")


    }

    fun observers() {
        viewModel.observeAssets().observe(this, androidx.lifecycle.Observer {
            setStatus(it.message, it.status)

            if (it.status == Status.SUCCESS) {
                setUpAssets(it.data)
            }

        })


    }


}
