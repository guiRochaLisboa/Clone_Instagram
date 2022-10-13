package com.example.clone_instagram.add.view

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.clone_instagram.R
import com.example.clone_instagram.add.Add
import com.example.clone_instagram.common.base.BaseFragment
import com.example.clone_instagram.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.jar.Manifest

class AddFragment : BaseFragment<FragmentAddBinding, Add.Presenter>(
    R.layout.fragment_add,
    FragmentAddBinding::bind
), Add.View {

    override lateinit var presenter: Add.Presenter

    override fun setpuPresenter() {

    }

    override fun setupViews() {
        val tabLayout = binding?.addTab
        val viewPager = binding?.addViewpager
        val adapter = AddViewPagerAdapter(requireActivity())
        viewPager?.adapter = adapter

        if (tabLayout != null && viewPager != null) {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(adapter.tabs[position])
            }.attach()
        }
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            getPermission.launch(REQUIRED_PERMISSION)
        }
    }

    private fun startCamera(){
        Log.i("Teste:","Teste da camera")
    }

    private val getPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){_ ->
    if(allPermissionsGranted()){
        startCamera()
    }else{
        Toast.makeText(requireContext(),R.string.permission_camera_denied,Toast.LENGTH_SHORT).show()
    }

    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }
}
