package com.bignerdranch.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstanse()

            // доступ к менеджеру фрагментов осуществляется через:
            supportFragmentManager
                .beginTransaction()                                                                 // создание и возврат экземпляра FragmentTransaction
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}