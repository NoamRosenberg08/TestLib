package frc.testlib.tests;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        InstantTest t = new InstantTest<>(
                "aaa",
                new Tags("a","b", "c"),
                (a) -> false,
                () -> 1
        );


        InstantTest t1 = new InstantTest<>(
                "bbb",
                new Tags("b", "c"),
                (a) -> false,
                () -> 1
        );

        InstantTest t2 = new InstantTest<>(
                "ccc",
                new Tags("z"),
                (a) -> false,
                () -> 1
        );

        TestsManager.addTests(t,t1,t2);
        TestsManager.setTags(null);

        System.out.println(Arrays.toString(TestsManager.getTestsByTags(new Tags("a")).toArray()));
        System.out.println(Arrays.toString(TestsManager.getTestsByTags(new Tags("b")).toArray()));
        System.out.println(Arrays.toString(TestsManager.getTestsByTags(new Tags("z")).toArray()));

    }
}
