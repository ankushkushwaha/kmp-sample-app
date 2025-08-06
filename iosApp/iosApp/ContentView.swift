//
//  UserListView.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 21/06/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI
import KMPObservableViewModelCore

struct ContentView: View {
    @StateViewModel var viewModel = CommonKoinHelper().contentViewModel
    @State private var pushToScreen = false

    var body: some View {
        List(state.options, id: \.self) { option in
            Button(action: {
                viewModel.selectOption(option: option)
            }) {
                Text(option.displayName)
                    .foregroundColor(state.selectedOption == option ? .green : .black)
            }
        }
        .listStyle(.plain)
        .pushView(destination: destinationView(), isPresented: $pushToScreen)
        .navigationTitle("Main Screen")
        .onChange(of: state.selectedOption) { option in
            if option == nil {
                pushToScreen = false
            } else {
                pushToScreen = true
            }
        }
        .onChange(of: pushToScreen) { pushToScreen in
            if pushToScreen == false {
                viewModel.selectOption(option: nil)
            }
        }

    }

    var state: ContentViewModel.ViewState {
        viewModel.viewStateValue
    }

    @ViewBuilder
    private func destinationView() -> some View {
        switch state.selectedOption {
        case .userList:
            UserListView()
        case .todos:
            TodoListView()
        case .calculatorIndependentModule:
            CalculatorView()
        default:
            EmptyView()
        }
    }
}

