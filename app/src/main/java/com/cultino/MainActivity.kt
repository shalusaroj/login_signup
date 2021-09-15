package com.cultino

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var camara_botton:Button
    private lateinit var Imagebox:ImageView
    private lateinit var name_view:TextView
    private lateinit var email_view:TextView
    private lateinit var image_view:ImageView
    private var flex:MutableLiveData<Int> = MutableLiveData(0)
    private var flex1:MutableLiveData<Int> =MutableLiveData(0)
    private var flex2:MutableLiveData<Int> =MutableLiveData(0)
     var  pic:Bitmap?=null;
    lateinit var  pic_view:Bitmap

    lateinit var encodedImage : String
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        name_view = findViewById(R.id.view_name)
        email_view = findViewById(R.id.view_email)
        image_view = findViewById(R.id.image_view)
        var submit = findViewById(R.id.submit)as Button
         camara_botton = findViewById(R.id.click_image)
        Imagebox = findViewById(R.id.image)
        submit.isEnabled = false
        var edtxt = findViewById(R.id.name)as EditText


        var e_mail = findViewById(R.id.match_mail)as EditText
        flex.observe(LifecycleOwner { lifecycle }, Observer { t ->enableButton(btn = submit)  })
        flex1.observe(LifecycleOwner { lifecycle }, Observer { t ->enableButton(btn = submit)  })
        flex2.observe(LifecycleOwner { lifecycle }, Observer { t ->enableButton(btn = submit)  })
        edtxt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
             //   TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             //   TODO("Not yet implemented")
                var name = edtxt.text
                if (name.isNotEmpty())
                    flex.value=1
                else{
                    edtxt.setError("Enter Your Name")
                    flex.value=0
                }
            }

            override fun afterTextChanged(s: Editable?) {
               // TODO("Not yet implemented")
            }

        })
        e_mail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(e_mail.text.toString()).matches()) {
                    flex1.value = 1
                    Log.d("#111","textchanged")
                }
                else{
                    e_mail.setError("Invalid E-mail")
                    flex1.value=0
                }
            }

            override fun afterTextChanged(s: Editable?) {
            //    TODO("Not yet implemented")
            }

        })

        camara_botton.isEnabled = false
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        }
        else
            camara_botton.isEnabled = true
        camara_botton.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,101)
        }
        val name1= sharedPreferences.getString("NAME","")
        name_view.text = name1
        val email1 = sharedPreferences.getString("EMAIL","")
        email_view.text = email1
        val image1 = sharedPreferences.getString("IMAGE","")
        val bytes: ByteArray = Base64.decode(image1, Base64.DEFAULT)
        pic_view= BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        image_view.setImageBitmap(pic_view)

        submit.setOnClickListener {
            name_view.text = "${edtxt.text}"
            email_view.text = "${e_mail.text}"
            image_view.setImageBitmap(pic)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME","${edtxt.text}")
            editor.putString("EMAIL","${e_mail.text}")
            editor.putString("IMAGE","${encodedImage}")
            editor.apply()
 }

        var bt1=findViewById<Button>(R.id.Activity2) as Button
        bt1.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)

            startActivity(intent)
        }

    }
fun enableButton(btn:Button){
    Log.d("#111","all True, ${flex.value}, ${flex1.value}, ${flex2.value} ")
    if(flex.value==1&&flex1.value==1&&flex2.value==1)
    btn.isEnabled=true
}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101){
              pic = data?.getParcelableExtra<Bitmap>("data")
            Imagebox.setImageBitmap(pic)
            flex2.value=1
            val baos = ByteArrayOutputStream()
            pic?.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
            val b: ByteArray = baos.toByteArray()
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            Log.d("#111","${encodedImage}")

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            camara_botton.isEnabled= true
        }
    }
}