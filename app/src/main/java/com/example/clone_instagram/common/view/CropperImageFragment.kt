package com.example.clone_instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.clone_instagram.R
import com.example.clone_instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment : Fragment(R.layout.fragment_image_cropper) {

    private var binding: FragmentImageCropperBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentImageCropperBinding.bind(view)


        val uri = arguments?.getParcelable<Uri>(KEY_URI)

        binding?.let {
            with(it){

                cropperContainer.setAspectRatio(1,1)
                cropperContainer.setFixedAspectRatio(true)

                cropperContainer.setImageUriAsync(uri)

                cropperBtnCancel.setOnClickListener {
                    parentFragmentManager.popBackStack() //Forçar o fragmento "voltar"
                }

                cropperContainer.setOnCropImageCompleteListener { view, result ->
                    Log.i("Teste","nova imagem ${result.uri}")

                    setFragmentResult("cropeKey", bundleOf(KEY_URI to result.uri))

                    parentFragmentManager.popBackStack()
                }

                cropperBtnSave.setOnClickListener {
                    var dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    if (dir != null){
                        val uriToSaved = Uri.fromFile(File(dir.path, System.currentTimeMillis().toString() + ".jpeg"))
                        cropperContainer.saveCroppedImageAsync(uriToSaved)
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_URI = "key_uri"
    }
}