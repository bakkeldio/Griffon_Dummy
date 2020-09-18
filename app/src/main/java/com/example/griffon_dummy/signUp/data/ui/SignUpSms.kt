package com.example.griffon_dummy.signUp.data.ui

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_sign_up_sms.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

class SignUpSms : Fragment(), ContractView3.View1 {


    private val presenter: ContractView3.SidPresenter by inject { parametersOf(this) }
    private lateinit var countdownTimer: CountDownTimer
    var isRunning: Boolean = false;
    var timeInMilliSeconds = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_up_sms, container, false)
    }

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("Time", Context.MODE_PRIVATE)

        presenter.getDesign()
        resendView.visibility = View.INVISIBLE

        if (arguments != null && sharedPref!!.getString("Time","") == "") {
            time.text = requireArguments().getString("time")
            timeInMilliSeconds = time.text.toString().toLong() * 1000L
            startTimer(timeInMilliSeconds)
        }
        else {
            val localTime = sharedPref!!.getString("Time", "")
            val savedTime = sharedPref.getInt("Timer", 0)
            if (savedTime != 0) {
                if (localTime != "") {
                    val localDateTime = LocalDateTime.parse(localTime).toLocalTime()
                    val currentLocalTime = LocalTime.now()
                    val difference: Long = Duration.between(localDateTime, currentLocalTime).seconds
                    if (difference.minus(savedTime) > 0) {
                        time.visibility = View.INVISIBLE
                        resendView.visibility = View.VISIBLE
                    } else {
                        time.text = "${(savedTime - difference)}"
                        timeInMilliSeconds = time.text.toString().toLong()*1000L
                        startTimer(timeInMilliSeconds)

                    }
                }
            }
        }


        verifyButton.setOnClickListener {
            if(time.visibility == View.INVISIBLE){
                verifyButton.error = "Time is over"
            }
            else {
                presenter.getCompletable(
                    requireArguments().getString("sid")!!,
                    enterCode.text.toString()
                )
            }
        }
        backSignUp.setOnClickListener {
            findNavController().navigate(SignUpSmsDirections.toSignUp())
        }


        resendView.setOnClickListener {
            time.visibility = View.VISIBLE
            time.text=requireArguments().getString("time")
            timeInMilliSeconds = time.text.toString().toLong()*1000L
            startTimer(timeInMilliSeconds)
            resendView.visibility = View.INVISIBLE
        }
    }

    private fun startTimer(time_in_seconds: Long) {

        countdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {

                resendView.visibility = View.VISIBLE
                time.visibility = View.INVISIBLE
            }

            override fun onTick(p0: Long) {
                timeInMilliSeconds = p0
                updateTextUI()
            }
        }
        countdownTimer.start()

        isRunning = true


    }
    private fun updateTextUI() {
        val minute = (timeInMilliSeconds / 1000) / 60
        val seconds = (timeInMilliSeconds / 1000) % 60


        if (seconds <10){
            time.text = "0$minute:0$seconds"
        }
        else{
            time.text = "0$minute:$seconds"
        }
    }

    override fun onStop() {
        val sharedPref = activity?.getSharedPreferences("Time", Context.MODE_PRIVATE)
        countdownTimer.cancel()
        sharedPref!!.edit().apply {  putString("Time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                val minInSec = time.text[1].toString().toInt()*60
                val seconds = (time.text[3].toString()+time.text[4].toString()).toInt()
            putInt("Timer", minInSec+seconds)

        }.apply()
        super.onStop()
    }


    override fun takeSid(sid: String) {

        val bundle = bundleOf("sid" to sid)
        findNavController().navigate(R.id.mainSignUp, bundle)
    }

    override fun update(colors: IntArray) {
        verifyButton.background = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }

    override fun logo(url: String) {
        Glide.with(requireContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(logoSms)
    }


}

