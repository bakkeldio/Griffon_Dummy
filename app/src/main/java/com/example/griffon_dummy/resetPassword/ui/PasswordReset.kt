package com.example.griffon_dummy.resetPassword.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_password_reset.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PasswordReset : Fragment() , ContractView.View1{

    val presenter : ContractView.Presenter by inject { parametersOf(this) }

    lateinit var option : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_reset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSP()



            resetNext.setOnClickListener {
                if (username.text!!.isNotEmpty()) {

                    if (username.text.toString().isValidEmail()) {
                        option = "email"
                        presenter.getOtpForUsername(username.text.toString(),option)
                    }
                    if (username.text.toString().isValidMobile()) {
                        option = "phone_number"
                        presenter.getOtpForUsername(username.text.toString(), option)
                    }
                }
                else{
                    username.error = "Provide username"
                }
            }
        }

    private fun String.isValidEmail(): Boolean =
        this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isValidMobile(): Boolean {
        return Patterns.PHONE.matcher(this).matches()
    }

    override fun getMessage(addInfo: String, sid : String) {
        Toast.makeText(requireContext(), username.text.toString(), Toast.LENGTH_LONG)
            .show()
        val action = PasswordResetDirections.toConfirmOTP(addInfo, username.text.toString(), option,sid)//if (username.text.toString().isValidMobile()) "phone_number" else "email")
      findNavController().navigate(action)
    }

    override fun updateImage(url: String) {
        Glide.with(requireContext())
            .load(url)
            .into(logoReset)
    }

    override fun onStop() {
        presenter.onDestroy()
        super.onStop()
    }

    override fun updateButton(colors: IntArray) {
        resetNext.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }
}
