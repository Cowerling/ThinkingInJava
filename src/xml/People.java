package xml;

import nu.xom.*;
import java.util.*;

/**
 * Created by cowerling on 16-2-1.
 */
public class People extends ArrayList<Person> {
    public People(String fileName) throws Exception {
        Document document = new Builder().build(fileName);
        Elements elements = document.getRootElement().getChildElements();
        for(int i = 0; i < elements.size(); i++)
            add(new Person(elements.get(i)));
    }

    public static void main(String[] args) throws Exception {
        People people = new People("/home/cowerling/IdeaProjects/people.xml");
        System.out.println(people);
    }
}
