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
    @State private var selectedOption: ContentViewModel.ContentOptions? = nil
    @State private var pushToScreen = false

    var body: some View {
        NavigationStack {
            List(state.options, id: \.self) { option in
                Button(action: {
                    selectedOption = option
                    pushToScreen = true
                }) {
                    Text(option.name)
                        .foregroundColor(.primary)
                }
            }
            .listStyle(.plain)
            .pushView(destination: destinationView(), isPresented: $pushToScreen)
            .navigationTitle("Main Screen")
        }
    }

    var state: ContentViewModel.ViewState {
        viewModel.viewStateValue
    }

    @ViewBuilder
    private func destinationView() -> some View {
        switch selectedOption {
        case .userList:
            UserListView()
        case .todos:
            EmptyView()
        case .none:
            EmptyView()
        default:
            EmptyView()
        }
    }
}

