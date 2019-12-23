package TimeControlAndServices;

/**
 *  Класс-реализация методов обращения к виртуальному времени
 */
public class TimeUtil {
    public static long iniTime = System.currentTimeMillis();
    public static long hourDuration = 120_000;

    public static long getCurrentHour(){

        if ((System.currentTimeMillis() - iniTime) / hourDuration == 24){
            iniTime = System.currentTimeMillis();
        }
        return (System.currentTimeMillis() - iniTime) / hourDuration;
    }

    public static long calcMillisTillNextHour(){
        return hourDuration - (System.currentTimeMillis() - iniTime) % hourDuration;
    }
}
