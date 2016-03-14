package annotations;

import util.OSExecute;

import java.util.*;

/**
 * Created by dell on 2016/3/14.
 */
public class AtUnitExample4 {
    static String theory = "All brontosauruses are thin at one end, much MUCH thicker in the middle, and then thin again at the far end.";
    private String word;
    private Random random = new Random();

    public AtUnitExample4(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String scrambleWord() {
        List<Character> characters = new ArrayList<Character>();
        for(Character character : word.toCharArray())
            characters.add(character);
        Collections.shuffle(characters, random);
        StringBuffer result = new StringBuffer();
        for(Character character : characters)
            result.append(character);
        return result.toString();
    }

    @TestProperty static List<String> input = Arrays.asList(theory.split(" "));
    @TestProperty static Iterator<String> words = input.iterator();

    @TestObjectCreate static AtUnitExample4 create() {
        if(words.hasNext())
            return new AtUnitExample4(words.next());
        else
            return null;
    }

    @Test boolean words() {
        System.out.println("'" + getWord() + "'");
        return getWord().equals("are");
    }

    @Test boolean scramble1() {
        random = new Random(47);
        System.out.println("'" + getWord() + "'");
        String scrambled = scrambleWord();
        System.out.println(scrambled);
        return scrambled.equals("lAl");
    }

    @Test boolean scramble2() {
        random = new Random(74);
        System.out.println("'" + getWord() + "'");
        String scrambled = scrambleWord();
        System.out.println(scrambled);
        return scrambled.equals("tsaeborornussu");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("starting");
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\AtUnitExample4");
    }
}
