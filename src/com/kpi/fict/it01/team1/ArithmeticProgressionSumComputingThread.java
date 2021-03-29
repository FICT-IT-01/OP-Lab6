package com.kpi.fict.it01.team1;

class ArithmeticProgressionSumComputingThread implements Runnable {
    private long from;
    private long to;
    private Long step;

    private long sum;

    public void setTo(long to) {
        this.to = to;
    }

    public long getSum() {
        return sum;
    }

    public long getTo() {
        return to;
    }

    public ArithmeticProgressionSumComputingThread(long from, long to, long step) {
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {
        for (long i = from; i <= to; i += step) {
            sum += i;
        }
    }
}
