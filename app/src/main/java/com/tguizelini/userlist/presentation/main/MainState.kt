package com.tguizelini.userlist.presentation.main

import com.tguizelini.userlist.domain.model.User

sealed class ScreenState {
    object List : ScreenState()
    data class Form(val user: User = User()) : ScreenState()
}
