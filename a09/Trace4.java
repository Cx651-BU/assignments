/**
 * Trace4.java — PA9 provided file: polymorphism and virtual method dispatch.
 * Do NOT modify this file.
 */
public class Trace4 {

    static class Animal {
        String sound() { return "..."; }
        void describe() {
            System.out.println("I am " + getClass().getSimpleName()
                               + " and I say " + sound());
        }
    }

    static class Dog extends Animal {
        @Override String sound() { return "woof"; }
    }

    static class Cat extends Animal {
        @Override String sound() { return "meow"; }
    }

    static class Kitten extends Cat {
        @Override String sound() { return "mew"; }
    }

    public static void main(String[] args) {
        Animal[] zoo = { new Animal(), new Dog(), new Cat(), new Kitten() };
        for (Animal a : zoo) {
            a.describe();
        }
    }
}
