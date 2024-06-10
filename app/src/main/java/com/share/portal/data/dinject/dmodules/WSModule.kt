package com.share.portal.data.dinject.dmodules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WSModule {

  @Provides
  fun provideServerSocket(): ServerSocket {
    return ServerSocket(0)
  }

  @Provides
  fun provideClientSocket(): Socket {
    return Socket()
  }
}