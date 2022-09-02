package net.guardiandev.pluto.data.item;

import lombok.Data;

@Data
public class Item {
    private int itemId;
    private Prefix prefix;
    private int stack;
}
