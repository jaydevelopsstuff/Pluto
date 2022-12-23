package net.guardiandev.pluto.data.item;

import lombok.ToString;
import net.guardiandev.pluto.Pluto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@ToString
public class Items {
    private static final HashMap<Integer, Items> idMap = new HashMap<>();
    private static final HashMap<String , Items> internalNameMap = new HashMap<>();

    public static void load() throws URISyntaxException, IOException {
        Items[] items = Pluto.gson.fromJson(Files.readString(Paths.get(Items.class.getClassLoader().getResource("items.json").toURI())), Items[].class);

        for(Items item : items) {
            idMap.put(item.id, item);
            internalNameMap.put(item.internalName, item);
        }
    }

    public static Items get(int id) {
        return idMap.get(id);
    }

    public static Items get(String internalName) {
        return internalNameMap.get(internalName);
    }

    public final int id;
    public final String name;
    public final String internalName;
    public final String type;
    public final int rarity;
    public final int blockId;
    public final int blockSubId;

    private Items(int id, String name, String internalName) {
        this.id = id;
        this.name = name;
        this.internalName = internalName;
        this.type = null;
        this.rarity = 0;
        this.blockId = -1;
        this.blockSubId = 0;
    }

    private Items(int id, String name, String internalName, String type, int rarity, int blockId, int blockSubId) {
        this.id = id;
        this.name = name;
        this.internalName = internalName;
        this.type = type;
        this.rarity = rarity;
        this.blockId = blockId;
        this.blockSubId = blockSubId;
    }
}
