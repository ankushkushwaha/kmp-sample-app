package com.example

import com.example.kmp_sample_app.android.PalindromeChecker
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PalindromeCheckerTest {

    private val checker = PalindromeChecker()

    @Test
    fun testEmptyStringIsNotPalindrome() {
        assertFalse(checker.isPalindrome(""))
    }

    @Test
    fun testSingleCharacterIsPalindrome() {
        assertTrue(checker.isPalindrome("a"))
    }

    @Test
    fun testSimplePalindrome() {
        assertTrue(checker.isPalindrome("madam"))
    }

    @Test
    fun testMixedCasePalindrome() {
        assertTrue(checker.isPalindrome("RaceCar"))
    }

    @Test
    fun testPalindromeWithPunctuation() {
        assertTrue(checker.isPalindrome("A man, a plan, a canal: Panama"))
    }

    @Test
    fun testNonPalindrome() {
        assertFalse(checker.isPalindrome("hello"))
    }
}
