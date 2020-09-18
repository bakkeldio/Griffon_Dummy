package com.example.griffon_dummy.signIn.data.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.GlideApp
import com.example.griffon_dummy.MainActivity
import com.example.griffon_dummy.R
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class SignIn : Fragment(), ClientContract.View, KoinComponent {

    private val presenter: ClientContract.Presenter by inject { parametersOf(this) }




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

    override fun successfullySignIn(token : AccessToken) {

        val sharedPref = activity?.getSharedPreferences("sp",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("Token", Gson().toJson(token))
            putString("token", "${token.tokenType} ${token.accessToken} ")
            commit()
        }
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.popup)
        dialog.show()

        val intent = Intent(context, MainActivity::class.java )
        startActivity(intent)
    }

    override fun updateButton(colors: IntArray) {
        signIn.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUp.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("Time", Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {  remove("Time")
            remove("Timer")}.apply()
            findNavController().navigate(SignInDirections.toSignUp())
        }
        signIn.setOnClickListener {
            if (usernameSignIn.text!!.isNotEmpty() && passwordSignIn.text!!.isNotEmpty()){
                presenter.getAccessToken(usernameSignIn.text.toString(), passwordSignIn.text.toString())
            }
            else{
                if (usernameSignIn.text.isNullOrEmpty()){
                    emailSignIn.error = "Provide username"
                }
                if (passwordSignIn.text.isNullOrEmpty()){
                    passwordLast.error = "Provide password"
                }
            }
        }

        usernameSignIn.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                if (TextUtils.isEmpty((v as EditText).text)){
                        emailSignIn.error = "Provide username"
                    }
                else{
                    emailSignIn.error = null
                }
            }
        }
        passwordSignIn.onFocusChangeListener = View.OnFocusChangeListener{v, hasFocus->
            if (!hasFocus){
                if (TextUtils.isEmpty((v as EditText).text)){
                    passwordLast.error = "Provide password"
                }
                else{
                    passwordLast.error = null
                }
            }
        }
        val textView : TextView = forgotPassword

        val ss = SpannableString(forgotPassword.text.toString())
        val clickableSpan1 = object  : ClickableSpan(){
            override fun onClick(widget: View) {
                val sharedPreferences = requireActivity().getSharedPreferences("TimeRemind", Context.MODE_PRIVATE)
                sharedPreferences.edit().apply {  remove("Time")
                    remove("Timer")}.apply()
                findNavController().navigate(SignInDirections.toPasswordReset())
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.color = Color.RED
                ds.isUnderlineText = true
            }
        }

        ss.setSpan(clickableSpan1,0, forgotPassword.text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()

    }

}




