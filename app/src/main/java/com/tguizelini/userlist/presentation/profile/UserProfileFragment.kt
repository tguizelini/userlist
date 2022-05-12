package com.tguizelini.userlist.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tguizelini.userlist.R
import com.tguizelini.userlist.databinding.FragmentUserProfileBinding
import com.tguizelini.userlist.presentation.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

//
class UserProfileFragment : Fragment() {

    private val binding by lazy {
        FragmentUserProfileBinding.inflate(layoutInflater)
    }

    private val vm by sharedViewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}