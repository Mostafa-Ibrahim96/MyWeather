package com.example.weather.data

open class DataException(message: String?) : Exception(message) {

    class LocationNotAvailableException :
        DataException("Location not available")

    class NetworkException(message: String? = "Network error") :
        DataException(message)

    class ParsingException(message: String? = "Error parsing data") :
        DataException(message)

    class UnknownDataException(message: String? = "Unknown data error") :
        DataException(message)
}