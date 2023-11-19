package com.share.portal.data.dinject.dmodules

import dagger.Module
import dagger.Provides
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Singleton

@Module
class WSModule {

  @Provides
  fun provideWSEars(): ServerSocket {
    return ServerSocket(0)
  }

  @Provides
  fun provideWSMouth(): Socket {
    return Socket()
  }
}