package net.guardiandev.pluto.data;

public enum Items {
;

    public final int id;
    public final String name;
    public final String internalName;
    public final String type;
    public final int rarity;
    public final int blockId;
    public final int blockSubId;

    Items(int id, String name, String internalName) {
        this.id = id;
        this.name = name;
        this.internalName = internalName;
        this.type = null;
        this.rarity = 0;
        this.blockId = -1;
        this.blockSubId = 0;
    }

    Items(int id, String name, String internalName, String type, int rarity, int blockId, int blockSubId) {
        this.id = id;
        this.name = name;
        this.internalName = internalName;
        this.type = type;
        this.rarity = rarity;
        this.blockId = blockId;
        this.blockSubId = blockSubId;
    }

    public static Items fromId(int id) {
        for(Items item : values()) {
            if(item.id == id) return item;
        }
        return null;
    }
}