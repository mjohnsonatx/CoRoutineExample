package com.example.coroutineexample

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute: Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener{
            showProgressDialog()
            lifecycleScope.launch{
                execute("Task executed successfully")
            }
        }
    }

    // suspend has to be called to let the compiler know this is a function that
    // will use a different thread
    // internally, withContext has to be called and the function definition has to be
    // placed inside withContext
    private suspend fun execute(result:String){
        // moves task to different thread
        withContext(Dispatchers.IO){
            for(i in 1..100000){
                Log.e("delay: ", "" + i )
            }
            cancelProgressDialog()
            runOnUiThread{
                Toast.makeText(this@MainActivity,result, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cancelProgressDialog(){
        if(customProgressDialog != null){
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this)

        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        customProgressDialog?.show()
    }
}