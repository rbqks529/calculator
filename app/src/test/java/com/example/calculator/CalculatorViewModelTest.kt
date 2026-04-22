package com.example.calculator

import com.example.calculator.data.repository.ExchangeRateRepository
import com.example.calculator.domain.model.Country
import com.example.calculator.viewmodel.CalculatorViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {

    private val mockRepository = mockk<ExchangeRateRepository>()
    private lateinit var viewModel: CalculatorViewModel

    private val testRates = mapOf(
        Country.KOREA to 1340.5,
        Country.JAPAN to 150.2,
        Country.PHILIPPINES to 57.3
    )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { mockRepository.getExchangeRates() } returns testRates
        viewModel = CalculatorViewModel(mockRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `초기 상태에서 환율이 로드된다`() = runTest {
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(1340.5, state.exchangeRates[Country.KOREA]!!, 0.001)
    }

    @Test
    fun `국가 변경 시 환율이 재조회된다`() = runTest {
        viewModel.onCountrySelected(Country.JAPAN)

        coVerify(exactly = 2) { mockRepository.getExchangeRates() }
        assertEquals(Country.JAPAN, viewModel.uiState.value.selectedCountry)
    }

    @Test
    fun `유효한 입력 시 변환 결과가 계산된다`() = runTest {
        viewModel.onAmountChanged("100")

        val state = viewModel.uiState.value
        assertEquals("134,050.00", state.conversionResult)
        assertNull(state.inputError)
    }

    @Test
    fun `최솟값 1 입력 시 결과가 표시된다`() = runTest {
        viewModel.onAmountChanged("1")

        assertNull(viewModel.uiState.value.inputError)
        assertNotNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `최댓값 10000 입력 시 결과가 표시된다`() = runTest {
        viewModel.onAmountChanged("10000")

        assertNull(viewModel.uiState.value.inputError)
        assertNotNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `0 입력 시 에러 메시지가 표시된다`() = runTest {
        viewModel.onAmountChanged("0")

        assertEquals("송금액이 바르지 않습니다", viewModel.uiState.value.inputError)
        assertNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `10001 입력 시 에러 메시지가 표시된다`() = runTest {
        viewModel.onAmountChanged("10001")

        assertEquals("송금액이 바르지 않습니다", viewModel.uiState.value.inputError)
    }

    @Test
    fun `문자열 입력 시 에러 메시지가 표시된다`() = runTest {
        viewModel.onAmountChanged("abc")

        assertEquals("송금액이 바르지 않습니다", viewModel.uiState.value.inputError)
    }

    @Test
    fun `빈 입력 시 결과와 에러가 모두 null이다`() = runTest {
        viewModel.onAmountChanged("100")
        viewModel.onAmountChanged("")

        assertNull(viewModel.uiState.value.conversionResult)
        assertNull(viewModel.uiState.value.inputError)
    }

    @Test
    fun `네트워크 오류 시 networkError 상태가 설정된다`() = runTest {
        coEvery { mockRepository.getExchangeRates() } throws RuntimeException("연결 실패")
        viewModel.refreshRates()

        assertNotNull(viewModel.uiState.value.networkError)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `국가 변경 시 해당 국가 환율로 재계산된다`() = runTest {
        viewModel.onAmountChanged("100")
        viewModel.onCountrySelected(Country.JAPAN)

        val state = viewModel.uiState.value
        assertEquals("15,020.00", state.conversionResult)
    }
}
