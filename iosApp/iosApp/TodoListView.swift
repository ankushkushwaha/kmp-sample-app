//
//  TodoListView.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 21/06/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI
import KMPObservableViewModelCore

struct TodoListView: View {
    @StateViewModel var viewModel = CommonKoinHelper().todoViewModel
    @State private var newTodo: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            TextField("Enter new todo", text: $newTodo)
                .textFieldStyle(.roundedBorder)
                .padding(.horizontal)
            
            Button("Add") {
                viewModel.addTodo(title: newTodo)
                newTodo = ""
            }
            .frame(maxWidth: .infinity, alignment: .trailing)
            .padding(.horizontal)
            
            List {
                ForEach(viewModel.viewStateValue.todos, id: \.id) { todo in
                    TodoRow(todo: todo) {
                        viewModel.toggleTodo(todo: todo)
                    }
                }
            }
        }
    }
}

struct TodoRow: View {
    let todo: Todo
    let onToggle: () -> Void
    
    var body: some View {
        HStack {
            Text(todo.title)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            if todo.is_done == 1 {
                Image(systemName: "checkmark.circle.fill")
                    .foregroundColor(.green)
            } else {
                Image(systemName: "circle")
                    .foregroundColor(.gray)
            }
        }
        .padding(.vertical, 8)
        .contentShape(Rectangle()) // make the full row tappable
        .onTapGesture {
            onToggle()
        }
    }
}

