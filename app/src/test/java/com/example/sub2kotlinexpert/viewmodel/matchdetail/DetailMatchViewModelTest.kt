package com.example.sub2kotlinexpert.viewmodel.matchdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sub2kotlinexpert.data.OperationCallback
import com.example.sub2kotlinexpert.entity.Match
import com.example.sub2kotlinexpert.model.DataSource
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailMatchViewModelTest {

    @Mock
    private lateinit var matchModel: DetailMatchViewModel

    @Mock
    private lateinit var repository: DataSource<Match>

    @Mock
    private var match: Match = Match()

    @Mock
    private lateinit var repositoryLogo: DataSource<String>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchModel = spy(DetailMatchViewModel(repository, repositoryLogo))
    }


    @Test
    fun setDetailMatchTest() {
        val id = "4328"
        val matchTest = Match()
        matchModel.setDetailMatch(id, match = matchTest)
        // ketika setEventNextMatch dijalakan akan muncul loading => isViewLoading = true
        Assert.assertEquals(matchModel.isViewLoading.value, true)
        argumentCaptor<OperationCallback<Match>>().apply {
            verify(repository).retrieveData(eq(id), capture())
            firstValue.onSuccess(match)
            Assert.assertEquals(matchModel.getDetailMatch().value, match)
        }
        //ketika data sudah didapatkan maka laoding akan  di stop => isViewLoading = false
        Assert.assertEquals(matchModel.isViewLoading.value, false)

    }

}