package music_metadata.utils;

import music_metadata.Main;

public class Debug {
    public static void print(Class c, String s) {
        if (Main.DEBUG) {
            System.out.println("[i] " + c.getName() + " >" + "\t" + s);
        }
    }
}
