package net.guardiandev.pluto.world;

import lombok.Data;
import net.guardiandev.pluto.world.tile.Tile;

@Data
public class World {
    private WorldData worldData;
    private Tile[][] tiles;

    public World() {
    }

    public Tile getTile(int x, int y) {
        checkInTilesBounds(x, y);

        return tiles[x][y];
    }

    public Tile[][] getTiles(int startX, int startY, int width, int height) {
        Tile[][] tempTiles = new Tile[width][height];

        for(int x = startX; x < startX + width; x++)
            System.arraycopy(tiles[x], startY, tempTiles[x - startX], 0, startY + height - startY);

        return tempTiles;
    }

    public void removeBlock(int tileX, int tileY) {
        checkInTilesBounds(tileX, tileY);

        tiles[tileX][tileY].removeBlock();
    }

    public void removeWall(int tileX, int tileY) {
        checkInTilesBounds(tileX, tileY);

        tiles[tileX][tileY].removeWall();
    }

    public void checkInTilesBounds(int tileX, int tileY) {
        if(!(tileX > 0 && tileX <= worldData.maxTilesX) && (tileY >= 0 && tileY < worldData.maxTilesY))
            throw new IndexOutOfBoundsException();
    }
}
