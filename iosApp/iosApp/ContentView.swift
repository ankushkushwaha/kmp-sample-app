import SwiftUI
import shared
import KMPObservableViewModelSwiftUI
import KMPObservableViewModelCore

struct ContentView: View {
    
    @StateViewModel var viewModel = CommonKoinHelper().userViewModel
    @StateObject var searchViewModel = SearchViewModel()
    @State private var searchText: String = ""
    
    var body: some View {
        VStack {
            
            TextField("Search users...", text: $searchText)
                .padding(8)
                .background(Color(.systemGray6))
                .cornerRadius(10)
                .padding(.horizontal)
            
            if state.isLoading {
                ProgressView("Loading...")
            } else if let error = state.errorMessage {
                Text("Error: \(error)")
            } else {
                List(filteredUsers, id: \.id) { user in
                    VStack(alignment: .leading) {
                        Text(user.name).font(.headline)
                        Text(user.email).font(.subheadline).foregroundColor(.gray)
                    }
                }
            }
        }
        .onAppear {
            viewModel.fetchUsers()
        }
    }
    
    var state: UserViewModel.ViewState {
        viewModel.viewStateValue
    }
    
    var filteredUsers: [User] {
        if searchText.isEmpty {
            return state.users
        } else {
            return state.users.filter {
                $0.name.lowercased().contains(searchText.lowercased()) ||
                $0.email.lowercased().contains(searchText.lowercased())
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
