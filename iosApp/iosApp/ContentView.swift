import SwiftUI
import shared
import KMPObservableViewModelSwiftUI
import KMPObservableViewModelCore

struct ContentView: View {
    
    @StateViewModel var viewModel = CommonKoinHelper().userViewModel
    
    var body: some View {
        VStack {
            if state.isLoading {
                ProgressView("Loading...")
            } else if let error = state.errorMessage {
                Text("Error: \(error)")
            } else {
                List(state.users, id: \.id) { user in
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
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
