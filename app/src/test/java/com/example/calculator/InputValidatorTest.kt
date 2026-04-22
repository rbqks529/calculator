package com.example.calculator

import com.example.calculator.domain.InputValidator
import com.example.calculator.domain.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {

    @Test
    fun `빈 문자열이면 Empty를 반환한다`() {
        assertEquals(ValidationResult.Empty, InputValidator.validate(""))
    }

    @Test
    fun `공백 문자열이면 Empty를 반환한다`() {
        assertEquals(ValidationResult.Empty, InputValidator.validate("   "))
    }

    @Test
    fun `최솟값 1은 Valid를 반환한다`() {
        val result = InputValidator.validate("1")
        assertTrue(result is ValidationResult.Valid)
        assertEquals(1.0, (result as ValidationResult.Valid).amount, 0.001)
    }

    @Test
    fun `최댓값 10000은 Valid를 반환한다`() {
        val result = InputValidator.validate("10000")
        assertTrue(result is ValidationResult.Valid)
        assertEquals(10000.0, (result as ValidationResult.Valid).amount, 0.001)
    }

    @Test
    fun `소수 입력도 범위 내면 Valid를 반환한다`() {
        val result = InputValidator.validate("1000.5")
        assertTrue(result is ValidationResult.Valid)
        assertEquals(1000.5, (result as ValidationResult.Valid).amount, 0.001)
    }

    @Test
    fun `0은 Invalid를 반환한다`() {
        assertEquals(ValidationResult.Invalid, InputValidator.validate("0"))
    }

    @Test
    fun `0_99는 Invalid를 반환한다`() {
        assertEquals(ValidationResult.Invalid, InputValidator.validate("0.99"))
    }

    @Test
    fun `10000_01은 Invalid를 반환한다`() {
        assertEquals(ValidationResult.Invalid, InputValidator.validate("10000.01"))
    }

    @Test
    fun `음수는 Invalid를 반환한다`() {
        assertEquals(ValidationResult.Invalid, InputValidator.validate("-1"))
    }

    @Test
    fun `문자열은 Invalid를 반환한다`() {
        assertEquals(ValidationResult.Invalid, InputValidator.validate("abc"))
    }
}
