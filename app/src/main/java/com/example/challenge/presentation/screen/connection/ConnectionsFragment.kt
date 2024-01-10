package com.example.challenge.presentation.screen.connection

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.databinding.FragmentConnectionsBinding
import com.example.challenge.presentation.base.BaseFragment
import com.example.challenge.presentation.event.conection.ConnectionEvent
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.screen.log_in.LogInFragmentDirections
import com.example.challenge.presentation.screen.log_in.LogInViewModel
import com.example.challenge.presentation.state.connection.ConnectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectionsFragment :
    BaseFragment<FragmentConnectionsBinding>(FragmentConnectionsBinding::inflate) {

    private val viewModel: ConnectionsViewModel by viewModels()
    private lateinit var connectionsRecyclerAdapter: ConnectionsRecyclerAdapter

    override fun bind() {
        connectionsRecyclerAdapter = ConnectionsRecyclerAdapter()
        binding.apply {
            recyclerConnections.layoutManager = LinearLayoutManager(requireContext())
            recyclerConnections.setHasFixedSize(true)
            recyclerConnections.adapter = connectionsRecyclerAdapter
        }
        viewModel.onEvent(ConnectionEvent.FetchConnections)
    }

    override fun bindViewActionListeners() {
        binding.btnLogOut.setOnClickListener {
            viewModel.onEvent(ConnectionEvent.LogOut)
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collect {
                    handleConnectionState(state = it)
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

    private fun handleConnectionState(state: ConnectionState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.connections?.let {
            connectionsRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(ConnectionEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: ConnectionsViewModel.ConnectionUiEvent) {
        findNavController().navigate(ConnectionsFragmentDirections.actionFriendsFragmentToLogInFragment())
    }
}

