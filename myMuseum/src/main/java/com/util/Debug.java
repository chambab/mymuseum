package com.util;
import java.io.*;

public class Debug {
    static public String print_log(String path, String msg)
    {
        try {
        FileWriter fw = new FileWriter(path,true);
        PrintWriter pw = new PrintWriter(fw, true);

        pw.println(msg);

        pw.close();
        fw.close();
        return "OK";
        }catch (IOException e) {
            String errmsg = e+" error";
            return errmsg;
        }
    }
}
