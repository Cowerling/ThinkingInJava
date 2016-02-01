package xml;

import nu.xom.*;
import java.io.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-25.
 */
public class Person {
    private String first, last;

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public Person(Element person) {
        first = person.getFirstChildElement("first").getValue();
        last = person.getFirstChildElement("last").getValue();
    }

    public Element getXML() {
        Element person = new Element("person");
        Element firstName = new Element("first");
        firstName.appendChild(first);
        Element lastName = new Element("last");
        lastName.appendChild(last);
        person.appendChild(firstName);
        person.appendChild(lastName);

        return person;
    }

    public String toString() {
        return first + " " + last;
    }

    public static void format(OutputStream outputStream, Document document) throws Exception {
        Serializer serializer = new Serializer(outputStream, "ISO-8859-1");
        serializer.setIndent(4);
        serializer.setMaxLength(60);
        serializer.write(document);
        serializer.flush();
    }

    public static void main(String[] args) throws Exception {
        List<Person> people = Arrays.asList(new Person("Dr. Bunsen", "Honeydew"), new Person("Gonzo", "The Great"), new Person("Phillp J.", "Fry"));
        System.out.println(people);
        Element root = new Element("people");
        for(Person person : people)
            root.appendChild(person.getXML());
        Document document = new Document(root);
        format(new BufferedOutputStream(new FileOutputStream("people.xml")), document);
    }
}
