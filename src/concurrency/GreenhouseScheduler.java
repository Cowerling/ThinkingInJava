package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/14.
 */
public class GreenhouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day";

    public synchronized void setThermostat(String value)
    {
        thermostat = value;
    }

    ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);

    public void schedule(Runnable event, long delay) {
        scheduledExecutorService.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        scheduledExecutorService.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    class LightOn implements Runnable {
        public void run() {
            System.out.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        public void run() {
            System.out.println("Turning off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        public void run() {
            System.out.println("Turning greenhouse water on");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        public void run() {
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {
        public void run() {
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        public void run() {
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        public void run() {
            System.out.println("Bing!");
        }
    }

    class Terminate implements Runnable {
        public void run() {
            System.out.println("Terminating");
            scheduledExecutorService.shutdownNow();

            new Thread() {
                public void run() {
                    for(DataPoint dataPoint : data)
                        System.out.println(dataPoint);
                }
            }.start();
        }
    }

    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity;

        public DataPoint(Calendar time, float temperature, float humidity) {
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        public String toString() {
            return time.getTime() + String.format(" temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }

    private Calendar lastTime = Calendar.getInstance();
    {
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 0);
    }
    private float lastTemperature = 65.0f;
    private int temperatureDirection = 1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = 1;
    private Random random = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());

    class CollectData implements Runnable {
        public void run() {
            System.out.println("Collecting data");

            synchronized (GreenhouseScheduler.this) {
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                if(random.nextInt(5) == 4)
                    temperatureDirection *= -1;
                lastTemperature += temperatureDirection * (1.0f + random.nextFloat());
                if(random.nextInt(5) == 4)
                    humidityDirection *= -1;
                lastHumidity += humidityDirection * random.nextFloat();
                data.add(new DataPoint((Calendar)lastTime.clone(), lastTemperature, lastHumidity));
            }
        }
    }

    public static void main(String[] args) {
        GreenhouseScheduler greenhouseScheduler = new GreenhouseScheduler();
        greenhouseScheduler.schedule(greenhouseScheduler.new Terminate(), 5000);
        greenhouseScheduler.repeat(greenhouseScheduler.new Bell(), 0, 1000);
        greenhouseScheduler.repeat(greenhouseScheduler.new ThermostatNight(), 0, 2000);
        greenhouseScheduler.repeat(greenhouseScheduler.new LightOn(), 0, 200);
        greenhouseScheduler.repeat(greenhouseScheduler.new LightOff(), 0, 400);
        greenhouseScheduler.repeat(greenhouseScheduler.new WaterOn(), 0, 600);
        greenhouseScheduler.repeat(greenhouseScheduler.new WaterOff(), 0, 800);
        greenhouseScheduler.repeat(greenhouseScheduler.new ThermostatDay(), 0, 1400);
        greenhouseScheduler.repeat(greenhouseScheduler.new CollectData(), 500, 500);
    }
}
