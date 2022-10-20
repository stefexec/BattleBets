package me.bananababoo.battlebets;

import java.util.List;
import java.util.Objects;

public class BattleItemArena {
    private final String name;
    private final List<BattleItem> battleItems;

    public BattleItemArena(String name, List<BattleItem> battleItem) {
        this.name = name;
        this.battleItems =  Objects.requireNonNull(battleItem);
    }

    public void addBattleItem(BattleItem battleItem) {
        this.battleItems.add(battleItem);
    }

    public void removeBattleItem(String battleItemName) {
        battleItems.forEach(battleItem -> {
            if (battleItem.getItemName().equals(battleItemName)) {
                battleItems.remove(battleItem);
            }
        });
    }
    public String getArena() {
        return name;
    }
    public List<BattleItem> getItems() {
        return battleItems;
    }

}
