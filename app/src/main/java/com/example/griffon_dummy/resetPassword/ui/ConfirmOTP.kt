package com.example.griffon_dummy.resetPassword.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide


import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_confirm_o_t_p.*
import kotlinx.android.synthetic.main.fragment_confirm_o_t_p.enterCode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfirmOTP : Fragment(), ContractOtp.View1 {
    val presenter : ContractOtp.Presenter by inject { parametersOf(this)}

    private lateinit var countdownTimer: CountDownTimer
    private var isRunning: Boolean = false;
    var timeInMilliSeconds = 0L

    private val args : ConfirmOTPArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_o_t_p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resendOtp.visibility = View.INVISIBLE

        timeInMilliSeconds = args.time.toLong()*1000L

        startTimer(timeInMilliSeconds)

        //Button verify
        verifyButton2.setOnClickListener {
            if (enterCode.text.toString().isNotEmpty() ){
                val bundle = Bundle()
                presenter.putCode(if (bundle.get("sid")!= null) bundle.getString("sid")!!
                else args.sid, enterCode.text.toString())

            }
        }

        //Getting the design for fragment
        presenter.getDesign()

        timeToConfirm.text = args.time

        resendOtp.setOnClickListener {
            Toast.makeText(context, args.username, Toast.LENGTH_LONG).show()
            presenter.resendOtp(args.username,args.resetOption)
            textInputLayout.visibility = View.VISIBLE
            timeToConfirm.visibility = View.VISIBLE
            timeToConfirm.text = args.time
            timeInMilliSeconds = timeToConfirm.text.toString().toLong()*1000L
            startTimer(timeInMilliSeconds)
            resendOtp.visibility = View.INVISIBLE
        }

    }

    override fun onStop() {
        countdownTimer.cancel()
        val bundle = Bundle()
        bundle.clear()
        super.onStop()
    }

    private fun updateTextUI() {
        val minute = (timeInMilliSeconds / 1000) / 60
        val seconds = (timeInMilliSeconds / 1000) % 60

        if (seconds<10){
            timeToConfirm.text ="0$minute:0$seconds"
        }
        else {
            timeToConfirm.text = "0$minute:$seconds"
        }
    }

    override fun updateButton(colors: IntArray) {
        verifyButton2.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }

    override fun updateImage(url: String) {
        Glide.with(requireContext())
            .load(url)
            .into(logoReset)
    }


    //Getting sid from resendOtp
    override fun getSid(sid: String) {
        Toast.makeText(context, "Sid is received!", Toast.LENGTH_LONG).show()
        val bundle = Bundle()
        bundle.putString("sid", sid)
    }

    private fun startTimer(time_in_seconds: Long) {

        countdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {

                resendOtp.visibility = View.VISIBLE
                timeToConfirm.visibility = View.INVISIBLE
                textInputLayout.visibility = View.INVISIBLE
            }

            override fun onTick(p0: Long) {
                timeInMilliSeconds = p0
                updateTextUI()
            }
        }
        countdownTimer.start()

        isRunning = true


    }

    override fun getSid1(message: String) {
        val action = ConfirmOTPDirections.toUpdatePassword(message)
        findNavController().navigate(action)
    }
}
