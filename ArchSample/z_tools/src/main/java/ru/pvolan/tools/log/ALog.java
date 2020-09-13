package ru.pvolan.tools.log;


import android.util.Log;

import ru.pvolan.tools.log.console.AConsole;


public class ALog {

    private static AConsole aConsole = new AConsole();



    //***********************************************
    //Publics



    //Basics

    public static void log(String msg){
        logInternal(msg);
    }

    public static void logTagged(String tag, String msg){
        logInternal(tag, msg);
    }


    //Combos

    public static void logFormatTagged(String tag, String format, Object... args){
        String msg = String.format(format, args);
        logTagged(tag, msg);
    }

    public static void logFormat(String format, Object... args){
        logFormatTagged(null, format, args);
    }



    //**************************************
    //Internal



    private static void logInternal(String msg){
        logInternal(null, msg);
    }

    private static void logInternal(String tag, String msg){
        aConsole.consoleLog(tag, msg);
    }

    private static void logInternal(Throwable throwable){
        logInternal(createExcString(throwable));
    }



    //**************************************
    //Tools



    private static String createExcString(Throwable exc)
    {
        return Log.getStackTraceString(exc);
    }
}
