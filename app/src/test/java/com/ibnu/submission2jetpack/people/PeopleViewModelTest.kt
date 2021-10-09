package com.ibnu.submission2jetpack.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibnu.submission2jetpack.data.repo.PeopleRepository
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.data.source.remote.status.people.ResponseStatusPeople
import com.ibnu.submission2jetpack.ui.people.PeopleViewModel
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PeopleViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var peopleObserver: Observer<List<People>>

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    @Mock
    private lateinit var disposable: CompositeDisposable

    private lateinit var peopleViewModel: PeopleViewModel
    private var apiService = RetrofitApp.getPeopleApiService()
    private val language = "en-US"
    private val page = 1

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        peopleViewModel = PeopleViewModel(peopleRepository, disposable)
    }

    @Test
    fun getPeople() {
        peopleViewModel.getPeople()

        apiService.getPeopleData(language, page).map { it.result }.subscribe(
            {
                argumentCaptor<ResponseStatusPeople>().apply {
                    peopleRepository.getPeopleData(disposable)
                    firstValue.onSuccess(it)
                }
                peopleViewModel.getPeople().observeForever(peopleObserver)
                verify(peopleObserver).onChanged(it)
            },{}
        )
    }
}