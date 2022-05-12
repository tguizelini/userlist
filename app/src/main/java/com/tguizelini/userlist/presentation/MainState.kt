package com.tguizelini.userlist.presentation

sealed class ScreenState {
    object List : ScreenState()
    object Form : ScreenState()
}
