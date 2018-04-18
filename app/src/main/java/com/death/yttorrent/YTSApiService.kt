package com.death.yttorrent

import com.death.yttorrent.model.movies.Response
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YTSApiService {

    companion object {
        fun create(): YTSApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://yts.am/api/v2/")
                    .build()

            return retrofit.create(YTSApiService::class.java)
        }
    }


    @GET("list_movies.json")
    fun getMovies(@Query("minimum_rating") minimum_rating:Int,
                      @Query("limit") limit:Int,
                      @Query("page") page:Int) : Observable<Response>
}