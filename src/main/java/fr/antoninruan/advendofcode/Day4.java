package fr.antoninruan.advendofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {

        String[] lines = Util.getInput("day4.txt");

        List<Integer> order = Arrays.stream(lines[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<String> currentBoard = new ArrayList<>();

        List<Board> boards = new ArrayList<>();

        for(String line : Arrays.copyOfRange(lines, 2, lines.length)) {
            if(line.isBlank()) {
                boards.add(new Board(currentBoard));
                currentBoard.clear();
            } else {
                currentBoard.add(line);
            }

        }
        boards.add(new Board(currentBoard));

        System.out.println("First winning score: " + problem1(boards, order));

        System.out.println("Last winning score: " + problem2(boards, order));

    }

    private static int problem1(List<Board> boards, List<Integer> order) {
        int winner = 0;
        int last = 0;

        find_winner: for (int i : order) {
            for (int j = 0; j < boards.size(); j ++) {
                boards.get(j).check(i);
                if (boards.get(j).isWinner()) {
                    winner = j;
                    last = i;
                    break find_winner;
                }
            }
        }
        return calculateScore(boards.get(winner), last);
    }

    private static int problem2(List<Board> boards, List<Integer> order) {
        int last = 0;
        ListIterator<Integer> iterator = order.listIterator();
        while(iterator.hasNext() && boards.size() > 0) {
            last = iterator.next();
            for (Board b : boards) {
                b.check(last);
            }
            if (boards.size() == 1) {
                if (boards.get(0).isWinner())
                    break;
            } else
                boards.removeIf(Board::isWinner);
        }
        return calculateScore(boards.get(0), last);
    }

    private static int calculateScore(Board b, int last) {
        int sum_unchecked = Arrays.stream(b.board).mapToInt(l ->
                Arrays.stream(l).filter(c -> !c.isChecked()).mapToInt(Case::getNumber).sum()).sum();

        return last * sum_unchecked;
    }

    private static class Case {

        private boolean checked;
        private final int number;

        public Case(int number) {
            this.checked = false;
            this.number = number;
        }

        public boolean isChecked() {
            return checked;
        }

        public void check() {
            this.checked = true;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return "Case{" +
                    "checked=" + checked +
                    ", number=" + number +
                    '}';
        }
    }

    private static class Board {

        private final Case[][] board = new Case[5][5];

        public Board(List<String> lines) {
            for (int i = 0; i < lines.size(); i ++) {
                List<Integer> column = Arrays.stream(lines.get(i).split(" ")).filter(o -> ! o.isBlank())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                for(int j = 0; j < column.size(); j ++) {
                    board[i][j] = new Case(column.get(j));
                }
            }
        }

        public void check(int number) {
            for (Case[] line : board) {
                for(Case c : line) {
                    if (c.getNumber() == number)
                        c.check();
                }
            }
        }

        public boolean isWinner() {
            for (int i = 0; i < board.length; i ++) {
                boolean column = board[i][0].isChecked() &&
                        board[i][1].isChecked() &&
                        board[i][2].isChecked() &&
                        board[i][3].isChecked() &&
                        board[i][4].isChecked();
                if (column) return true;

                column = board[0][i].isChecked() &&
                        board[1][i].isChecked() &&
                        board[2][i].isChecked() &&
                        board[3][i].isChecked() &&
                        board[4][i].isChecked();
                if (column) return true;

            }

            return false;

        }

        @Override
        public String toString() {
            return "Board{" +
                    "board=" + Arrays.toString(board) +
                    '}';
        }
    }

}
