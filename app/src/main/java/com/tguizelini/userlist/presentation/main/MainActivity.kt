package com.tguizelini.userlist.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tguizelini.userlist.R
import com.tguizelini.userlist.databinding.ActivityMainBinding
import com.tguizelini.userlist.presentation.UserViewModel
import com.tguizelini.userlist.presentation.list.UserListFragment
import com.tguizelini.userlist.presentation.profile.UserProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val vm by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindObservables()
    }

    private fun bindObservables() {
        vm.screenState.observe(this, Observer {
            when (it) {
                is ScreenState.Form -> replaceFragment(
                    UserProfileFragment.newInstance(it.user)
                )
                else -> replaceFragment(
                    UserListFragment.newInstance()
                )
            }
        })
    }

    override fun onBackPressed() {
        vm.navigateTo(ScreenState.List)
    }

    private fun replaceFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.mainContainer, fragment)
            setReorderingAllowed(true)
            commitAllowingStateLoss()
        }
    }
}