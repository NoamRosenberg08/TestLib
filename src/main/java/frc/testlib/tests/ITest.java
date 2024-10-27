package frc.testlib.tests;

import java.util.function.BooleanSupplier;

public interface ITest {

    boolean test(BooleanSupplier interruptedSignalSupplier);
    String getName();

}
