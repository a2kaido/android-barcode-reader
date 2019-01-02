package io.github.a2kaido.barcode.reader.di

import io.github.a2kaido.barcode.reader.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val myModule = module {
    viewModel { HomeViewModel() }
}