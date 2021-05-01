package com.example.griffon_dummy.signUp.data.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_main_sign_up.*
import kotlinx.android.synthetic.main.fragment_update_password.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.regex.Pattern


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
            when {
                firstPassword.text.toString().isEmpty() -> passwordLast.error = "Enter new password"
                (!isValidPasswordFormat(firstPassword.text.toString()) && firstPassword.text!!.isNotEmpty()) -> passwordLast.error = "Password must contain at least: 1 digit," +
                        "1 uppercase letter"
                (firstPassword.text.toString() != secondPassword.text.toString()) && (isValidPasswordFormat(firstPassword.text.toString()) &&
                        isValidPasswordFormat(secondPassword.text.toString())) -> confirm.error = "Passwords must be matched"
                secondPassword.text.toString().isEmpty() -> confirm.error = "Enter password"

                secondPassword.text.toString().isNotEmpty() && !isValidPasswordFormat(secondPassword.text.toString()) -> confirm.error = "Provide valid password"

            }
            if ((isValidPasswordFormat(firstPassword.text.toString()) && isValidPasswordFormat(secondPassword.text.toString()))
                && (firstPassword.text.toString() == secondPassword.text.toString())
            ){
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

    }

        firstPassword.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                if (!isValidPasswordFormat((v as EditText).text.toString())){
                    passwordLast.error = "Password must contain at least: 1 digit,1 upperCase letter and 8 characters"
                }
                else{
                    passwordLast.error = null
                }
            }
        }
        secondPassword.onFocusChangeListener = View.OnFocusChangeListener{ v, hasFocus ->
            if (!hasFocus) {
                if (!isValidPasswordFormat((v as EditText).text.toString())) {
                    confirm.error = "Password must contain at least: 1 digit,1 upperCase and 8 characters"
                } else {
                     confirm.error = null
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

    override fun getAccessTokenForEmail() {
        findNavController().navigate(MainSignUpDirections.toSignIn())
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
}
