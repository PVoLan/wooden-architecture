package ru.pvolan.archsample.storage.tools.db.room.mig.dto;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//This is a fake. The only reason for this is that we query MigrationDTOs (empty list) to force
//room  migration on init
@Entity
public class MigrationDTO {

    @PrimaryKey
    public int id;

}
