package frc.testlib.tests;

public class Main {
    public static void main(String[] args) {

        Tags t = new Tags("a","b","c");
        Tags t1 = new Tags("a");

        System.out.println(t.containsTags(t1));
        System.out.println(t1.containsTags(t));

    }
}
