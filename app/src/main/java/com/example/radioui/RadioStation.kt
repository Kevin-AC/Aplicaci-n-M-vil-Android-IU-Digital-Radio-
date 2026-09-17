package com.example.radioui

data class RadioStation(
    val id: String,
    val name: String,
    val category: String,
    val isLive: Boolean = false,
    val listenersCount: String = "",
    val frequency: String = ""
)

// Lista de estaciones de prueba para la IU Digital Radio
val sampleStations = listOf(
    RadioStation(
        id = "1",
        name = "Emisión Central IU",
        category = "Institucional & Noticias",
        isLive = true,
        listenersCount = "1.2k oyentes",
        frequency = "98.4 FM"
    ),
    RadioStation(
        id = "2",
        name = "IU Rock & Alt",
        category = "Música & Cultura",
        isLive = false,
        listenersCount = "450 oyentes",
        frequency = "Streaming"
    ),
    RadioStation(
        id = "3",
        name = "Voces del Campus",
        category = "Podcasts & Entrevistas",
        isLive = true,
        listenersCount = "890 oyentes",
        frequency = "Streaming"
    ),
    RadioStation(
        id = "4",
        name = "IU Urbana",
        category = "Tendencias & Ritmos",
        isLive = false,
        listenersCount = "320 oyentes",
        frequency = "Streaming"
    )
)