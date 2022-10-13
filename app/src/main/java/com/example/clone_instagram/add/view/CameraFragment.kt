package com.example.clone_instagram.add.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.clone_instagram.R
import java.lang.Exception

class CameraFragment : Fragment() {

    private lateinit var previewView: PreviewView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewView = view.findViewById(R.id.camera_img)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("cameraKey") { requestKey, bundle ->
            val shouldStart = bundle.getBoolean("startCamera")

            if (shouldStart) {
                startCamera()
            }
        }
    }

    //Método para inicializar a camera (código padrão da documentação)
    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
            cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this,cameraSelector,preview)
            }catch (ex: Exception){
                Log.e("Exception","Failure initialize camera",ex)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


}