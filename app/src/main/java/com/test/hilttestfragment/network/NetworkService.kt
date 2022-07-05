package com.test.hilttestfragment.network

class NetworkService {
    fun callApi(): CharSequence {
        return "Api call\n$fakeResponse"
    }

    var fakeResponse = "Dummy response"
}
