package com.seis635.group.tictactoe.logic;

import com.seis635.group.tictactoe.view.Board;
import com.seis635.group.tictactoe.view.Cell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComputerAITests {

    ComputerAI ai;

    @Before
    public void init() {
        ai = new ComputerAI();
    }

    @Test
    public void putMarkerTestPass() {
        Board board = new Board();
        ai.putMarker(board.getCell(), board.getStatus());
    }






}
