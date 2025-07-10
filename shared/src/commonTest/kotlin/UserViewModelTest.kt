import Presentation.UserViewModel
import data.Model.User
import data.Network.UserRepository
import data.Storage
import data.UserSettingsManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class UserViewModelTest {

    private val testUsers = listOf(
        User(id = 1, name = "Alice", username = "alice", email = "alice@example.com"),
        User(id = 2, name = "Bob", username = "bob", email = "bob@example.com")
    )

    class FakeUserRepository(
        private val shouldFail: Boolean = false,
        private val usersToReturn: List<User> = emptyList()
    ) : UserRepository() {
        override suspend fun fetchUsers(): List<User> {
            if (shouldFail) throw RuntimeException("Simulated failure")
            return usersToReturn
        }
    }

    class TestUserSettingsManager(storage: Storage) : UserSettingsManager(storage)

    @Test
    fun `fetchUsers updates state on success`() = runTest {
        val storage = FakeStorage()
        val repo = FakeUserRepository(usersToReturn = testUsers)
        val manager = TestUserSettingsManager(storage)
        val viewModel = UserViewModel(repo, manager)

        viewModel.fetchUsers()
        delay(100)

        val state = viewModel.viewStateValue
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertEquals(testUsers, state.users)
        assertEquals(emptyList(), state.filteredUsers)
        assertEquals(1, manager.getCount())
    }

    @Test
    fun `fetchUsers sets errorMessage on failure`() = runTest {
        val storage = FakeStorage()
        val repo = FakeUserRepository(shouldFail = true)
        val manager = TestUserSettingsManager(storage)
        val viewModel = UserViewModel(repo, manager)

        viewModel.fetchUsers()
        delay(100)

        val state = viewModel.viewStateValue
        assertFalse(state.isLoading)
        assertEquals("Unknown error", state.errorMessage)
        assertTrue(state.users.isEmpty())
        assertEquals(1, manager.getCount())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChanged filters correctly`() = runTest {
        val storage = FakeStorage()
        val repo = FakeUserRepository(usersToReturn = testUsers)
        val manager = TestUserSettingsManager(storage)
        val viewModel = UserViewModel(repo, manager)

        viewModel.fetchUsers()
        advanceUntilIdle() // replaces delay(100)

        viewModel.onSearchQueryChanged("bob")
        advanceUntilIdle() // replaces delay(1000)

        val filtered = viewModel.viewStateValue.filteredUsers
        assertEquals(1, filtered.size)
        assertEquals("Bob", filtered.first().name)
    }

}
