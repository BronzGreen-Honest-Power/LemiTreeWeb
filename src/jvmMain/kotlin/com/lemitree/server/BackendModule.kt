package com.lemitree.server

import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_DIR = "BASE_DIR"

val backendModule = module {
    single { Sesame("quackz") }
    single(named(BASE_DIR)) {
//        System.getenv("BASE_DIR_LEMITREE") ?: error("BASE_DIR_LEMITREE env not found!")
        "/home/d/repos/LemiTree/Human_Individual"
    }
}