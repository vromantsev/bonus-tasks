package ua.hillel.serialization;

public class Demo {
    public static void main(String[] args) {
        Person person = new Person("Serhii", 25);
        String fileName = "person.txt";
        SerializationUtils.serializeToFile(person, fileName);

        Person deserializedPerson = SerializationUtils.deserializeFromFile(fileName);
        System.out.printf("Deserialized an object %s from file %s%n", deserializedPerson, fileName);
    }
}
