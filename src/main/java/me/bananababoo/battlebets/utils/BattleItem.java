package me.bananababoo.battlebets.utils;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
public class BattleItem {

    String arenaName;
    List<ItemBase> battleItems;

    static class ItemBase {
        String mmName;
        int timer;
        List<XYZ> locations = new ArrayList<>();

        static class XYZ {
            double x;
            double y;
            double z;
        }

    }
}

