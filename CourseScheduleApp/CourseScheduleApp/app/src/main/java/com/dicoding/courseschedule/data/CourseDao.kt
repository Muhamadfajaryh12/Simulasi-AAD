package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {

    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>
    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>
    @Query("SELECT * FROM course where id = :id")
    fun getCourse(id: Int): LiveData<Course>
    @Query("SELECT * FROM course where day = :day")
    fun getTodaySchedule(day: Int): List<Course>
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Course::class)
    fun insert(course: Course)
    @Delete
    fun delete(course: Course)
    @Query("SELECT * FROM course order by :params")
    fun sort(params:String):DataSource.Factory<Int,Course>
}