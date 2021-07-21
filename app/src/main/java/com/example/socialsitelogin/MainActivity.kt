package com.example.socialsitelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    lateinit var callbackManager: CallbackManager
    private val EMAIL ="email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener {
            login_button.setReadPermissions(listOf(EMAIL))
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    val graphRequest = GraphRequest.newMeRequest(result?.accessToken){obj, response ->

                        try {
                            if (obj.has("id")) {
                                Log.d("FACEBOOKDATA", obj.getString("name"))
                                Log.d("FACEBOOKDATA", obj.getString("email"))
                                Log.d("FACEBOOKDATA", obj.getString("picture"))
                            }
                        }catch(e:Exception){

                    }

                    }
                    val param = Bundle()
                    param.putString("fields","name,email,id,picture.type(large)")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()
                }

                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
