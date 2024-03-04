package ru.pvolan.archsample.storage.tools.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.pvolan.archsample.storage.forecast.CityDTO;
import ru.pvolan.archsample.storage.forecast.ForecastDTO;
import ru.pvolan.archsample.storage.forecast.ForecastDao;
import ru.pvolan.archsample.storage.tools.db.room.mig.Migration1to2;
import ru.pvolan.archsample.storage.tools.db.room.mig.dto.MigrationDTO;
import ru.pvolan.archsample.storage.tools.db.room.mig.dto.MigrationDTODao;


@Database(entities =
        {
                MigrationDTO.class,
                CityDTO.class,
                ForecastDTO.class
        },
        version = 1, exportSchema = false)
public abstract class RoomDatabaseImpl extends RoomDatabase {


    public static RoomDatabaseImpl create(Context appContext) {
        return Room.databaseBuilder(appContext,
                RoomDatabaseImpl.class, "room-database")
                .addMigrations(
                        new Migration1to2()
                )
                .build();
    }

    public abstract MigrationDTODao getMigrationDTODao();

    public abstract ForecastDao getForecastDao();
}
