package enumerated;

/**
 * Created by cowerling on 16-2-17.
 */
enum Shrubbery {
    GROUND, CRAWLING, HANGING
}

public class EnumClass {
    public static void main(String[] args) {
        for(Shrubbery shrubbery : Shrubbery.values()) {
            System.out.println(shrubbery + " ordinal: " + shrubbery.ordinal());
            System.out.print(shrubbery.compareTo(Shrubbery.CRAWLING) + " ");
            System.out.print(shrubbery.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(shrubbery == Shrubbery.CRAWLING);
            System.out.println(shrubbery.getDeclaringClass());
            System.out.println(shrubbery.name());
            System.out.println("------------------------------");
        }

        for(String string : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrubbery = Enum.valueOf(Shrubbery.class, string);
            System.out.println(shrubbery);
        }
    }
}
