package threaded_java;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TimerScheduledMain {
    public static void main(String[] args) {
        Timer timer = new Timer();
               TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Date date = Calendar.getInstance().getTime();
                String format = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                System.out.println("Timer triggered at " + sdf.format(date));
                timer.cancel();
            }
        };

        timer.schedule(timerTask, 2000);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        Timer timerTwo = new Timer();
        TimerTask timerTaskTwo = new TimerTask() {
            @Override
            public void run() {
                if(atomicInteger.get() > 2){
                    timerTwo.cancel();
                    return;
                }
                Date date = Calendar.getInstance().getTime();
                String format = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                System.out.println("Scheduled triggered at " + sdf.format(date));
                System.out.println("Atomic value: " + atomicInteger.get());
                atomicInteger.set(atomicInteger.get() + 1);



            }
        };

        timer.scheduleAtFixedRate(timerTaskTwo, 0,500);

    }
}
