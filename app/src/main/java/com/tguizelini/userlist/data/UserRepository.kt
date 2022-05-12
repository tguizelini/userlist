package com.tguizelini.userlist.data

import com.tguizelini.userlist.domain.model.User
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUsers() : Single<List<User>>
}

class UserRepositoryImpl constructor(
    private val service: UserService
) : UserRepository {
    override fun getUsers(): Single<List<User>> {
        return service.getFakeList().map {
            if (it.isSuccessful)
                return@map it.body().orEmpty()

            throw Exception(it.message())
        }
    }
}