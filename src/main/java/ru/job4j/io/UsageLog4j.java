package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        int one = 1;
        byte two = 2;
        short three = 3;
        long four = 4L;
        float five = 5F;
        double six = 6D;
        boolean isTrue = true;
        char a = 'a';
        LOG.debug("int:{}, byte:{}, short:{}, long:{}, float:{}, double:{}, boolean:{}, char:{}",
                one, two, three, four, five, six, isTrue, a);
    }
}