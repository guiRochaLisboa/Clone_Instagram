package com.example.clone_instagram.register.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R
import com.example.clone_instagram.common.view.TxtWatcher
import com.example.clone_instagram.common.view.base.DependencyInjector
import com.example.clone_instagram.databinding.FragmentRegisterEmailBinding
import com.example.clone_instagram.databinding.FragmnetRegisterNamePasswordBinding
import com.example.clone_instagram.register.RegisterEmail
import com.example.clone_instagram.register.RegisterNameAndPassword
import com.example.clone_instagram.register.presentation.RegisterEmailPresenter
import com.example.clone_instagram.register.presentation.RegisterNamePasswordPresenter
import java.lang.IllegalArgumentException

class RegisterNamePasswordFragment : Fragment(R.layout.fragmnet_register_name_password),RegisterNameAndPassword.View {


    private var binding: FragmnetRegisterNamePasswordBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null


    override lateinit var presenter: RegisterNameAndPassword.Presenter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmnetRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmaiRepository()
        presenter = RegisterNamePasswordPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("email not found")

        binding?.let {
            with(it){
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }

                registerNameBtnNext.setOnClickListener{
                    presenter.create(
                        email,
                        registerEditName.text.toString(),
                    registerEditPassword.text.toString(),
                        registerEditConfirm.text.toString()
                    )
                }


                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditConfirm.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener(TxtWatcher{
                    displayNameFailure(null)
                })

                registerEditPassword.addTextChangedListener(TxtWatcher{
                    displayPasswordFailure(null)
                })

                registerEditConfirm.addTextChangedListener(TxtWatcher{
                    displayPasswordFailure(null)
                })

            }
        }

        Log.d("emial",email.toString())
    }

    /**Método disparado quando nosso fragment é vinculado a uma activity*/
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener){
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNameBtnNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onCreateSucess(name: String) {
         TODO: abrir a tela de bem-vindo
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    private val watcher = TxtWatcher{
        binding?.registerNameBtnNext?.isEnabled = binding?.registerEditName?.text.toString().isNotEmpty()
                && binding?.registerEditPassword?.text.toString().isNotEmpty()
                && binding?.registerEditConfirm?.text.toString().isNotEmpty()
    }



    companion object{
        const val KEY_EMAIL = "kay_email"
    }

}