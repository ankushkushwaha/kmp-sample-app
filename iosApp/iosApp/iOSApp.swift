import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitWithSharedModules(iOSModule: "iOSModule")
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
