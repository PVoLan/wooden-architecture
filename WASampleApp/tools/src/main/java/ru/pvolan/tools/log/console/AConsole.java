package ru.pvolan.tools.log.console;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class AConsole {



    public void consoleLog(String tag, String msg){
        if(tag != null) consoleLog(String.format("[%10.10s] %s", tag, msg));
        else consoleLog(msg);
    }


    private void consoleLog(String msg){
        List<String> lines = splitTooLongMessages(msg);
        for (String line : lines) {
            Log.i ("ALog", line);
        }
    }


    private static List<String> splitTooLongMessages(String msg){
        List<String> res = new ArrayList<>();
        int maxLength = 1000;
        if(msg.length() <= maxLength) {
            res.add(msg);
            return res;
        }

        String[] lines = msg.split("\\n");
        for (String line : lines) {
            if(line.length() <= maxLength){
                res.add(line);
            } else {
                int index = 0;
                while (index < line.length()) {
                    res.add(line.substring(index, Math.min(index + maxLength,line.length())));
                    index += maxLength;
                }
            }
        }

        return res;
    }
}
