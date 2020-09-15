package ru.pvolan.archsample.storage.tools.db;

import android.content.Context;

import ru.pvolan.archsample.storage.tools.db.room.mig.dto.MigrationDTODao;
import ru.pvolan.archsample.storage.tools.db.room.RoomDatabaseImpl;
import ru.pvolan.tools.log.ALog;

public class DBInstance {


    private RoomDatabaseImpl roomDatabase;

    private Context appContext;

    public DBInstance(Context appContext) {
        this.appContext = appContext;
    }

    private final Object sync = new Object();

    //This method is expected to be called somewhere on the launcher screen
    //to warm up database and provide migration, when necessary
    //Launcher screen is not implemented in this sample
    public void warmUp() {
        getRoomDatabase();
    }

    public RoomDatabaseImpl getRoomDatabase() {
        synchronized (sync) {
            if (roomDatabase == null) {
                doMigration(); //It should be quick if not from launcher activity
            }
            return roomDatabase;
        }
    }

    private void doMigration(){
        ALog.log("DBInstance.doMigration");
        this.roomDatabase = RoomDatabaseImpl.create(appContext);
        ALog.log("DBInstance.doMigration end 1");
        MigrationDTODao dao = this.roomDatabase.getMigrationDTODao();
        ALog.log("DBInstance.doMigration end 2");
        ALog.log("size = " + dao.loadAll().size());
        ALog.log("DBInstance.doMigration end");
    }

}
