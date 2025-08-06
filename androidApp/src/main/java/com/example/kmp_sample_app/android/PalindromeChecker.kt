package com.example.kmp_sample_app.android

class PalindromeChecker {

    fun isPalindrome(input: String): Boolean {
        val normalized = input
            .lowercase()
            .filter { it.isLetterOrDigit() }

        if (normalized.isEmpty()) return false
        if (normalized.length == 1) return true

        return normalized == normalized.reversed()
    }
}
