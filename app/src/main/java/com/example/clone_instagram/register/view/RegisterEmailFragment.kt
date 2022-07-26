package com.example.clone_instagram.register.view


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.clone_instagram.R
import com.example.clone_instagram.common.view.TxtWatcher
import com.example.clone_instagram.common.view.base.DependencyInjector
import com.example.clone_instagram.databinding.FragmentRegisterEmailBinding
import com.example.clone_instagram.register.RegisterEmail
import com.example.clone_instagram.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {


    private var binding: FragmentRegisterEmailBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter : RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterEmailBinding.bind(view)

        val repository = DependencyInjector.registerEmaiRepository()
        presenter = RegisterEmailPresenter(this,repository)

        binding?.let {
            with(it){
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }

                registerBtnNext.setOnClickListener {
                    presenter.create(registerEditEmail.text.toString())
                }

                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener { TxtWatcher {
                    displayEmailFailure(null)
                } }
            }
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
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

    private val watcher = TxtWatcher {
        binding?.registerBtnNext?.isEnabled = binding?.registerEditEmail?.text.toString().isNotEmpty()

    }
}