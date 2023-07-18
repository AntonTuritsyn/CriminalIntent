package com.android.criminalintent

import android.content.Context
import androidx.room.Room
import com.android.criminalintent.database.CrimeDatabase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/*      CrimeRepository — синглтон. То есть в процессе приложения будет только один экземпляр. (Существует до тех пор, пока приложение находится в памяти)
        Хранение любых свойств в синглтоне сохранит их доступность на протяжении всех изменений жизненного цикла в активити и фрагментах.
        Синглтон не является решением для долгосрочного хранения данных.
        Вместо этого он дает приложению владельца данных о "преступлении" и предоставляет способ легко передавать эти данные между компонентами.*/

//      Конструктор помечается как частный, чтобы гарантировать, что никакие компоненты не смогут выйти из-под контроля и создать свой собственный экземпляр.
private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    /*      Room.databaseBuilder() создает конкретную реализацию абстрактной базы данных CrimeDatabase, используя три параметра.
            Сначала ему нужен объект Context, так как база данных обращается к файловой системе.
            Используется контекст приложения, потому что синглтон, скорее всего, проживет дольше, чем любой из классов активности.
            Второй параметр — это класс базы данных, который должен быть создан Room.
            Третий - это имя файла базы данных, который вы создаст Room.
            Используется private const val, определенная в этом же файле, так как никакие другие компоненты не нуждаются в доступе к ней.*/

    private val database: CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    /*      Затем заполняется CrimeRepository, чтобы другие компоненты могли выполнять любые необходимые операции с БД.
            Добавляются функции в репозиторий для каждой функции в DAO.*/

    /*      Flow используется, чтобы передать этот список преступлений*/
    fun getCrimes(): Flow<List<Crime>> = database.crimeDao().getCrimes()
    suspend fun getCrime(id: UUID): Crime = database.crimeDao().getCrime(id)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        //      fun initialize(...) инициализирует новый экземпляр репозитория
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }
        //      fun get()... обращается к репозиторию.

        /*      Выбрасывает исключение, если при старте приложения не был инициализирован репозиторий
                Необходимо вызвать initialize() в onCreate() субкласса Application */
        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}