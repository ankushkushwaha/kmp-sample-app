import SwiftUI
import Combine

class SearchViewModel: ObservableObject {
    
    @Published var searchText = ""


    private var cancellables = Set<AnyCancellable>()
    private var searchRequestCancellable: AnyCancellable?

    init() {
        setupSearch()
    }
    
    private func setupSearch() {
        $searchText
            .debounce(for: .milliseconds(500), scheduler: DispatchQueue.main)
            .removeDuplicates()
            .filter { $0.count >= 3 }
            .sink { [weak self] text in
                self?.performSearch(for: text)
            }
            .store(in: &cancellables)
    }
    
    private func performSearch(for query: String) {
        
    }
}
