package frc.testlib.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TestsManager {

    private static final List<ITest> registeredTests = new ArrayList<>();
    private static Supplier<Tags> tagsSupplier = () -> null;
    private static final Thread testThread = new Thread(TestsManager::runTests);


    public static void addTests(ITest test){
        registeredTests.add(test);
    }

    public static void addTests(ITest... tests){
        for (ITest test : tests){
            addTests(test);
        }
    }

    public static List<ITest> getTestsByTags(Tags wantedTags){

        List<ITest> taggedTests = new ArrayList<>();
        String[] tags;

        for (ITest test : registeredTests){
            if(test.getTags().containsTags(wantedTags)){
                taggedTests.add(test);
            }
        }

        return taggedTests;
    }

    private static void runTests(){

        List<ITest> tests = getTests(tagsSupplier.get());

        for (ITest test : tests){
            if(test instanceof CommandTest<?>){
                System.out.println(
                        test.getName() + ": " + (test.test() ? "\u001B[32m"+ "passed" : "\u001B[31m" +"failed") + "\u001B[0m"
                        + " expected time: " + ((CommandTest<?>) test).getExpectedTime() + " actual end time: " + ((CommandTest<?>) test).getEndTime()
                );
            }else{
                System.out.println(test.getName() + ": " + (test.test() ? "\u001B[32m"+ "passed" : "\u001B[31m" +"failed") + "\u001B[0m");
            }

        }
    }

    private static List<ITest> getTests(Tags tags){
        if(tags == null){
            return registeredTests;
        }
        return getTestsByTags(tagsSupplier.get());
    }

    public static void runTestsOnThread(){
        System.out.println("starting tests thread...");
        testThread.start();
    }

    public static void setTags(Tags tags){
        tagsSupplier = () -> tags;
    }

}
