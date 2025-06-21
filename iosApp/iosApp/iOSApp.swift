import SwiftUI
import Shared
import KMPObservableViewModelCore

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitWithSharedModules(iOSModule: "iOSModule")
    }
	var body: some Scene {
		WindowGroup {
            NavigationStack {
                ContentView()
            }
		}
	}
}

extension Kmp_observableviewmodel_coreViewModel: ViewModel { }
