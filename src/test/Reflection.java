package test;

import java.util.Objects;


public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.mashibing.tank.Strategies.FourDirectionFire");

        System.out.println(new Reflection().hashCode());
        System.out.println(pp == pt);
        System.out.println(builder1 == builder2);

    }

    static StringBuilder builder1 = new StringBuilder("oyyq");
    static StringBuilder builder2 = new StringBuilder("oyyq");
    static Person pp = new Person();
    static Person pt= new Person();

    @Override
    public int hashCode() {
        return Objects.hash(builder2, pp);
    }

    static class Person{
        public Person() {}

        public StringBuilder name = new StringBuilder("xxy");
        public int id = 123;
    }
}
