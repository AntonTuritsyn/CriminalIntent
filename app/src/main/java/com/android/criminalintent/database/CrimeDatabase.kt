package com.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.criminalintent.Crime

/*      Аннотация @Database сообщает Room, что данный класс представляет базу данных в приложении
        Сама аннотация требует двух параметров:
        Первый параметр представляет собой список классов сущностей, который сообщает Room о том, какие классы сущностей использовать при создании таблиц для этой БД и управлении ими.
        В этом случае вы проходите только класс Crime, так как это единственная сущность в приложении.
        Второй параметр — версия базы данных*/

/*      С помощью аннотации @TypeConverters и передавая класс CrimeTypeConverters явным образом добавляется конвертер в класс БД.*/
@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase: RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}