package com.example.vk

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class CatalogItem(val id: Int, val name: String, val iconUrl: String? = null)
data class AppDetail(val id: Int, val name: String, val description: String? = null)

interface CatalogApi {
    @GET("catalog")
    fun getCatalog(): Call<List<CatalogItem>>

    @GET("{id}")
    fun getAppDetail(@Path("id") id: Int): Call<AppDetail>
}

object RetrofitClient {
    private const val BASE_URL = "http://185.103.109.134/"
    val api: CatalogApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatalogApi::class.java)
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        fetchCatalog()
    }

    private fun fetchCatalog() {
        RetrofitClient.api.getCatalog().enqueue(object : Callback<List<CatalogItem>> {
            override fun onResponse(call: Call<List<CatalogItem>>, response: Response<List<CatalogItem>>) {
                if (response.isSuccessful) Log.d("Network", "catalog: ${response.body()}")
                else Log.e("Network", "error: ${response.code()}")
            }
            override fun onFailure(call: Call<List<CatalogItem>>, t: Throwable) {
                Log.e("Network", "fail: ${t.message}")
            }
        })
    }

    private fun fetchAppDetail(id: Int) {
        RetrofitClient.api.getAppDetail(id).enqueue(object : Callback<AppDetail> {
            override fun onResponse(call: Call<AppDetail>, response: Response<AppDetail>) {
                if (response.isSuccessful) Log.d("Network", "detail: ${response.body()}")
            }
            override fun onFailure(call: Call<AppDetail>, t: Throwable) {
                Log.e("Network", "fail: ${t.message}")
            }
        })
    }
}