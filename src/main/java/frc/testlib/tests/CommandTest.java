package frc.testlib.tests;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class CommandTest<T> implements ITest {

    private static final int DEFAULT_OUTPUT_FRAME_SIZE = 5;

    private final Command command;
    private final double timeoutInSeconds;
    private final Predicate<T> outputCheck;
    private final Supplier<T> outputSupplier;
    private final Deque<T> outputFrame;
    private final String name;

    public CommandTest(String name, Command command, Predicate<T> outputCheck, Supplier<T> outputSupplier, int outputFrameSize, double timeoutInSeconds) {

        this.name = name;
        this.command = command.withTimeout(timeoutInSeconds);

        this.outputCheck = outputCheck;
        this.outputSupplier = outputSupplier;

        this.outputFrame = new LinkedList<>();
        fillOutputFrame(outputFrameSize);

        this.timeoutInSeconds = timeoutInSeconds;
    }

    public CommandTest(String name, Command command, Predicate<T> outputCheck, Supplier<T> outputSupplier, double timeoutInSeconds) {
        this(
                name,
                command,
                outputCheck,
                outputSupplier,
                DEFAULT_OUTPUT_FRAME_SIZE,
                timeoutInSeconds
        );
    }

    public static double getActiveTime(double startingTimeSeconds) {
        return System.currentTimeMillis() / 1e3 - startingTimeSeconds;
    }

    private void fillOutputFrame(int outputFrameSize) {
        for (int i = 0; i < outputFrameSize; i++) {
            outputFrame.add(outputSupplier.get());
        }
    }

    public void scheduleCommand() {
        command.schedule();
    }

    private void updateFrame(){
        outputFrame.remove();
        outputFrame.add(outputSupplier.get());
    }

    private void waitForCommandToFinish() {

        double startTime = System.currentTimeMillis() / 1e3;

        while (command.isScheduled()) {

            if (getActiveTime(startTime) > timeoutInSeconds) {
                break;
            }
            updateFrame();
        }
    }

    private boolean hasFramePassedTest(){

        for (T output : outputFrame) {
            if (outputCheck.test(output)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean test() {
        scheduleCommand();

        waitForCommandToFinish();

        return hasFramePassedTest();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CommandTest{" +
                "command=" + command.getName() +
                ", timeoutInSeconds=" + timeoutInSeconds +
                ", name='" + name + '\'' +
                '}';
    }
}
