package com.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.criminalintent.databinding.ListItemCrimeBinding
import java.util.UUID

/*
* RecyclerView никогда не создает представление (View)
* Для этого всегда создается ViewHolder
*/
/*
    Передаем корневое представление в качестве аргумента конструктору RecyclerView.ViewHolder
    Затем базовый класс ViewHolder будет удерживать представление в свойстве itemView
*/
class CrimeHolder(
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {

//      Лямбда (onCrimeClicked) вызывается при нажании на элемент списка
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.root.setOnClickListener {
            /*Toast.makeText(binding.root.context, "${crime.title} clicked!", Toast.LENGTH_SHORT)
                .show()*/
            onCrimeClicked(crime.id)
        }
        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}


/*  CrimeListAdapter знает все детали об элементах в списке.
    Адаптер также знает о списке преступлений, который поддерживает RecyclerView*/
class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit
) : RecyclerView.Adapter<CrimeHolder>() {

    /*  Adapter.onCreateViewHolder(...) отвечает за создание привязки к отображению, оболочку представления
        в держателе представления (ViewHolder) и возврат результата.
        Здесь "раздувается" (inflate) и привязывается ListItemCrimeBinding и передается результирующая привязка (binding) новому экземпляру CrimeHolder.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding)
    }

    /*  Adapter.onBindViewHolder(...) отвечает за заполнение данного держателя (holder) преступлением из заданной позиции.
        В этом случае происходит получение преступления из списка по запрашиваемой позиции.
        Затем используется заголовок и дата преступления, чтобы установить текстовую инф. в соответствующих текстовых представлениях.*/
    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        /*holder.apply {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
        }*/
        holder.bind(crime, onCrimeClicked)
    }

    /*  Сообщаем RecyclerView, сколько элементов находится в наборе данных
        Здесь - количество элементов в заданном массиве (будет переделано)*/
    override fun getItemCount() = crimes.size
}