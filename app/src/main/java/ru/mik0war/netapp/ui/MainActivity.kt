package ru.mik0war.netapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mik0war.netapp.R
import ru.mik0war.netapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /*
        Объявление переменной все функции onCreate
        для использования её в рамках всего класса
    */
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация переменной
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Отображение корневого элемента.
        // В случае, если оставить старый вариант,
        // все настройки элментов, сделанные через
        // биндинг не будут применены к отображаемым
        // элементам
        setContentView(binding.root)
    }
}