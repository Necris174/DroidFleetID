package com.example.droidfleetid.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.droidfleetid.R
import com.example.droidfleetid.data.ApiFactory
import com.example.droidfleetid.data.SignInRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val json = JSONObject()
            json.put("jsonrpc","2.0")
            json.put("method","signin")
        val jsonparam = JSONObject().put("login", "genadich74").put("password","genadich")
            json.put("params", jsonparam)

            Log.d("TEST_OF_LOADING_DATA", json.toString())

        val disposable = ApiFactory.service.getAccessToken("application/json"
            ,"application/json"
            ,"drivertask"
            ,SignInRequest("2.0","signin",
                mutableMapOf("login" to "genadich74", "password"  to "genadich")))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            Log.d("TEST_OF_LOADING_DATA",it.toString())
            },{
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
            }
            )
        compositeDisposable.addAll(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}