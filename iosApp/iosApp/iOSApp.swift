import SwiftUI
import Shared
import KMPObservableViewModelCore

@main
struct iOSApp: App {
    init() {
        
        KoinHelperKt.doInitWithSharedModules(iOSModule: "iOSModuleTestString")
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
