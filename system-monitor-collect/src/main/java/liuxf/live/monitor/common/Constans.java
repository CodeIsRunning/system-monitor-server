package liuxf.live.monitor.common;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 14:04
 */
public class Constans {

    /**
     * 1 win 2 linux
     */
    public static String osType = "";


    static {
         osType = System.getProperties().getProperty("os.name").toUpperCase();

    }

}
