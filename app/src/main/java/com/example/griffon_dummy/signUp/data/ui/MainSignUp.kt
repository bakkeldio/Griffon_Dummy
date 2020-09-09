package com.example.griffon_dummy.signUp.data.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_main_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MainSignUp : Fragment(), ContractView2.View {

    private val presenter: ContractView2.Presenter by inject { parametersOf(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getClientInfo()


        createAccount.setOnClickListener {
            if (firstPassword.text.isNullOrEmpty()) {
                firstPassword.error = "Provide password"
            }
            else{


                if (firstPassword.text.toString() == secondPassword.text.toString()) {


                    if (arguments != null && secondPassword.text.toString().isNotEmpty()) {


                        if (arguments?.get("email") != null) {
                            presenter.getAccessToken(
                                arguments?.getString("email")!!,
                                secondPassword.text.toString()
                            )
                        }
                        else{
                            presenter.getAccessTokenWithPhone(requireArguments().getString("sid")!!, secondPassword.text.toString())
                        }
                    }

                }
                else{
                    firstPassword.error = "Please confirm the new password!"

                }
            }
    }
    }

    override fun updateButton(intArray: IntArray) {
        createAccount.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, intArray)
    }

    override fun updateImage(url: String) {
        Glide.with(requireContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(LOGO)
    }

    override fun getAccessToken(accessToken: String) {
        findNavController().navigate(MainSignUpDirections.toSignIn())
    }
}
