package com.example.griffon_dummy.resetPassword.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.griffon_dummy.R
import kotlinx.android.synthetic.main.fragment_update_password.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf




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
            if (updatePassword.text.toString().isNotEmpty() && updateConfirmPassword.text.toString().isNotEmpty()){
                Toast.makeText(context, args.sid, Toast.LENGTH_LONG).show()
                presenter.getUpdatePasswordConfirm(args.sid, updatePassword.text.toString())
            }
            else{
                if(updatePassword.text.toString().isEmpty()){
                    updatePassword.error = "Type a new password"
                }
                if (updateConfirmPassword.text.toString().isEmpty()){
                    updateConfirmPassword.error = "Confirm a new password"
                }
            }
        }


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
