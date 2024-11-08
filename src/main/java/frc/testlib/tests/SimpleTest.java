package frc.testlib.tests;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleTest<T> implements ITest{

    private final Predicate<T> outputCheck;
    private final Supplier<T> outputSupplier;
    private final String name;
    private final Tags tags;

    public SimpleTest(String name, Tags tags, Predicate<T> outputCheck, Supplier<T> outputSupplier) {
        this.outputCheck = outputCheck;
        this.outputSupplier = outputSupplier;
        this.name = name;
        this.tags = tags;
    }

    public boolean test(T output){
        return outputCheck.test(output);
    }

    public boolean test(){
        return test(outputSupplier.get());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Tags getTags() {
        return tags;
    }
    public String toString() {
        return "SimpleTest{" +
                "outputCheck=" + outputCheck.toString() +
                ", outputSupplier=" + outputSupplier.toString() +
                ", name='" + name + '\'' +
                '}';
    }
}
