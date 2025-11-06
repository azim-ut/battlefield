package com.proftrud.sea_battle.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class GameTable {

    private String Id;
    private String name;
    private int fieldSize = 0;
    private List<HistoryRow> history = new ArrayList<>();
    private List<BattleField> players = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    public GameTableDescription getDescription(String fieldName){
        return new GameTableDescription(
                fieldSize,
                Map.of(0, "вода", 1, "корабль", 2, "мимо", 3, "попал"),
                players.stream()
                        .filter(row -> fieldName.equals(row.getName()))
                        .findFirst()
                        .get()
                        .getFieldState()
                        .stateMap()
        );
    }

    public GameTable clearMessages(){
        messages.clear();
        return this;
    }

    public GameTableState getState(){
        Map<String, BattleField> fields = new LinkedHashMap<>();
        players.forEach(row -> fields.put(row.getName(), row));
        return new GameTableState(
                fields,
                history
        );
    }

    public GameTable addHistory(String player, String playerTarget, String target, int result){
        this.history.add(new HistoryRow(this.history.size(), player, playerTarget, target, result));
        return this;
    }

    public void applyHistory(List<HistoryRow> historyRows){
        if(historyRows == null){
            return;
        }
        historyRows.forEach(historyRow -> {
            var out = new AtomicInteger(-100);
            var letter = historyRow.target.substring(0,1);
            int row = Arrays.asList(BattleField.LETTERS).indexOf(letter);
            int column = Integer.parseInt(historyRow.target.substring(1,2));
            this.players
                    .stream()
                    .filter(p -> !p.getName().equals(historyRow.player))
                    .findFirst()
                    .ifPresent(f -> {
                        out.set(f.getFieldMatrix()[row][column]);
                    });
            if(out.get() >= 0){
                var newRow = new HistoryRow(
                        historyRow.turn,
                        historyRow.player,
                        historyRow.playerTarget,
                        historyRow.target,
                        out.get()
                );
                if(history.size() >= historyRow.turn){
                    history.set(historyRow.turn, newRow);
                }else{
                    history.add(historyRow.turn, newRow);
                }
            }
        });
    }

    public record GameTableState(
            Map<String, BattleField> fields,
            List<HistoryRow> history
    ){}

    public record GameTableDescription(
            int fieldSize,
            Map<Integer, String> resultsValues,
            Map<String, Integer[]> aiField
    ){}

    public record FieldState(String name, List<BattleField.State> rows){}

    public record HistoryRow(
            int turn,
            String player,
            String playerTarget,
            String target,
            int result
    ){}
}
