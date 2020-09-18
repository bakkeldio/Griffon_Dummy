package com.example.griffon_dummy.resetPassword.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_update_password.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.regex.Pattern


class UpdatePassword : Fragment(), Contract.View {
    private val args : UpdatePasswordArgs by navArgs()

    val presenter : Contract.Presenter by inject{ parametersOf(this)}



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDesign()
        updateButton.setOnClickListener {
            when {
                updatePassword.text.toString().isEmpty() -> textInputLayout3.error = "Enter new password"
                (!isValidPasswordFormat(updatePassword.text.toString()) && updatePassword.text!!.isNotEmpty()) -> textInputLayout3.error = "Password must contain at least: 1 digit," +
                        "1 uppercase letter"
                (updatePassword.text.toString() != updateConfirmPassword.text.toString()) && (isValidPasswordFormat(updatePassword.text.toString()) &&
                        isValidPasswordFormat(updateConfirmPassword.text.toString())) -> textInputLayput4.error = "Passwords must be matched"
                updateConfirmPassword.text.toString().isEmpty() -> textInputLayput4.error = "Enter password"

                updateConfirmPassword.text.toString().isNotEmpty() && !isValidPasswordFormat(updateConfirmPassword.text.toString()) -> textInputLayput4.error = "Provide valid password"

            }
            if ((isValidPasswordFormat(updatePassword.text.toString()) && isValidPasswordFormat(updateConfirmPassword.text.toString()))
                && (updatePassword.text.toString() == updateConfirmPassword.text.toString())
            ){
                presenter.getUpdatePasswordConfirm(args.sid, updatePassword.text.toString())
            }

        }

        updatePassword.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                if (!isValidPasswordFormat((v as EditText).text.toString())){
                    textInputLayout3.error = "Password must contain at least: 1 digit,1 upperCase letter and 8 characters"
                }
                else{
                    textInputLayout3.error = null
                }
            }
        }
        updateConfirmPassword.onFocusChangeListener = View.OnFocusChangeListener{ v, hasFocus ->
            if (!hasFocus) {
                if (!isValidPasswordFormat((v as EditText).text.toString())) {
                    textInputLayput4.error = "Password must contain at least: 1 digit,1 upperCase and 8 characters"
                } else {
                    textInputLayput4.error = null
                }
            }

        }


    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$")
        return passwordREGEX.matcher(password).matches()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateButton(colors: IntArray) {
        updateButton.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }

    override fun updateLogo(url: String) {
        Glide.with(requireContext())
            .load(url)
            .into(logo_update)
    }

    override fun getSuccessMessage(message :String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        findNavController().navigate(UpdatePasswordDirections.toSignIn())
    }


}
