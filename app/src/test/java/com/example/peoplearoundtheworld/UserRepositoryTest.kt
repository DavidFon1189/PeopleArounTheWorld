package com.example.peoplearoundtheworld

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.peoplearoundtheworld.data.RestDataSource
import com.example.peoplearoundtheworld.model.User
import com.example.peoplearoundtheworld.model.UserDao
import com.example.peoplearoundtheworld.repository.UserRespositoryImp
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private val user = User("David", "Fonseca", "CDMX", "http://..")
private val user1 = User("Valeria", "Fonseca", "CDMX", "http://..")
private val user2 = User("Noah", "Fonseca", "CDMX", "http://..")
class UserRepositoryTest {

    private val mockWebServer = MockWebServer().apply {
        url("/")
        dispatcher = myDispatcher
    }


    private val restDataSource = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestDataSource::class.java)

    private val newRepository = UserRespositoryImp(restDataSource, MockUserDao())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Users is delete correctly`() {
        runBlocking {
            newRepository.deleteUser(user1)
            val users = newRepository.getAllUser()
            assertEquals(2, users.value?.size)
        }
    }

    @Test
    fun `Users on the DB are retrieved correctly`() {
        val users = newRepository.getAllUser()
        assertEquals(3, users.value?.size)
    }

    @Test
    fun `Users is fetched correctly`() {
        runBlocking {
            val newUser = newRepository.getNewUser()
            val users = newRepository.getAllUser()
            assertEquals(4, users.value?.size)
            assertEquals("Jandira", newUser.city)
            assert(newUser.thumbnail.contains("thumb/men/14.jpg"))
        }
    }
}

    class MockUserDao: UserDao {
        private val users = MutableLiveData(listOf(user, user1, user2))
        override fun insert(user: User) {
            users.value = users.value?.toMutableList()?.apply {  add(user) }
        }

        override fun getAll(): LiveData<List<User>> = users

        override fun delete(user: User) {
            users.value = users.value?.toMutableList()?.apply { remove(user) }
        }

    }

    val myDispatcher: Dispatcher = object : Dispatcher(){
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path){
                "/?inc=name" -> MockResponse().apply { addResponse("api_name.json")}
                "/?inc=location" -> MockResponse().apply { addResponse("api_location.json")}
                "/?inc=picture" -> MockResponse().apply { addResponse("api_picture.json")}
                else -> MockResponse().setResponseCode(404)
            }
        }
    }
    fun MockResponse.addResponse(filePhat: String): MockResponse{
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePhat)
        val source = inputStream?.source()?.buffer()
        source?.let {
            setResponseCode(200)
            setBody(it.readString(StandardCharsets.UTF_8))
        }
        return this
    }
