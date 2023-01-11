package com.codely.pollution.application

sealed class ReadPollutionError {
    class Unknown(val throwable: Throwable) : ReadPollutionError()
}
