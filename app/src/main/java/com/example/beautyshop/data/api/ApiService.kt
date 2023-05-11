package com.example.beautyshop.data.api

import com.example.beautyshop.data.models.LoginResponse
import com.example.beautyshop.data.models.MessageResponse
import com.example.beautyshop.models.LoginRequest
import com.example.beautyshop.models.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login/")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("registration/")
    suspend fun registration(@Body body: RegistrationRequest): MessageResponse

//    @GET("account/")
//    fun getProfile(): Call<ProfileResponse>
//
//    @POST("account/image/")
//    fun updateImageProfile(
//        @Body body: UpdateImageProfileModel
//    ): Call<Any>
//
//    @GET("cinema/")
//    suspend fun getCinema(): CinemaResponse
//
//    @GET("favourites/")
//    fun getFavouriteMovies(): Call<FavouriteMoviesResponse>
//
//    @POST("favourites/")
//    fun saveFavouriteMovie(@Body body: FavouriteMovieRequest): Call<FavouriteMoviesResponse>
}