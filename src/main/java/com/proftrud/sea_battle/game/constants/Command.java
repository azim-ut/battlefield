package com.proftrud.sea_battle.game.constants;

public enum Command {
    KILL("убил!"),
    HIT("попал!"),
    MISS("мимо!"),
    MAKE_MOVE("ходи!"),
    YOU_WIN("ты победил!"),
    YOU_LOOS("ты проиграл!");

    private final String text;

    Command(String s) {
        this.text = s;
    }

    public static boolean isHitOrKillOrWin(String txt) {
        return txt.equals(KILL.text) || txt.equals(HIT.text) || txt.equals(YOU_WIN.text);
    }

    public static boolean isMissOrLoos(String txt) {
        return txt.equals(MISS.text) || txt.equals(YOU_LOOS.text);
    }

    public static boolean aiMakeMove(String txt) {
        return txt.equals(MISS.text) || txt.equals(YOU_LOOS.text);
    }
}
