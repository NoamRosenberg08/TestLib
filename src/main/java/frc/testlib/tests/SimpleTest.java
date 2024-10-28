package frc.testlib.tests;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleTest<T> implements ITest{

    private final Predicate<T> outputCheck;
    private final Supplier<T> outputSupplier;
    private final String name;
    private final String path;

    public SimpleTest(String name, String path, Predicate<T> outputCheck, Supplier<T> outputSupplier) {
        this.name = name;
        this.path = path;

        this.outputCheck = outputCheck;
        this.outputSupplier = outputSupplier;
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
    public String getPath() {
        return path;
    }
}
