package com.tguizelini.userlist.presentation.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tguizelini.userlist.R
import com.tguizelini.userlist.databinding.FragmentUserProfileBinding
import com.tguizelini.userlist.domain.model.User
import com.tguizelini.userlist.presentation.UserViewModel
import com.tguizelini.userlist.presentation.main.ScreenState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val ARG_USER = "ARG_USER"

class UserProfileFragment : Fragment() {

    private val GALLERY_CODE = 0

    private val binding by lazy {
        FragmentUserProfileBinding.inflate(layoutInflater)
    }

    private val vm by sharedViewModel<UserViewModel>()

    private var id: Int? = null
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setUser(it.getParcelable<User>(ARG_USER))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindClickListeners()

        return binding.root
    }

    private fun setUser(user: User?) {
        id = user?.id

        binding.profileBtnDelete.visibility = if (id == null)
            View.GONE else View.VISIBLE

        user?.avatar?.let {
            binding.profileIvAvatar.setImageURI(Uri.parse(it))
        }

        binding.profileTvName.editText?.setText(user?.name.orEmpty())
        binding.profileTvBio.editText?.setText(user?.bio.orEmpty())
    }

    private fun bindClickListeners() {
        binding.profileBtnDelete.setOnClickListener {
            id?.let {
                vm.remove(it)
            }
        }

        binding.profileBtnSave.setOnClickListener {
            vm.save(
                User(
                    id = id,
                    name = binding.profileTvName.editText?.text.toString(),
                    bio = binding.profileTvBio.editText?.text.toString(),
                    avatar = avatar
                )
            )
        }

        binding.profileFabList.setOnClickListener {
            vm.navigateTo(ScreenState.List)
        }

        binding.profileIvAvatar.setOnClickListener {
            //TODO: check permissions

            startActivityForResult(
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                GALLERY_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            data.data?.let {
                avatar = it.toString()
                binding.profileIvAvatar.setImageURI(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_USER, user)
            }
        }
    }
}