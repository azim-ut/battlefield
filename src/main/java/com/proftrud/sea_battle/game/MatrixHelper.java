package com.proftrud.sea_battle.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MatrixHelper {
    public void printToConsole(BattleField field) {
        int[][] matrix = field.getFieldMatrix();
        System.out.println("---------------" + field.getName() + "---------------");
        for(int i = 0; i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                String nm = Math.round(matrix[i][j]) + "";
                if(matrix[i][j]>9){
                    nm = "0";
                }
                if(matrix[i][j]<0){
                    nm = "*";
                }
                System.out.print((matrix[i][j] == 0.0)?" . ":" "+nm+" ");
            }
            System.out.println("");
        }
        System.out.println("------------------------------");
    }

    public List<Integer> matrixToVector(int[][] matrix){
        List<Integer> vector = new ArrayList<>(matrix.length * matrix[0].length);
        int ind = 0;
        for (int[] values : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                vector.add(values[j]);
                ind++;
            }
        }
        return vector;
    }
}
