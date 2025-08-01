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
                CalculatorView()
            }
		}
	}
}

extension Kmp_observableviewmodel_coreViewModel: ViewModel { }
