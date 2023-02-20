package br.com.schmidt.appwithtdd.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {

    @get:Rule
    var coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
}