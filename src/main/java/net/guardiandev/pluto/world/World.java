package net.guardiandev.pluto.world;

import lombok.Data;
import net.guardiandev.pluto.world.tile.Tile;

@Data
public class World {
    private WorldData worldData;
    private Tile[][] tiles;

    public World() {}

    public Tile[][] getTiles(int startX, int startY, int width, int height) {
        Tile[][] tempTiles = new Tile[width][height];

        for(int x = startX; x < startX + width; x++)
            System.arraycopy(tiles[x], startY, tempTiles[x - startX], 0, startY + height - startY);

        return tempTiles;
    }
}
