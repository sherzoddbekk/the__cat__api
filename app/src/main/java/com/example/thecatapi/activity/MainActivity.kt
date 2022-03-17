package com.example.thecatapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.thecatapi.R
import com.example.thecatapi.fragment.FragmentAllCat
import com.example.thecatapi.fragment.FragmentMyCat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    lateinit var floatActionButton:FloatingActionButton
    lateinit var allCat:TextView
    lateinit var myCat:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView(){
        floatActionButton = findViewById(R.id.fab_bottom_appbar)
        floatActionButton.setOnClickListener {
            openDetails()
        }
        allCat = findViewById(R.id.all_cats)
        myCat = findViewById(R.id.my_cats)

        replaceFragment(FragmentAllCat())
        allCat.setOnClickListener {
            FragmentAllCat()
        }
        myCat.setOnClickListener {
            FragmentMyCat()
        }

    }

    private fun openDetails(){
        val intent = Intent(this,DetailsActivity::class.java)
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) {
            val ft = manager.beginTransaction()
            ft.replace(R.id.frame, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }

    }

}