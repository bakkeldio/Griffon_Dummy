package com.example.griffon_dummy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.presenter.ContractView
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.logo
import org.koin.core.KoinComponent
import org.koin.core.inject

import org.koin.core.parameter.parametersOf


class SignUp : Fragment(),ContractView.View, KoinComponent{

    private val signUpService : SignUpService by inject()
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

            if (checkbox.isChecked) {

                if (emailNumber.text.toString().isValidEmail()) {

                }
                if (emailNumber.text.toString().isValidMobile()) {

                } else {
                    emailNumber.error = "Provide correct email or Phone number"
                }
            }
            else{
                Toast.makeText(context, "Check terms and conditions", Toast.LENGTH_LONG).show()
            }

        }


    }


    private fun String.isValidEmail(): Boolean =
        this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isValidMobile(): Boolean {
        return Patterns.PHONE.matcher(this).matches()
    }

    override fun updateImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(logo)
    }

    override fun termsConditions(url: String) {
        val textView : TextView = Terms_and_condition_text
        val text = getText(R.string.terms_and_conditions)
        val ss = SpannableString(text)
        val clickableSpan1 = object  : ClickableSpan(){
            override fun onClick(widget: View) {
                /*
                web.settings.javaScriptEnabled = true
                web.settings.domStorageEnabled = true
                web.webViewClient = WebViewClient()
                web.loadUrl(url)
                web.canGoBack()
                web.setOnKeyListener{ v, keyCode, event ->
                    if(keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP
                        && web.canGoBack()){
                        web.goBack()
                        return@setOnKeyListener true
                    }
                    false
                }
                textInputLayout.visibility = View.GONE
                backSignIn.visibility = View.GONE
                onNext.visibility = View.GONE
            }

                 */
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



    fun Uri?.openInBrowser(context: Context){
        this ?: return

        val browser = Intent(Intent.ACTION_VIEW, this)
        ContextCompat.startActivity(context, browser, null)
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
