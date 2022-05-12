package com.tguizelini.userlist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tguizelini.userlist.domain.model.User
import com.tguizelini.userlist.domain.UserInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel constructor(
    private val interactor: UserInteractor
) : ViewModel() {

    private val disposable = CompositeDisposable()
    val items = MutableLiveData<List<User>>()

    init { getData() }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun getData() {
        disposable.add(
            interactor.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        items.value = it
                    }, {
                       items.value = emptyList()
                    }
                )
        )
    }

    fun save(item: User) {
        val current = mutableListOf<User>()

        when(item.id) {
            null -> {
                current.addAll(items.value.orEmpty())
                current.add(item.copy(id = Math.random().toInt()))
            }
            else -> {
                items.value?.forEach {
                    if (it.id == item.id) {
                        current.add(item)
                        return
                    }

                    current.add(it)
                }
            }
        }

        items.value = current
    }

    fun remove(id: Int) {
        items.value = items.value?.filter { it.id != id }.orEmpty()
    }

}