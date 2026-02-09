package com.example.hubtrackerapp.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hubtrackerapp.data.db.dao.HabitDao
import com.example.hubtrackerapp.data.db.dao.HabitProgressDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.data.db.model.UserDbModel

@Database(
    entities = [
        UserDbModel::class,
        HabitDbModel::class,
        HabitProgressDbModel::class
               ],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class HabitsDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao
    abstract fun habitProgressDao(): HabitProgressDao

    companion object{

        private var instance: HabitsDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): HabitsDatabase {
            instance?.let { return it }

            synchronized(LOCK) {
                instance?.let { return it }

                return Room.databaseBuilder(
                    context = context,
                    klass = HabitsDatabase::class.java,
                    name = "habits.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("Database", "Database created")
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Log.d("Database", "Database opened")
                        }
                    })
                    .build()
                    .also {
                        instance = it
                        Log.d("Database", "Database instance created")
                    }
            }
        }
    }
}