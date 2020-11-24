package com.example.worktest

data class Japan (
    var regions: List<Chihou>? = null,
    var prefectures: List<Tdfk>? = null
)

data class Chihou (
    var id: Int = 0,
    var name: String? = null
)

data class Tdfk (
    var id: Int = 0,
    var name: String? = null,
    var capital: Kenchoshozaichi? = null,
    var region_id: Int = 0,
    var area: Float = 0F,
    var jinkoumitudo: Int = 0,
    var flowers: List<String> = emptyList(),
    var trees: List<String> = emptyList()
)

data class Kenchoshozaichi (
    var zip_code: String? = null,
    var address: String? = null,
    var name: String? = null,
    var city_type:Int = 0
)