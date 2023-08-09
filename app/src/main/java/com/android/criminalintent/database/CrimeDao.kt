package com.android.criminalintent.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.criminalintent.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/*      Создание интерфейса для доступа к БД (DAO - data access object)
        С двумя функциями запроса:
        одна для возврата списка всех преступлений в базе данных,
        другая для возврата одного преступления, соответствующего заданному UUID.*/

/*      Аннотация @Query указывает, что getCrimes() и getCrime(UUID) предназначены для извлечения информации из БД,
        а не для вставки, обновления или удаления элементов из БД.*/

/*      SELECT * FROM crime выводит все столбцы для всех строк таблицы crime.
        SELECT FROM crime WHERE id=(:id) запрашивает все столбцы только из строки с нужным идентификатором.*/
@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")

    /*      Flow, встроенный в библиотеку Coroutines, представляет собой асинхронный поток данных.
    Коллектор будет наблюдать за потоком и будет получать уведомления каждый раз, когда в потоке будет выдаваться новое значение.*/
    fun getCrimes(): Flow<List<Crime>>

    @Query("SELECT * FROM crime WHERE id =(:id)")
    suspend fun getCrime(id: UUID): Crime

    @Update
    suspend fun updateCrime(crime: Crime)

    @Insert
    suspend fun addCrime(crime: Crime)
}