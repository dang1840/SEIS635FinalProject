package com.seis635.group.tictactoe.view;

import com.seis635.group.tictactoe.logic.ComputerAI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContestantTest {


    Contestant contestant;

    @Before
    public void init() {
        contestant = new ComputerAI();
    }
    @Test
    public void setNameTest() {
        contestant.setName("TestName");
        Assert.assertEquals("TestName", contestant.getName());
    }

    @Test
    public void setRoleTest() {
        contestant.setRole('A');
        Assert.assertEquals('A', contestant.getRole());
    }
}
