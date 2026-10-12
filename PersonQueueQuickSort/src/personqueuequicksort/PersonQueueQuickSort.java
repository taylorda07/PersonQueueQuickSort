package personqueuequicksort;

import java.util.ArrayList;
import java.util.Scanner;

public class PersonQueueQuickSort {

    static class Person {
        String firstName;
        String lastName;
        int age;

        Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + " – Age: " + age;
        }
    }

    static class PersonQueue {
        private final ArrayList<Person> people = new ArrayList<>();

        void enqueue(Person person) {
            people.add(person);
        }

        Person dequeue() {
            if (people.isEmpty()) {
                throw new IllegalStateException("Queue is empty.");
            }
            return people.remove(0);
        }

        boolean isEmpty() {
            return people.isEmpty();
        }

        void display() {
            for (Person person : people) {
                System.out.println(person);
            }
        }

        ArrayList<Person> getPeople() {
            return new ArrayList<>(people);
        }
    }

    static int compare(Person a, Person b, boolean byAge) {
        if (byAge) {
            return Integer.compare(b.age, a.age);
        }
        return b.lastName.compareToIgnoreCase(a.lastName);
    }

    static void quickSort(ArrayList<Person> list,
                          int low, int high, boolean byAge) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, byAge);
            quickSort(list, low, pivotIndex - 1, byAge);
            quickSort(list, pivotIndex + 1, high, byAge);
        }
    }

    static int partition(ArrayList<Person> list,
                         int low, int high, boolean byAge) {
        Person pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(list.get(j), pivot, byAge) <= 0) {
                i++;
                Person temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Person temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    static void displayList(ArrayList<Person> list) {
        for (Person person : list) {
            System.out.println(person);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonQueue queue = new PersonQueue();

        System.out.println("CSC400 Module 8 Portfolio Project");
        System.out.println("Option 1: Person Class");
        System.out.println("--------------------------------");

        for (int i = 1; i <= 5; i++) {
            System.out.println("\nEnter Person " + i + ":");

            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            int age;
            while (true) {
                System.out.print("Age: ");
                try {
                    age = Integer.parseInt(scanner.nextLine().trim());
                    if (age >= 0 && age <= 130) {
                        break;
                    }
                    System.out.println("Enter an age from 0 to 130.");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }

            queue.enqueue(new Person(firstName, lastName, age));
        }

        System.out.println("\nORIGINAL QUEUE:");
        queue.display();

        ArrayList<Person> byLastName = queue.getPeople();
        quickSort(byLastName, 0, byLastName.size() - 1, false);

        System.out.println("\nSORTED BY LAST NAME (DESCENDING):");
        displayList(byLastName);

        ArrayList<Person> byAge = queue.getPeople();
        quickSort(byAge, 0, byAge.size() - 1, true);

        System.out.println("\nSORTED BY AGE (DESCENDING):");
        displayList(byAge);

        scanner.close();
    }
}
