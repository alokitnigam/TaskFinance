package com.example.financerepublicassign.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.financerepublicassign.Model.StockModel
import com.example.financerepublicassign.Model.UserModel


@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upateStocks(stocks:List<StockModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user:UserModel):Long
    @Query("UPDATE UserModel SET saved = '1' WHERE id = :id")
    fun addedEdited(id:Long)

    @Query("Select * from StockModel where userid = :id")
    fun getStocks( id: Long) : List<StockModel>


    @Query("Select * from UserModel ")
    fun getUserStocks() : LiveData<List<UserModel>>

}
