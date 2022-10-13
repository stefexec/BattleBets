package me.bananababoo.battlebets;

import java.util.List;
import java.util.Objects;

public class BattleItemArena {
    private final String name;
    private final List<ItemBase> battleItem;

    public BattleItemArena(String name, List<ItemBase> battleItem) {
        this.name = name;
        this.battleItem =  Objects.requireNonNull(battleItem);
    }

    public String getArena() {
        return name;
    }
    public List<ItemBase> getItems() {
        return battleItem;
    }

}
