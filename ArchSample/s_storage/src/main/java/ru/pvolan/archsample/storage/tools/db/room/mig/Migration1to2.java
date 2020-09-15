package ru.pvolan.archsample.storage.tools.db.room.mig;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ru.pvolan.tools.log.ALog;

public class Migration1to2 extends Migration {

    public Migration1to2() {
        super(1, 2);
    }

    //Sample
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        ALog.log("Migration1to2 start>>>>>>>>>>>>>>>>>>>>>");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ALog.log("Migration1to2 end<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
