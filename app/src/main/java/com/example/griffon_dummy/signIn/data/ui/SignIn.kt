package com.example.griffon_dummy.signIn.data.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.GlideApp
import com.example.griffon_dummy.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.logo
import kotlinx.android.synthetic.main.fragment_sign_up.*
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

    override fun successfullySignIn() {
      val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.popup)
        dialog.show()
    }

    override fun updateButton(colors: IntArray) {
        signIn.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUp.setOnClickListener {
            findNavController().navigate(SignInDirections.toSignUp())
        }
        signIn.setOnClickListener {
            presenter.getAccessToken(emailSignIn.text.toString(), passwordSignIn.text.toString())
        }

        val textView : TextView = forgotPassword

        val ss = SpannableString(forgotPassword.text.toString())
        val clickableSpan1 = object  : ClickableSpan(){
            override fun onClick(widget: View) {
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




