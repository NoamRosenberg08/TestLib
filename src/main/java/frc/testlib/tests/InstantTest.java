package frc.testlib.tests;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class InstantTest<Type> implements ITest{

    private final Predicate<Type> outputCheck;
    private final Supplier<Type> outputSupplier;
    private final String name;
    private final Tags tags;

    public InstantTest(String name, Tags tags, Predicate<Type> outputCheck, Supplier<Type> outputSupplier) {
        this.outputCheck = outputCheck;
        this.outputSupplier = outputSupplier;
        this.name = name;
        this.tags = tags;
    }

    public boolean test(Type output){
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
                "name='" + name + '\'' +
                '}';
    }
}
