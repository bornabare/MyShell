package hr.fer.oop.lab3.topic1;

/**
 * Created by borna on 1/5/15.
 */
public class SimpleHashTableMain {

    public static void main(String[] args) {

        SimpleHashTable<String, Integer> exams = new SimpleHashTable<>();

        exams.put("Ivana", Integer.valueOf(5));
        exams.put("Janko", Integer.valueOf(4));

        for (String name : exams.keys()) {
            System.out.println("Ime = " + name);
        }

        for (Integer grade : exams.values()) {
            System.out.println("Ocjena = " + grade);
        }

        for (SimpleHashTable.TableEntry<String, Integer> pair : exams) {
            System.out.println("(Ime, Ocjena) = (" + pair.getKey() + ", " + pair.getValue() + ")");
        }
    }
}
