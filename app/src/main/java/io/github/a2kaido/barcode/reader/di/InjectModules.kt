package io.github.a2kaido.barcode.reader.di

import androidx.room.Room
import io.a2kaido.barcode.reader.history.HistoryViewModel
import io.github.a2kaido.barcode.reader.HomeViewModel
import io.github.a2kaido.barcode.reader.QrCodeFactoryViewModel
import io.github.a2kaido.barcode.reader.data.BarcodeRepository
import io.github.a2kaido.barcode.reader.data.room.BarcodeDatabase
import io.github.a2kaido.barcode.reader.domain.BarcodeUseCase
import io.github.a2kaido.barcode.reader.domain.BarcodeUseCaseInterface
import io.github.a2kaido.barcode.reader.domain.data.BarcodeRepositoryInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val myModule = module {
    single { Room.databaseBuilder(androidApplication(), BarcodeDatabase::class.java, "barcode").build() }
    single { BarcodeRepository(get()) as BarcodeRepositoryInterface }
    single { BarcodeUseCase(get()) as BarcodeUseCaseInterface }
    viewModel { HomeViewModel(get()) }
    viewModel { io.a2kaido.barcode.reader.history.HistoryViewModel(get()) }
    viewModel { QrCodeFactoryViewModel() }
}