package com.tguizelini.userlist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val name: String? = null,
    val bio: String? = null,
    val avatar: String? = null
) : Parcelable
