package com.example.calculator

import com.example.calculator.data.model.CurrencyResponse
import com.example.calculator.data.remote.CurrencyApiService
import com.example.calculator.data.repository.ExchangeRateRepositoryImpl
import com.example.calculator.domain.model.Country
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExchangeRateRepositoryTest {

    private val mockApiService = mockk<CurrencyApiService>()
    private val repository = ExchangeRateRepositoryImpl(mockApiService)

    private val validQuotes = mapOf(
        "USDKRW" to 1340.5,
        "USDJPY" to 150.2,
        "USDPHP" to 57.3
    )

    @Test
    fun `정상 응답 시 Country를 키로 하는 환율 맵을 반환한다`() = runTest {
        coEvery { mockApiService.getRates() } returns CurrencyResponse(true, validQuotes)

        val result = repository.getExchangeRates()

        assertEquals(1340.5, result[Country.KOREA]!!, 0.001)
        assertEquals(150.2, result[Country.JAPAN]!!, 0.001)
        assertEquals(57.3, result[Country.PHILIPPINES]!!, 0.001)
    }

    @Test
    fun `success가 false이면 예외를 던진다`() = runTest {
        coEvery { mockApiService.getRates() } returns CurrencyResponse(false, emptyMap())

        val result = runCatching { repository.getExchangeRates() }
        assertTrue(result.isFailure)
    }

    @Test
    fun `응답에 특정 통화 키가 없으면 예외를 던진다`() = runTest {
        val incompleteQuotes = mapOf("USDKRW" to 1340.5)
        coEvery { mockApiService.getRates() } returns CurrencyResponse(true, incompleteQuotes)

        val result = runCatching { repository.getExchangeRates() }
        assertTrue(result.isFailure)
    }

    @Test
    fun `네트워크 예외는 그대로 전파된다`() = runTest {
        coEvery { mockApiService.getRates() } throws RuntimeException("네트워크 오류")

        val result = runCatching { repository.getExchangeRates() }
        assertTrue(result.exceptionOrNull() is RuntimeException)
    }
}
