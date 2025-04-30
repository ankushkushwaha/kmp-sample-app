package Network

import Model.User
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class UserRepository(private val client: HttpClient = createHttpClient()) {

    suspend fun fetchUsers(): List<User> {
        // Perform the network request and parse the JSON response
        return try {
            client.get("https://jsonplaceholder.typicode.com/users").body()
        } catch (e: Exception) {

            println("Exception: $e")
            emptyList() // or handle error accordingly
        }
    }
}

