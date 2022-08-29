package net.guardiandev.pluto.world.tile;

import lombok.Getter;
import lombok.Setter;

public class Tile {
    @Getter
    private Block block;
    @Getter
    private Wall wall;

    @Getter
    @Setter
    private short frameX = -1;

    @Getter
    @Setter
    private short frameY = -1;

    @Getter
    @Setter
    private byte liquidAmount;

    /**
     * The bit by bit flags for this tile, this reduces the memory needed per tile.
     * <br> <br>
     * Bits: 1: Red Wire, 2: Blue Wire, 3: Green Wire, 4: Yellow Wire, 5: Has Actuator, 6: Actuated, 7: Lava, 8: Honey.
     */
    private byte flags = 0b00000000;

    public Tile(Block block) {
        this.block = block;
    }

    public Tile(Wall wall) {
        this.wall = wall;
    }

    public Tile(Block block, Wall wall) {
        this.block = block;
        this.wall = wall;
    }

    public Tile(Block block, int frameX, int frameY) {
        this(block);
        this.frameX = (short)frameX;
        this.frameY = (short)frameY;
    }

    public Tile(Wall wall, int frameX, int frameY) {
        this(wall);
        this.frameX = (short)frameX;
        this.frameY = (short)frameY;
    }

    public Tile(Block block, Wall wall, int frameX, int frameY) {
        this(block, wall);
        this.frameX = (short)frameX;
        this.frameY = (short)frameY;
    }

    public WireType getWire() {
        if((flags & redWireBit) == redWireBit) return WireType.Red;
        else if((flags & blueWireBit) == blueWireBit) return WireType.Blue;
        else if((flags & greenWireBit) == greenWireBit) return WireType.Green;
        else if((flags & yellowWireBit) == yellowWireBit) return WireType.Yellow;
        return null;
    }

    public void setWire(WireType wire) {
        // Reset
        flags &= ~redWireBit;
        flags &= ~blueWireBit;
        flags &= ~greenWireBit;
        flags &= ~yellowWireBit;

        if(wire == null) return;

        switch(wire) {
            case Red -> flags |= redWireBit;
            case Blue -> flags |= blueWireBit;
            case Green -> flags |= greenWireBit;
            case Yellow -> flags |= yellowWireBit;
        }
    }

    public boolean hasActuator() {
        return (flags & hasActuatorBit) == hasActuatorBit;
    }

    public void setHasActuator(boolean hasActuator) {
        if(hasActuator) flags |= hasActuatorBit;
        else flags &= ~hasActuatorBit;
    }

    public void clearLiquid() {
        setLiquid(null);
        liquidAmount = 0;
    }

    public Liquid getLiquid() {
        if(liquidAmount == 0) return null;

        boolean lavaActive = (flags & lavaBit) == lavaBit;
        boolean honeyActive = (flags & honeyBit) == honeyBit;

        if(!lavaActive && !honeyActive) return Liquid.Water;
        else if(lavaActive) return Liquid.Lava;
        return Liquid.Honey;
    }

    public void setLiquid(Liquid liquid) {
        flags &= ~lavaBit;
        flags &= ~honeyBit;
        if(liquid == Liquid.Lava) {
            flags |= lavaBit;
        } else if(liquid == Liquid.Honey) {
            flags |= honeyBit;
        }
    }

    public boolean isActuated() {
        return (flags & actuatedBit) == actuatedBit;
    }

    public void setActuated(boolean actuated) {
        if(actuated) flags |= actuatedBit;
        else flags &= ~actuatedBit;
    }

    public boolean hasBlock() {
        return block != null;
    }

    public boolean hasWall() {
        return wall != null;
    }

    public boolean isEmpty() {
        return !hasBlock() && !hasWall();
    }

    public boolean sameAs(Tile tile) {
        if(isEmpty() && tile.isEmpty() && flags == tile.flags) return true;
        if(block == null) return tile.block == null;
        if(wall == null) return tile.wall == null;
        return block.sameAs(tile.block) && wall.sameAs(tile.wall) && flags == tile.flags;
    }

    public Tile copy() {
        return new Tile(block, wall, frameX, frameY);
    }

    public Tile copy(int customFrameY) {
        return new Tile(block, wall, frameX, customFrameY);
    }

    public static final byte redWireBit = 0b0000_0001;
    public static final byte blueWireBit = 0b0000_0010;
    public static final byte greenWireBit = 0b0000_0100;
    public static final byte yellowWireBit = 0b0000_1000;
    public static final byte hasActuatorBit = 0b0001_0000;
    public static final byte actuatedBit = 0b0010_0000;
    public static final byte lavaBit = 0b0100_0000;
    public static final byte honeyBit = (byte)0b1000_0000;
}
