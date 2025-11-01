package com.proftrud.sea_battle.game;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@Service
public class BattleFieldBuilder {

    private int height = 10;
    private int width = 10;
    private String name = "player";

    private final List<Integer> ships = Arrays.asList(1, 1, 1, 1, 4, 3, 3, 2, 2, 2);
    private final Random random = new Random();
    private final List<Position> directions = Arrays.asList(Position.HORIZONTAL, Position.VERTICAL);
    private int[][] positionsMatrix;

    public BattleFieldBuilder setWidth(int columns){
        this.height = columns;
        return this;
    }

    public BattleFieldBuilder setHeight(int rows){
        this.width = rows;
        return this;
    }

    public BattleField build(){
        int[][] positionsMatrix;
        int[] initPlaces = new int[this.width * this.height];

        positionsMatrix = vectorToMatrix(initPlaces, this.width, this.height);

        for(int shipIndex = 0; shipIndex < this.ships.size(); shipIndex++){
            boolean placed = false;
            int shipSize = ships.get(shipIndex);
            while(!placed) {
                int matrix_i = random.nextInt(this.width);
                int matrix_j = random.nextInt(this.height);
                if (pointIsEmpty(matrix_i, matrix_j, positionsMatrix)) {
                    placed = this.placeShip((shipIndex + 1), shipSize, matrix_i, matrix_j, positionsMatrix);
                }
            }
        }
        return new BattleField(positionsMatrix).setName(name);
    }

    private static int[][] vectorToMatrix(int[] vector, int columns, int rows){
        int[][] matrix = new int[columns][rows];
        int ind = 0;
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                matrix[i][j] = vector[ind];
                ind++;
            }
        }
        return matrix;
    }


    private boolean placeShip(int shipInd, int size, int i, int j, int[][] matrix){
        boolean placed = false;

        try{
            Collections.shuffle(directions);
            Position nextDirection = directions.get(0);
            if(nextDirection == Position.HORIZONTAL){
                boolean areaOpen = areaIsEmpty(i, j, i, j+size, matrix);
                int toJ = j+size;
                if(areaOpen && matrix[i][toJ - 1] == 0.0){
                    for(int pos_j = j; pos_j<toJ; pos_j++){
                        matrix[i][pos_j] = 1;
                    }
                    matrix[i][j] = 1;
                    placed = true;
                }
            }else{
                int toI = i+size;
                boolean areaOpen = areaIsEmpty(i, j, i+size, j, matrix);
                if(areaOpen && matrix[toI - 1][j] == 0.0){
                    for(int pos_i = i; pos_i<toI; pos_i++){
                        matrix[pos_i][j] = 1;
                    }
                    matrix[i][j] = 1;
                    placed = true;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return placed;
    }

    private boolean pointIsEmpty(int i, int j, int[][] matrix) {
        return areaIsEmpty(i, j, i, j, matrix, true);
    }
    private boolean areaIsEmpty(int i1, int j1, int i2, int j2, int[][] matrix){
        return areaIsEmpty(i1, j1, i2, j2, matrix, false);
    }
    private boolean areaIsEmpty(int i1, int j1, int i2, int j2, int[][] matrix, boolean muted){
        boolean empty = true;
        int sum = 0;
        List<String> list = new ArrayList<>();
        for(int i = i1-1; (i<=(i2+1) && empty); i++){
            if(i<0 || i>= matrix.length){
                continue;
            }

            for(int j = j1-1; (j<=(j2+1) && empty); j++){
                if(j<0 || j>= matrix[0].length){
                    continue;
                }
                if(matrix[i][j] != 0){
                    empty = false;
                }
                list.add(""+i+":"+j+"; ");
            }
        }
        if(empty && !muted){
//            System.out.println(String.join(" ", list));
        }
        return empty;
    }
}
