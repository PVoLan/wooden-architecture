package ru.pvolan.archsample.storage.tools.db.room.mig.dto;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MigrationDTODao {

    @Query("SELECT * FROM MigrationDTO")
    List<MigrationDTO> loadAll();

}
