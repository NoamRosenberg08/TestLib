package frc.testlib.tests;

import java.util.ArrayList;
import java.util.List;

public class TestsManager {

    private static final List<ITest> registeredTests = new ArrayList<>();
    private static final Thread testThread = new Thread(TestsManager::runTests);
    private static boolean interruptSignal = false;


    public static void addTests(ITest test){
        registeredTests.add(test);
    }

    public static void addTests(ITest... tests){
        for (ITest test : tests){
            addTests(test);
        }
    }

    private static void runTests(){
        interruptSignal = false;
        boolean testResult;
        for (ITest test : registeredTests){
            if(interruptSignal){
                return;
            }
            testResult = test.test(() -> interruptSignal);
            System.out.println(test.getName() + ": " + (testResult ? "\u001B[32m"+ "passed" : "\u001B[31m" +"failed") + "\u001B[0m");;
        }
    }

    public static void runTestsOnThread(){
        System.out.println("starting tests thread...");
        testThread.start();
    }

    public void interrupt(){
        interruptSignal = true;
    }

}
