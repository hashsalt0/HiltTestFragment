package com.test.hilttestfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.hilttestfragment.network.NetworkService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)