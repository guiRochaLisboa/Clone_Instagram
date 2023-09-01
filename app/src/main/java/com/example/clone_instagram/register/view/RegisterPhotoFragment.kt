package com.example.clone_instagram.register.view

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.clone_instagram.R
import com.example.clone_instagram.common.base.DependencyInjector
import com.example.clone_instagram.common.base.RequestCallback
import com.example.clone_instagram.common.view.CropperImageFragment.Companion.KEY_URI
import com.example.clone_instagram.common.view.CustomDialog
import com.example.clone_instagram.databinding.FragmentRegisterPhotoBinding
import com.example.clone_instagram.post.view.AddFragment
import com.example.clone_instagram.register.RegisterPhoto
import com.example.clone_instagram.register.presentation.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo),RegisterPhoto.View {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var attachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterPhoto.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("cropeKey"){requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>(KEY_URI)
            onCropImageResult(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view)

        val repository = DependencyInjector.registerEmaiRepository()
        presenter = RegisterPhotoPresenter(this,repository)

        binding?.let {
            with(it){
                registerBtnJump.setOnClickListener{
                    attachListener?.goToMainScreen()
                }

                registerBtnNext.isEnabled = true
                registerBtnNext.setOnClickListener {
                    openDialog()
                }

            }
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener){
            attachListener = context
        }
    }


    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    override fun onUpdateSucess() {
        attachListener?.goToMainScreen()
    }

    private fun openDialog(){
        val customDialog = CustomDialog(requireContext())

        customDialog.addButton(R.string.photo, R.string.gallery) {
            when (it.id) {
                R.string.photo -> {
                    if(allPermissionsGranted()){
                        attachListener?.goToCameraScreen()
                    }else{
                    getPermission.launch(REQUIRED_PERMISSION)
                        }
                }
                R.string.gallery -> {
                    attachListener?.goToGalleryScreen()
                }
            }

        }
        customDialog.show()
    }

    private val getPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ _ ->
        if(allPermissionsGranted()){
            attachListener?.goToCameraScreen()
        }else{
            Toast.makeText(requireContext(),R.string.permission_camera_denied,Toast.LENGTH_SHORT).show()
        }

    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED

    private fun onCropImageResult(uri: Uri?) {
        if (uri != null){
            val bitmap =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver,uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver,uri)
            }
            binding?.registerImgProfile?.setImageBitmap(bitmap)

            presenter.updateUser(uri)
        }
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    }


    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}