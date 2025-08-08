//
//  PalindromeChecker.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 07/08/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Foundation

class PalindromeChecker {
    func isPalindrome(_ text: String) -> Bool {
        let cleaned = text.lowercased().filter { $0.isLetter || $0.isNumber }
        return cleaned == String(cleaned.reversed())
    }
}
