package com.tom.weather

import com.tom.weather.location.locationModule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest: KoinTest {
    @Test
    fun `check all koin modules`() {
        locationModule.verify()
    }
}
