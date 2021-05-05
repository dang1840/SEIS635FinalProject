package com.seis635.group.tictactoe.player;

import com.seis635.group.tictactoe.view.Cell;
import com.seis635.group.tictactoe.view.Contestant;
import com.seis635.group.tictactoe.view.Marker;

public class Player2 extends Contestant {
    public void putMarker(Cell cell) {
        if (role == 'x') {
            cell.setIcon(Marker.X);
        }
        if (role == 'o') {
            cell.setIcon(Marker.O);

        }
        cell.setToken(role);
        cell.setEnabled(false);
    }
}
