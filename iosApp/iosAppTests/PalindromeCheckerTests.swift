//
//  PalindromeCheckerTests.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 07/08/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Testing
@testable import iosApp

struct PalindromeCheckerTests {
    private let checker = PalindromeChecker()

    @Test
    func singleCharacterIsPalindrome() {
        #expect(checker.isPalindrome("a"))
    }

    @Test
    func simplePalindrome() {
        #expect(checker.isPalindrome("madam"))
    }

    @Test
    func mixedCasePalindrome() {
        #expect(checker.isPalindrome("RaceCar"))
    }

    @Test
    func palindromeWithPunctuation() {
        #expect(checker.isPalindrome("A man, a plan, a canal: Panama"))
    }

    @Test
    func nonPalindrome() {
        #expect(!checker.isPalindrome("hello"))
    }
}
