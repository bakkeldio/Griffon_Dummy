package com.example.griffon_dummy.signUp.data.ui
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.GlideApp
import com.example.griffon_dummy.R
import com.example.griffon_dummy.signUp.data.data.SignUpService
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.logo
import org.koin.core.KoinComponent
import org.koin.core.inject

import org.koin.core.parameter.parametersOf
import java.util.regex.Pattern


class SignUp : Fragment(), ContractView.View, KoinComponent{

    private val presenter: ContractView.SignUpPresenter by inject{parametersOf(this)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter.getSignUpResult()

        onNext.setOnClickListener{



            if (!checkbox.isChecked) {

                Toast.makeText(context, "Check terms and conditions", Toast.LENGTH_LONG).show()
            }
            when {
                emailNumber.text.toString().isEmailValid() -> {
                    val bundle = bundleOf("email" to emailNumber.text.toString())
                    findNavController().navigate(R.id.toMainSignUp,bundle)

                }
                emailNumber.text.toString().isValidMobile() -> {
                    presenter.giveNumber(emailNumber.text.toString())
                }
                else -> {
                    if (emailNumber.text!!.isEmpty()){
                        emailNumberSignUp.error = "Provide username"
                    }
                    if (emailNumber.text!!.isNotEmpty() && (!emailNumber.text.toString().isValidMobile()||!emailNumber.text.toString().isEmailValid())){
                        emailNumberSignUp.error = "Provide valid mobile/email"
                    }

                }
            }
            }

        emailNumber.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty())
                    emailNumberSignUp.error = null

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        backSignIn.setOnClickListener {
            findNavController().navigate(SignUpDirections.toSignIn())
        }



    }

    override fun onDestroy() {
        Log.d("DestroySignUp: ", "Running")
        presenter.onDestroy()
        super.onDestroy()
    }


    fun String.isEmailValid() =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(this).matches()

    private fun String.isValidMobile(): Boolean {
        return Patterns.PHONE.matcher(this).matches()
    }
    override fun updateImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(logo)
    }

    override fun termsConditions(url: String) {
        val textView : TextView = Terms_and_condition_text
        val text = getText(R.string.terms_and_conditions)
        val ss = SpannableString(text)
        val clickableSpan1 = object  : ClickableSpan(){
            override fun onClick(widget: View) {

                url.asUri().openInBrowser(context!!)

            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.color = Color.RED
                ds.isUnderlineText = true
            }
        }

        ss.setSpan(clickableSpan1,13, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun updateButton(colors: IntArray) {
        onNext.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }

    override fun takeSidDuration(sid: String, time: String) {
        val bundle = bundleOf("sid" to sid, "time" to time)
        findNavController().navigate(R.id.signUpSms, bundle)
    }


    fun Uri?.openInBrowser(context: Context){
        this ?: return

        val browser = Intent(Intent.ACTION_VIEW, this)
        ContextCompat.startActivity(context, browser, null)
    }

    override fun onStop() {
        Log.d("StopSignUp: ", "Running")
        super.onStop()
    }

    fun String?.asUri() : Uri? {
        try {
            return Uri.parse(this)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }





}
