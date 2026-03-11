package com.example.hubtrackerapp.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hubtrackerapp.data.db.dao.friends.FriendRequestDao
import com.example.hubtrackerapp.data.db.dao.HabitDao
import com.example.hubtrackerapp.data.db.dao.HabitProgressDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeHabitDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeInvitationDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeParticipantDao
import com.example.hubtrackerapp.data.db.dao.challenge.UserCurrentChallengeDao
import com.example.hubtrackerapp.data.db.dao.club.ClubDao
import com.example.hubtrackerapp.data.db.dao.club.ClubFeedDao
import com.example.hubtrackerapp.data.db.dao.club.ClubStatsDao
import com.example.hubtrackerapp.data.db.dao.club.UserInClubDao
import com.example.hubtrackerapp.data.db.dao.friends.FriendDao
import com.example.hubtrackerapp.data.db.dao.statistic.UserActionDao
import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.data.db.model.UserDbModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeHabitDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeInvitationDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeParticipantDBModel
import com.example.hubtrackerapp.data.db.model.challenge.UserCurrentChallengeDBModel
import com.example.hubtrackerapp.data.db.model.club.ClubDBModel
import com.example.hubtrackerapp.data.db.model.club.ClubFeedDBModel
import com.example.hubtrackerapp.data.db.model.club.ClubStatsDBModel
import com.example.hubtrackerapp.data.db.model.club.UserInClubDBModel
import com.example.hubtrackerapp.data.db.model.friends.FriendDBModel
import com.example.hubtrackerapp.data.db.model.friends.FriendRequestDBModel
import com.example.hubtrackerapp.data.db.model.statistic.UserActionDBModel

@Database(
    entities = [
        UserDbModel::class,
        HabitDbModel::class,
        HabitProgressDbModel::class,
        FriendRequestDBModel::class,
        FriendDBModel::class,
        UserActionDBModel::class,
        ClubDBModel::class,
        ClubFeedDBModel::class,
        ClubStatsDBModel::class,
        UserInClubDBModel::class,
        ChallengeDBModel::class,
        ChallengeHabitDBModel::class,
        ChallengeInvitationDBModel::class,
        ChallengeParticipantDBModel::class,
        UserCurrentChallengeDBModel::class
               ],
    version = 4,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class HabitsDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao
    abstract fun habitProgressDao(): HabitProgressDao
    abstract fun friendRequestDao(): FriendRequestDao
    abstract fun friendDao(): FriendDao
    abstract fun userActionDao(): UserActionDao
    abstract fun clubDao(): ClubDao
    abstract fun clubFeedDao(): ClubFeedDao
    abstract fun clubStatsDao(): ClubStatsDao
    abstract fun userInClubDao(): UserInClubDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun challengeHabitDao(): ChallengeHabitDao
    abstract fun challengeInvitationDao(): ChallengeInvitationDao
    abstract fun challengeParticipantDao(): ChallengeParticipantDao
    abstract fun userCurrentChallengeDao(): UserCurrentChallengeDao

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