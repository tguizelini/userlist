package com.tguizelini.userlist.data

import com.tguizelini.userlist.domain.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

//FAKE SERVICE, should be an retrofit service here
class UserService constructor() {
    fun getFakeList() : Single<Response<List<User>>> {
        return Single.just(
            Response.success(listOf(
                User(
                    id = Math.random().toInt(),
                    name = "Tiago Guizelini",
                    bio = "Android Engineer"
                )
            ))
        )
    }
}