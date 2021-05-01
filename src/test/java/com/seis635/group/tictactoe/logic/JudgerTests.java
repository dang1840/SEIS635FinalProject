package com.seis635.group.tictactoe.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JudgerTests {

    Judger judger;

    @Before
    public void init() {
        judger = new Judger();
    }

    @Test
    public void getWinnerTestPass() {
        judger.setWinner('A');
        Assert.assertEquals('A', judger.getWinner());
    }

    @Test
    public void getWinnerTestFail() {
        judger.setWinner('A');
        Assert.assertNotEquals('B', judger.getWinner());
    }
}
