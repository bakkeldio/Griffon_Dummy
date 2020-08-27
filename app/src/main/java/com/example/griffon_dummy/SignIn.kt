package com.example.griffon_dummy

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.example.griffon_dummy.presenter.ClientContract
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignIn : Fragment(), ClientContract.View, KoinComponent {

    private val presenter: ClientContract.Presenter by inject { parametersOf(this) }


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        presenter.getClientInfo()
        return inflater.inflate(R.layout.fragment_sign_in, container, false)

    }


    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()

    }

    override fun loadLogo(url: String) {
        context?.let {
            GlideApp.with(it)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(logo)
        }

    }

    override fun updateBackground(color: String) {
        this.view?.setBackgroundColor(Color.parseColor(color))
    }

    override fun updateButton(colors: IntArray) {
        signIn.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instanceSignUp = SignUp()
        signUp.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, instanceSignUp)
                ?.addToBackStack(null)
                ?.commit()
        }

    }
}




