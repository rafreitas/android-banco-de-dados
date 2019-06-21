package com.tcc.maua.bancodedados

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title: String, Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    fun clearEditTexts(){
        editTextName.setText("")
        editTextGalaxy.setText("")
        editTextType.setText("")
        editTextID.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }

    fun handleInserts(){
        buttonInsert.setOnClickListener{
            try {
                dbHelper.insertData(editTextName.text.toString(), editTextGalaxy.text.toString(),
                    editTextType.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleUpdates(){
        buttonUpdate.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(editTextID.text.toString(),
                    editTextName.text.toString(),
                    editTextGalaxy.text.toString(), editTextType.text.toString())
                if(isUpdate == true)
                    showToast("Data Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleDeletes(){
        buttonDelete.setOnClickListener {
            try {
                dbHelper.deleteData(editTextID.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleViewing(){
        buttonViewAll.setOnClickListener {
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.count == 0){
                    showDialog("Error", "No Data Found")
                    return@OnClickListener
                }

                val buffer = StringBuffer()
                while (res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n")
                    buffer.append("NAME: " + res.getString(1) + "\n")
                    buffer.append("GALAXY: " + res.getString(2) + "\n")
                    buffer.append("TYPE: " + res.getString(3) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
            }
        }
    }
}

