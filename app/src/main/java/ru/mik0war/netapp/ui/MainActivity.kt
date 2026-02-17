package ru.mik0war.netapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.mik0war.netapp.data.cloud.NetApp
import ru.mik0war.netapp.data.cloud.ServerDTO
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

        lifecycleScope.launch {

            NetApp.api.createItem(ServerDTO("new item", 5))
            NetApp.api.getItem(0)
            NetApp.api.getItems()

        }


    }
}