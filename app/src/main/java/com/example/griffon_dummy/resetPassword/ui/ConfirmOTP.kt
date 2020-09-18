package com.example.griffon_dummy.resetPassword.ui

import android.content.Context
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
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfirmOTP : Fragment(), ContractOtp.View1 {
    val presenter : ContractOtp.Presenter by inject { parametersOf(this)}

    private lateinit var countdownTimer: CountDownTimer
    private var isRunning: Boolean = false
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

        val sharedPref = activity?.getSharedPreferences("TimeRemind", Context.MODE_PRIVATE)
        if (sharedPref!!.getString("Time","") == "") {

            resendOtp.visibility = View.INVISIBLE

            timeInMilliSeconds = args.time.toLong() * 1000L

            startTimer(timeInMilliSeconds)

            timeToConfirm.text = args.time
        }
        else {
            resendOtp.visibility = View.INVISIBLE

            val localTime = sharedPref.getString("Time", "")
            val savedTime = sharedPref.getInt("Timer", 0)
            if (savedTime != 0) {
                if (localTime != "") {
                    val localDateTime = LocalDateTime.parse(localTime).toLocalTime()
                    val currentLocalTime = LocalTime.now()
                    val difference: Long = Duration.between(localDateTime, currentLocalTime).seconds
                    if (difference.minus(savedTime) > 0) {
                        timeToConfirm.visibility = View.INVISIBLE
                        resendOtp.visibility = View.VISIBLE
                    } else {
                        timeToConfirm.text = "${(savedTime - difference)}"
                        timeInMilliSeconds = timeToConfirm.text.toString().toLong() * 1000L
                        startTimer(timeInMilliSeconds)

                    }
                }
            }

        }


        //Button verify
        verifyButton2.setOnClickListener {
            if (enterCode.text.toString().isNotEmpty() ){
                val sid = sharedPref.getString("sid","")
                presenter.putCode(if (sid != "") sid!! else args.sid, enterCode.text.toString())

            }
        }



        //Getting the design for fragment
        presenter.getDesign()

        resendOtp.setOnClickListener {
            Toast.makeText(context, args.username, Toast.LENGTH_LONG).show()
            presenter.resendOtp(args.username,args.resetOption)

        }

    }

    override fun onStop() {

        val sharedPref = activity?.getSharedPreferences("TimeRemind", Context.MODE_PRIVATE)

        sharedPref!!.edit().apply {  putString("Time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            val minInSec = timeToConfirm.text[1].toString().toInt()*60
            val seconds = (timeToConfirm.text[3].toString()+timeToConfirm.text[4].toString()).toInt()
            putString("username", args.username)
            putInt("Timer", minInSec+seconds)

        }.apply()
        countdownTimer.cancel()
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
        val sharedPref = activity?.getSharedPreferences("TimeRemind", Context.MODE_PRIVATE)
        Toast.makeText(context, "Sid is received!", Toast.LENGTH_LONG).show()
        sharedPref?.edit()?.putString("sid", sid)?.apply()
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

    override fun addInfo(addInfo: String) {

        textInputLayout.visibility = View.VISIBLE
        timeToConfirm.visibility = View.VISIBLE
        timeToConfirm.text = addInfo
        timeInMilliSeconds = timeToConfirm.text.toString().toLong()*1000L
        startTimer(timeInMilliSeconds)
        resendOtp.visibility = View.INVISIBLE
    }
}
