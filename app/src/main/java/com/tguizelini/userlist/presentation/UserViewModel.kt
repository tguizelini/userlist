package com.tguizelini.userlist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tguizelini.userlist.domain.model.User
import com.tguizelini.userlist.domain.UserInteractor
import com.tguizelini.userlist.presentation.main.ScreenState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class UserViewModel constructor(
    private val interactor: UserInteractor
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val screenState = MutableLiveData<ScreenState>(ScreenState.List)
    val items = MutableLiveData<List<User>>()

    init {
        getData()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun navigateTo(value: ScreenState) {
        screenState.value = value
    }

    fun getData() {
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
                val newId = Random().nextInt(200)
                current.addAll(items.value.orEmpty())
                current.add(item.copy(id = newId))
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
        screenState.value = ScreenState.List
    }

    fun remove(id: Int) {
        items.value = items.value?.filter { it.id != id }.orEmpty()
        screenState.value = ScreenState.List
    }

}