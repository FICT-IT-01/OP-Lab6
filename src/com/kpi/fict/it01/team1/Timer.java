package com.kpi.fict.it01.team1;

class Timer {
    private static long startTimestamp;
    private static boolean isRun;

    public static void start() {
        isRun = true;
        startTimestamp = System.nanoTime();
    }

    public static long stop() {
        if (isRun) {
            isRun = false;
            return System.nanoTime() - startTimestamp;
        }
        return 0;
    }
}
