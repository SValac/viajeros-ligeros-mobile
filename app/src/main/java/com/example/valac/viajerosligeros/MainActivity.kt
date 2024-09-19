package com.example.valac.viajerosligeros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.valac.viajerosligeros.db.DestinosDAO
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mis_viajes)

        startActivity<MisViajes>()

        /*registroDestinoBtn.setOnClickListener {
            startActivity<MisViajes>()
        }


        test_dao.setOnClickListener{v: View? ->
            val dao = DestinosDAO(applicationContext)
            val list = dao.getAllDestinos()
            Log.d("DAO Lis", "${dao.getAllDestinos()}")
        }*/



    }

}
