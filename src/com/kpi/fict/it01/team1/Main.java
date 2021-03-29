package com.kpi.fict.it01.team1;

import java.util.ArrayList;

public class Main {
    private static final long TO = 1_000_000_000L;
    private static final long FROM = 1;
    private static final int PStep = 1;


    public static void main(String[] args) {
        int[] threadsAmount = new int[] { 1, 2, 4, 8, 16, 32 };

        for (var amount : threadsAmount) {
            compute(amount, FROM, TO, PStep);
        }

        computeArithmeticProgressionSumByFormula(FROM, TO);
    }

    private static void compute(int threadsAmount,
                                long from,
                                long to,
                                long pStep) {
        Timer.start();
        var sum = computeArithmeticProgressionSumInMultithreadingMode(threadsAmount, from, to, pStep);
        System.out.println("Arithmetic progression sum for N = " + to + ": " + sum);
        System.out.println(threadsAmount + (threadsAmount != 1 ? " Threads" : " Thread") + ": Elapsed time: " + Timer.stop() / 1_000_000 + " ms");
    }

    private static void computeArithmeticProgressionSumByFormula(long from, long to) {
        Timer.start();
        System.out.println("Arithmetic progression sum for N = " + to + ": " + ((from + to) * to) / 2);
        System.out.println("FORMULA: Elapsed time: " + Timer.stop() / 1000 + " microseconds");
    }

    private static long computeArithmeticProgressionSumInMultithreadingMode(int threadsAmount,
                                                                            long from,
                                                                            long to,
                                                                            long pStep)
    {
        var arithmeticSumComputingThreads =
                generateComputingThreads(threadsAmount, from, to, pStep);
        executeThreads(threadsAmount, arithmeticSumComputingThreads);
        return getProgressionSum(arithmeticSumComputingThreads);
    }

    private static long getProgressionSum(ArrayList<ArithmeticProgressionSumComputingThread> arithmeticSumComputingThreads) {
        long res = 0;
        for (var s : arithmeticSumComputingThreads) {
            res += s.getSum();
        }
        return res;
    }

    private static void executeThreads(int threadsAmount,
                                       ArrayList<ArithmeticProgressionSumComputingThread> arithmeticSumComputingThreads) {
        var threads = new ArrayList<Thread>(threadsAmount);

        for (int i = 0; i < threadsAmount; i++) {
            var thread = new Thread(arithmeticSumComputingThreads.get(i));

            threads.add(thread);
            thread.start();
        }

        while (threads.size() > 0) {
            threads.removeIf(thread -> !thread.isAlive());
        }
    }

    private static ArrayList<ArithmeticProgressionSumComputingThread> generateComputingThreads(int threadsAmount,
                                                                                               long from,
                                                                                               long to,
                                                                                               long pStep) {
        var arithmeticSumComputingThreads
                = new ArrayList<ArithmeticProgressionSumComputingThread>(threadsAmount);

        long step = ((to - from) + 1) / threadsAmount;

        for (int i = 0; i < threadsAmount; i++) {
            arithmeticSumComputingThreads.add(new ArithmeticProgressionSumComputingThread(from, from + step - 1, pStep));

            from = from + step;
        }

        if (arithmeticSumComputingThreads.get(threadsAmount - 1).getTo() < to) {
            arithmeticSumComputingThreads.get(threadsAmount - 1).setTo(to);
        }
        return arithmeticSumComputingThreads;
    }
}
