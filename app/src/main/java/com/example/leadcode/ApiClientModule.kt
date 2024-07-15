package com.example.leadcode

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.example.clientsdk.MyQuestionsAPIClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object ApiClientModule {
//    @Singleton
//    @Provides
//    fun provideMyQuestionsAPIClient(): MyQuestionsAPIClient{
//
//
//        val apiClientFactory = ApiClientFactory()
//        // create a client
//        return apiClientFactory.build(MyQuestionsAPIClient::class.java)
//
//    }
//
//
//
//
//}