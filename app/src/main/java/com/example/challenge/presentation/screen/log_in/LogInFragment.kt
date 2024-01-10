package com.example.challenge.presentation.screen.log_in

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.presentation.base.BaseFragment
import com.example.challenge.databinding.FragmentLogInBinding
import com.example.challenge.presentation.event.log_in.LogInEvent
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.state.log_in.LogInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {
        binding.btnLogIn.setOnClickListener {
            logIn()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    handleLogInState(logInState = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun logIn() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLogInState(logInState: LogInState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (logInState.isLoading) View.VISIBLE else View.GONE

        logInState.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(LogInEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: LogInViewModel.LogInUiEvent) {
        when (event) {
            is LogInViewModel.LogInUiEvent.NavigateToConnections -> findNavController().navigate(
                LogInFragmentDirections.actionLogInFragmentToFriendsFragment()
            )
        }
    }
}
