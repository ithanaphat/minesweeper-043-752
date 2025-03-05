package com.lab;

import java.util.Random;
import java.util.Scanner;
import java.io.InputStream;

public class Minesweeper {
    static char SAFE_CELL = '.';
    static char MINE_CELL = 'X' ;
    static int IS_SAFE = 0;
    static int IS_MINE = 1;
    int fieldX, fieldY;
    int[][] cells;
    String fieldFileName;

     int defuseAttempts = 0;
     int totalMines = 0;
    Random rand = new Random();

    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }

    public Minesweeper(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.cells = new int[fieldX][fieldY];
        for (int i=0; i<fieldX; i++) {
            for (int j=0; j<fieldY; j++) {
                cells[i][j] = IS_SAFE;
            }
        }
    }

    void displayField() {
        // Task 1: Display the mine field to terminal
        for (int i=0; i<fieldX; i++) {
            for (int j=0; j<fieldY; j++) {
                if(cells[i][j] == IS_SAFE){
                    System.out.print(".");
                }else {
                    System.out.print("X");
                }
            }
            System.out.println("");
    }
}


    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
        totalMines++;
    }

    void initFromFile(String mineFieldFile) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mineFieldFile);
        Scanner scan = new Scanner(is);
        fieldX = Integer.parseInt(scan.nextLine());
        fieldY = Integer.parseInt(scan.nextLine());
        cells = new int[fieldX][fieldY];
 
        for(int i = 0 ; i < fieldX;i++){
            String line = scan.nextLine();
            for(int j = 0 ; j < fieldY;j++){
                if(line.charAt(j) == 'X'){
                    cells[i][j] = IS_MINE;
                }else if(line.charAt(j) == '.')
                    cells[i][j] = IS_SAFE;
            }
        }
        scan.close();
        // Task 2: Using `java.util.Scanner` to load mine field from the input stream named, `is`
 
    }

    void defuseMine(int x, int y) {
        if (cells[x][y] == IS_MINE) {
            cells[x][y] = IS_SAFE;
            totalMines--;
            System.out.println("Mine defused successfully!");
            System.out.println("total mine left: "+totalMines);
            defuseAttempts = 0; 
            if (totalMines == 0) {
                System.out.println("Congratulations! You have defused all mines and won the game!");
                System.exit(0);
            }
        }else {
            System.out.println("No mine at this location!");
            defuseAttempts++;
            if (defuseAttempts >= 3) {
                addRandomMine();
                defuseAttempts = 0;
            }
        }
    }

    void addRandomMine() {
        int x, y;
        do {
            x = rand.nextInt(fieldX);
            y = rand.nextInt(fieldY);
        } while (cells[x][y] == IS_MINE);
    
        
        cells[x][y] = IS_MINE;
        totalMines++; // เพิ่มจำนวนระเบิด
        System.out.println("Mine added!!!!!!!!!");
        System.out.println("After adding mine: " + totalMines); // Debug log
    }
}