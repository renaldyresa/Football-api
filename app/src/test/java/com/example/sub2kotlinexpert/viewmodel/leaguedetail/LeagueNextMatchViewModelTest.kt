package com.example.sub2kotlinexpert.viewmodel.leaguedetail


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.DataSource
import com.nhaarman.mockito_kotlin.*

import org.junit.Test
import org.junit.Assert.*


import org.junit.Before
import org.junit.Rule

import org.mockito.*


class LeagueNextMatchViewModelTest {

    @Mock
    private lateinit var matchModel: LeagueNextMatchViewModel

    @Mock
    private lateinit var repository: DataSource<ArrayList<Match>>

    @Mock
    private lateinit var listData: ArrayList<Match>


    @Mock
    private lateinit var repositoryLogo: DataSource<String>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchModel = spy(LeagueNextMatchViewModel(repository, repositoryLogo))
    }


    @Test
    fun setEventNextMatchTest() {
        val id = "4328"
        matchModel.setEventNextMatch(id)
        // ketika setEventNextMatch dijalakan akan muncul loading => isViewLoading = true
        assertEquals(matchModel.isViewLoading.value, true)
        argumentCaptor<OperationCallback<ArrayList<Match>>>().apply {
            verify(repository).retrieveData(eq(id), capture())
            firstValue.onSuccess(listData)
            assertEquals(matchModel.getEventNextMatch().value, listData)
        }
        //ketika data sudah didapatkan maka laoding akan  di stop => isViewLoading = false
        assertEquals(matchModel.isViewLoading.value, false)

    }

    @Test
    fun setEventNextMatchErrorTest() {
        val id = ""
        matchModel.setEventNextMatch(id)
        // ketika setEventNextMatch dijalakan akan muncul loading => isViewLoading = true
        assertEquals(matchModel.isViewLoading.value, true)
        argumentCaptor<OperationCallback<ArrayList<Match>>>().apply {
            verify(repository).retrieveData(eq(id), capture())
            firstValue.onError("")
            assertEquals(matchModel.onMessageError.value, "Failed")
        }
        //ketika data sudah didapatkan maka laoding akan  di stop => isViewLoading = false
        assertEquals(matchModel.isViewLoading.value, false)

    }
}