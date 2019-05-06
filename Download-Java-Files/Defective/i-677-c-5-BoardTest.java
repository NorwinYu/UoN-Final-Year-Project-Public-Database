package cs361.battleships.models;

import org.junit.Test;

import javax.swing.plaf.basic.BasicOptionPaneUI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));

        Board board2 = new Board();
        board2.placeShip(new Ship("MINESWEEPER"), 0, 'D', true);
        assertFalse(board2.placeShip(new Ship("DESTROYER"), 1, 'B', false));

        Board board3 = new Board();
        board3.placeShip(new Ship("BATTLESHIP"), 0, 'A', true);
        assertFalse(board3.placeShip(new Ship("BATTLESHIP"), 0, 'B', true));

        Board board4 = new Board();
        assertTrue(board4.placeShip(new Ship("MINESWEEPER"), 9, 'A', false));

        Board board5 = new Board();
        assertFalse(board5.placeShip(new Ship("asdsdfasdf"), 9, 'B', false));
    }

    @Test
    public void testInvalidAttack(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', true);

        Result r = board.attack(11, 'C');   //INVALID

        assertEquals(r.getResult(), AtackStatus.INVALID);

        Result samePlace1 = board.attack(4, 'D');
        Result samePlace2 = board.attack(4,'D');
        assertEquals(samePlace2.getResult(), AtackStatus.INVALID);

    }

    @Test
    public void testHitAttack(){
        Board board = new Board();
        board.placeShip(new Ship ("BATTLESHIP"), 4, 'D', true);

        Result r = board.attack(4, 'D'); //HIT
        assertEquals(r.getResult(), AtackStatus.HIT);
    }


    @Test
    public void testSunkAttack(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 4, 'A', false);
        board.placeShip(new Ship("DESTROYER"), 5, 'A', false);
        Result r = board.attack(4, 'A');
        assertEquals(r.getResult(), AtackStatus.HIT);
        Result r1 = board.attack(4, 'B');
        assertEquals(r1.getResult(), AtackStatus.SUNK);

    }

    @Test
    public void testSurrenderAttack(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 4, 'A', false);
        board.placeShip(new Ship("DESTROYER"), 5, 'A', false);

        board.attack(4, 'A');
        board.attack(4, 'B');
        board.attack(5, 'A');
        board.attack(5, 'B');
        Result r = board.attack(5, 'C');

        assertEquals(r.getResult(), AtackStatus.SURRENDER);
    }

    @Test
    public void testMissAttack(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 4, 'A', false);

        Result r = board.attack(1, 'A');

        assertEquals(r.getResult(), AtackStatus.MISS);
    }
}
