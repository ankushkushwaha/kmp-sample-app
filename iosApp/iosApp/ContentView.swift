import SwiftUI
import Shared
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
            
            Spacer()
            
            if state.isLoading {
                ProgressView("Loading...")
            } else if let error = state.errorMessage {
                Text("Error: \(error)")
            } else {
                List(state.filteredUsers, id: \.id) { user in
                    VStack(alignment: .leading) {
                        Text(user.name).font(.headline)
                        Text(user.email).font(.subheadline).foregroundColor(.gray)
                    }
                }
            }
        }
        .onChange(of: searchText) { searchText in
            viewModel.onSearchQueryChanged(query: searchText)
        }
        .onAppear {
            viewModel.fetchUsers()
        }
    }
    
    var state: UserViewModel.ViewState {
        viewModel.viewStateValue
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
