package enumerated;

/**
 * Created by cowerling on 16-2-17.
 */
enum Signal {
    GREEN, YELLOW, RED
}

public class TrafficLight {
    Signal color = Signal.RED;

    public void change() {
        switch(color) {
            case RED:
                color = Signal.GREEN;
                break;
            case GREEN:
                color = Signal.YELLOW;
                break;
            case YELLOW:
                color = Signal.RED;
                break;
        }
    }

    public String toString() {
        return "The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        for(int i = 0; i < 7; i++) {
            System.out.println(trafficLight);
            trafficLight.change();
        }
    }
}
