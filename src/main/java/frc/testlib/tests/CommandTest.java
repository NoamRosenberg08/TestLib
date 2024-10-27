package frc.testlib.tests;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CommandTest<T> implements ITest {

    private static final double commandEndDelayTimeSeconds = 5 / 1e3;

    private final Command checkCommand;
    private final Command command;
    private BooleanSupplier interruptedSignalSupplier;
    private final double timeoutInSeconds;
    private final String name;
    private boolean hasPassed;
    public CommandTest(String name, Command command, Predicate<T> outputCheck, Supplier<T> outputSupplier, double timeoutInSeconds) {

        this.timeoutInSeconds = timeoutInSeconds - commandEndDelayTimeSeconds;

        this.checkCommand = new InstantCommand(() -> hasPassed = outputCheck.test(outputSupplier.get()));
        this.command = command.withTimeout(this.timeoutInSeconds).andThen(checkCommand);

        this.interruptedSignalSupplier = () -> false;

        this.name = name;
    }

    public void scheduleCommand(){
        command.schedule();
        shouldInterrupt(interruptedSignalSupplier);
    }
    public static double getActiveTime(double startingTimeSeconds){
        return System.currentTimeMillis() / 1e3 - startingTimeSeconds;
    }

    private void waitForCommandToFinish(){

        double startTime = System.currentTimeMillis() / 1e3;

        while (command.isScheduled() || checkCommand.isScheduled()){
            shouldInterrupt(interruptedSignalSupplier);
            if(getActiveTime(startTime) > timeoutInSeconds){
                break;
            }
        }
    }

    private boolean shouldInterrupt(BooleanSupplier interruptedSignalSupplier){
        if (interruptedSignalSupplier.getAsBoolean()){
            command.end(true);
            failTest();
            return true;
        }
        return false;
    }

    private void failTest(){
        hasPassed = false;
    }

    @Override
    public boolean test(BooleanSupplier interruptedSignalSupplier) {
        this.interruptedSignalSupplier = interruptedSignalSupplier;
        scheduleCommand();

        waitForCommandToFinish();

        return hasPassed;
    }

    @Override
    public String getName() {
        return name;
    }
}
