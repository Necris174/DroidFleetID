package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.droidfleetid.R
import com.example.droidfleetid.databinding.FragmentLoginBinding
import com.example.droidfleetid.presentation.Result


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")
    lateinit var viewModel: LoginFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputLogin()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        viewModel.errorInputLogin.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Введите Логин"
            } else {
                null
            }
            TransitionManager.beginDelayedTransition(binding.rootLogin)
            binding.textInputLogin.error = message
            binding.authorizationButton.isEnabled = true
            binding.progressBar.visibility = View.INVISIBLE
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Введите Пароль"
            } else {
                null
            }
            TransitionManager.beginDelayedTransition(binding.rootLogin)
            binding.textInputPassword.error = message
            binding.authorizationButton.isEnabled = true
            binding.progressBar.visibility = View.INVISIBLE
        }

        viewModel.authorizationToken.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out)
                        .replace(
                            R.id.droid_fleet_container,
                            NavigationFragment.newInstance(
                                it.data.accessToken,
                                it.data.refreshToken,
                                it.data.expires
                            )
                        ).commit()
                }
                is Result.Error -> {
                    TransitionManager.beginDelayedTransition(binding.rootLogin)
                    binding.authorizationButton.isEnabled = true
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.textView.text = it.message

                }
            }
        }
        binding.authorizationButton.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.rootLogin)
            binding.textView.text = null
            binding.authorizationButton.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            viewModel.authorization(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}