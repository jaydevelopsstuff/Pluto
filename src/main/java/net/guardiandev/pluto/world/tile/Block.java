package net.guardiandev.pluto.world.tile;

import lombok.Getter;
import lombok.Setter;

/**
 * The class for a Terraria "Block", this class is optimized for minimal memory use
 * @see Blocks
 * @author Jay
 */
@Getter
@Setter
public class Block {
    /** The internal ID of this tile */
    private short ID;

    private short frameX;
    private short frameY;

    private byte color;
    private byte shape = (byte)Shape.Solid.ID;

    /**
     * The bit by bit flags for this block, this reduces the memory needed per block
     * <br> <br>
     * First 3 bits are allocated for combinations to check what {@link Shape} this block has
     * <br>
     * The last 5 bits are currently unused
     * <br><br>
     * If this byte is 0 (all bits 0/false) then this block is "plain"
     */
    private byte flags = 0;

    public Block(short ID) {
        this.ID = ID;
    }

    public Block(int ID) {
        this((short)ID);
        if(ID < Short.MIN_VALUE || ID > Short.MAX_VALUE) throw new IllegalArgumentException("ID must be in range of signed short");
    }

    public Block(Blocks block) {
        this(block.ID);
    }

    public Shape getShapeEnum() {
        return Shape.fromID(shape);
    }

    public void setShapeEnum(Shape shape) {
        this.shape = (byte)shape.ID;
    }

    public boolean sameAs(Block block) {
        if(block == null) return false;
        return ID == block.ID && frameX == block.frameX && frameY == block.frameY && block.color == color && block.flags == flags;
    }

    public Blocks getType() {
        return Blocks.fromID(ID);
    }

    public void setType(Blocks block) {
        ID = (short)block.ID;
        frameX = 0;
        frameY = 0;
    }

    public String getName() {
        Blocks tileType = Blocks.fromID(ID);
        if(tileType == null) return null;
        return tileType.name;
    }

    public void setID(short ID) {
        this.ID = ID;
        this.frameX = 0;
        this.frameY = 0;
    }

    public void setID(int ID) {
        if(ID < Short.MIN_VALUE || ID > Short.MAX_VALUE) throw new IllegalArgumentException("ID must be in the range of signed short");
        setID((short)ID);
    }

    public enum Shape {
        Solid(0),
        HalfBrick(1),
        SlopeDownRight(2),
        SlopeDownLeft(3),
        SlopeUpRight(4),
        SlopeUpLeft(5);

        public final int ID;

        Shape(int ID) {
            this.ID = ID;
        }

        public static Shape fromID(int ID) {
            for(Shape modification : values()) {
                if(modification.ID == ID) return modification;
            }
            return null;
        }
    }
}
