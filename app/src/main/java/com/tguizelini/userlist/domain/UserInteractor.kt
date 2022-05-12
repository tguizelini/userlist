package com.tguizelini.userlist.domain

import com.tguizelini.userlist.data.UserRepository
import com.tguizelini.userlist.domain.model.User
import io.reactivex.rxjava3.core.Single

interface UserInteractor {
    fun getUsers() : Single<List<User>>
}

class UserInteractorImpl constructor(
    private val repository: UserRepository
) : UserInteractor {
    override fun getUsers(): Single<List<User>> {
        //we can turn the response into other type using mapper here
        return repository.getUsers()
    }
}