//
//  View+Extension.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 21/06/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation

import SwiftUI

extension View {
    func pushView(destination:some View, isPresented:Binding<Bool>) -> (some View)? {
        if #available(iOS 16, macOS 13, tvOS 16, watchOS 9, *) {
            return navigationDestination(isPresented: isPresented) {
                destination
            }
        }else {
            return background(
              NavigationLink(
                destination: destination,
                isActive: isPresented
              )
              {
                EmptyView()
              }
            )
        }
    }
}
