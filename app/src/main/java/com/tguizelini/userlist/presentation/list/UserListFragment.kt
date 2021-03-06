package com.tguizelini.userlist.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tguizelini.userlist.R
import com.tguizelini.userlist.databinding.FragmentUserListBinding
import com.tguizelini.userlist.domain.model.User
import com.tguizelini.userlist.presentation.main.ScreenState
import com.tguizelini.userlist.presentation.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserListFragment : Fragment() {

    private val binding by lazy {
        FragmentUserListBinding.inflate(layoutInflater)
    }

    private val vm by sharedViewModel<UserViewModel>()

    private lateinit var userAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setup()
        bindObservables()
        bindClickListeners()

        return binding.root
    }

    private fun setup() {
        userAdapter = UserListAdapter(::onItemClick)

        binding.rvUserList.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.item_decoration,
                    )!!)
                }
            )
        }
    }

    private fun bindObservables() {
        vm.items.observe(viewLifecycleOwner, Observer {
            userAdapter.setData(it)
        })
    }

    private fun bindClickListeners() {
        binding.fabAdd.setOnClickListener {
            vm.navigateTo(ScreenState.Form())
        }
    }

    private fun onItemClick(item: User) {
        vm.navigateTo(ScreenState.Form(item))
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}