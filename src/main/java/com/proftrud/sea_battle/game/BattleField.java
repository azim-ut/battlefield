package com.proftrud.sea_battle.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class BattleField {
    private String name;
    private int[][] fieldMatrix = new int[][]{};
    public static String[] LETTERS = new String[]{"A", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У"};

    public BattleField(int[][] matrix){
        fieldMatrix = matrix;
    }

    public State getFieldState(){
        Map<String, Integer[]> out = new LinkedHashMap<>();

        for(int j = 0; j< fieldMatrix.length; j++){
            for(int i = 0; i< fieldMatrix.length; i++){
                String key = LETTERS[j];
                if(!out.containsKey(key)){
                    out.put(key, Arrays.stream(fieldMatrix[j])
                            .boxed()
                            .toArray(Integer[]::new)
                    );
                }
            }
        }

        return new State(name, out);
    }

    public record State(String name, Map<String, Integer[]> stateMap){}
}
