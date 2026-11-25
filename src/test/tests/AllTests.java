package tests;

public class AllTests {
    private static int passed = 0;

    private static void run(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("PASS " + name);
        } catch (Throwable error) {
            System.err.println("FAIL " + name + ": " + error);
            throw error;
        }
    }

    public static void main(String[] args) {
        run("Composite: leaves and groups share Activity interface",
                CompositeTests::testLeavesAndGroupsShareInterface);
        run("Composite: nested category groups calculate total",
                CompositeTests::testNestedGroupsCalculateTotal);
        run("Decorator: stacked services provide price breakdown",
                DecoratorTests::testStackingAndPriceBreakdown);
        run("Decorator: wrapping keeps base price unchanged",
                DecoratorTests::testWrappingDoesNotChangeBase);
        run("Facade: full trip creation integrates patterns",
                FacadeTests::testFullTripCreation);
        run("Facade: destination pricing and no optional services",
                FacadeTests::testDestinationCostAndNoExtras);

        run("Strategy: standard and student prices are correct",
                StrategyTests::testStrategiesProduceExpectedPrices);
        run("Strategy: Facade switches strategies at runtime",
                StrategyTests::testFacadeSwitchesStrategiesAtRuntime);

        run("Observer: subscribed observers receive creation events",
                ObserverTests::testSubscribedObserversReceiveCreation);
        run("Observer: unsubscribed observers receive no further events",
                ObserverTests::testUnsubscribedObserversStopReceivingEvents);

        run("Command: trip creation undo and Observer notification",
                CommandTests::testFacadeCreationCanBeUndoneAndNotifiesObservers);
        run("Command: undo follows reverse execution order",
                CommandTests::testInvokerUndoesCommandsInReverseOrder);

        System.out.println("All " + passed + " tests passed.");
    }
}
