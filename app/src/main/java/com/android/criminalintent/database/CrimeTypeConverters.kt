package com.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date

//      Создание отедльного класса для преобразования значений (дата) в примитивные и обратно
class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}