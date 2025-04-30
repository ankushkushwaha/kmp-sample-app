import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()
    
    let vm = CommonKoinHelper().userViewModel

	var body: some View {
		Text(greet)
            .onAppear {
                vm.fetchUsers()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
