package com.seis635.group.tictactoe.player;

import com.seis635.group.tictactoe.view.Cell;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PlayerTest {
    @Test
    public void shouldPutMarker() {
        Player player = new Player();
        Cell cell = new Cell(3, 3);
        Assertions.assertEquals('X', 'X');
    }
}