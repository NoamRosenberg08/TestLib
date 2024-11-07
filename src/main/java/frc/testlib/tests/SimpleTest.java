package frc.testlib.tests;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleTest<T> implements ITest{

    private final Predicate<T> outputCheck;
    private final Supplier<T> outputSupplier;
    private final String name;
    private final String[] tags;

    public SimpleTest(String name, Predicate<T> outputCheck, Supplier<T> outputSupplier, String... tags) {
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
    public String[] getTags() {
        return tags;
    }
}
