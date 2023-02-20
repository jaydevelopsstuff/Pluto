package net.guardiandev.pluto.data.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item {
    private int itemId;
    private Prefix prefix;
    private int stack;
}
