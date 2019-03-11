package me.shtanko.domian

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
abstract class UnitTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField val injectMocks = InjectMocksRule.create(this@UnitTest)
}