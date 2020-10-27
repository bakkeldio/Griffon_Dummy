package com.example.griffon_dummy.profile.ui


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.griffon_dummy.Dummy
import com.example.griffon_dummy.R
import com.example.griffon_dummy.profile.entity.UserInfo
import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import com.google.gson.Gson
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.File


private const val Request_Code = 2000

class ProfileFragment : Fragment() , ProfileContract.View{

    val presenter : ProfileContract.ProfilePresenter by inject{ parametersOf(this) }


    private lateinit var token : AccessToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("sp"
            , Context.MODE_PRIVATE)

        token = Gson().fromJson(sharedPref!!.getString("Token",""), AccessToken::class.java)
        val tn = sharedPref.getString("token", "")
        if (token.isExpired()){
            val refreshToken = "${token.tokenType} ${token.refreshToken}"
            sharedPref.edit().putString("token", refreshToken).apply()
            presenter.getUserInfo(refreshToken)
        }
        else {
            presenter.getUserInfo(tn!!)
        }

        logOut.setOnClickListener {
            sharedPref.edit().remove("token").apply()

            val intent = Intent(context, Dummy::class.java)
            startActivity(intent)
        }

        userLogo.setOnClickListener{
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Request_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            Request_Code -> {
                if (resultCode == Activity.RESULT_OK){
                    launchImageCrop(data?.data!!)
                }
                else{
                    Log.e("Tag", "Couldn't select this image from gallery")
                }

            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE->{
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Log.e("Tag", "Error: ${result.error}")
                }
            }
        }
    }
    private fun setImage(uri: Uri){
        val path = uri.path
        val file = File(path!!)
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartData = MultipartBody.Part.createFormData("fileupload", file.name, requestFile)


        presenter.uploadImage(multipartData,"${token.tokenType} ${token.accessToken}")
        Toast.makeText(context, token.accessToken, Toast.LENGTH_LONG).show()


    }

    private fun launchImageCrop(uri : Uri){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(context as Activity, this)
    }

    override fun setImage(url: String) {
        Toast.makeText(context, url, Toast.LENGTH_LONG).show()
        Glide.with(requireContext())
            .load(url)
            .centerCrop()
            .into(userLogo)
    }

    override fun getUserInfo(userInfo: UserInfo) {
        email.text = userInfo.email
        if (userInfo.avatar.original != ""){
            Glide.with(requireContext())
                .load(userInfo.avatar.original)
                .centerCrop()
                .into(userLogo)
        }
        emailVerification.text = userInfo.email_verified.toString()

    }

    override fun successfullyRevoked() {

    }
}
