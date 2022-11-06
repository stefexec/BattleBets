package me.bananababoo.battlebets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleItemList {

    public void BattleItemList(String arena, int timer,HashMap<String, Object> Coords, List<BattleItemList> battleItems) {
        this.arena = arena;
        this.timer = timer;

        this.battleItems = battleItems;
    }

    class setCoords{
        private int cx;
        private int cy;
        private int cz;

        public void setCx(int cx) {
            this.cx = cx;
        }
        public void setCy(int cy) {
            this.cy = cy;
        }
        public void setCz(int cz) {
            this.cz = cz;
        }
    }

    public BattleItemList() {}

    private String arena;
    int timer;

    private List<BattleItemList> battleItems;
    private HashMap<String, Object> Coords;

}

// TODO: Read/Write to YAML using ObjectMapper