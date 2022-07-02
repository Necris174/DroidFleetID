package com.example.droidfleetid.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entity.AuthorizationProperties
import com.example.droidfleetid.DFApp
import com.example.droidfleetid.R
import com.example.droidfleetid.databinding.FragmentLoginBinding
import com.example.droidfleetid.presentation.Result
import javax.inject.Inject


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")

    lateinit var viewModel: LoginFragmentViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val component by lazy {
        (requireActivity().application as DFApp).component
    }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this,viewModelFactory)[LoginFragmentViewModel::class.java]
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
                   saveInSharedPreferences(it.data)
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out)
                        .replace(
                            R.id.droid_fleet_container,
                            NavigationFragment.newInstance(
                                it.data.accessToken,
                                it.data.refreshToken,
                                it.data.expires.toLong()
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

    private fun saveInSharedPreferences(data: AuthorizationProperties) {
                    sharedPreferences.edit()
                        .putString("accessToken", data.accessToken)
                        .putString("refreshToken", data.refreshToken)
                        .putLong("expires", System.currentTimeMillis()+(data.expires.toLong()*1000)).apply()
                        Log.d("FRAGMENT", "CREATE FRAGMENT")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}