package bd.com.albin.media.di

import bd.com.albin.media.data.repository.MovieRepository
import bd.com.albin.media.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaModule {
    @Binds abstract fun provideMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}