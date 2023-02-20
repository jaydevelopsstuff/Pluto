package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.world.tile.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@AllArgsConstructor
public class SendTileSection implements ServerPacket {
    public Channel channel;
    public int startX;
    public int startY;
    public short width;
    public short height;
    public Tile[][] tiles;
    //public Chest[] chests;
    //public Sign[] signs;
    //public TileEntity[] tileEntities;

    @Override
    public void writePacket(ByteBuf buf) {
        //buf.writeBoolean(compressed);
        ByteBuf uncompressed = writeUncompressedData();

        byte[] uncompressedBytes = new byte[uncompressed.readableBytes()];
        uncompressed.readBytes(uncompressedBytes);
        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(compressedStream, new Deflater(6, true));
        try {
            dos.write(uncompressedBytes);
            dos.flush();
            dos.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        buf.writeBytes(compressedStream.toByteArray());

        uncompressed.release();
    }

    public ByteBuf writeUncompressedData() {
        ByteBuf buf = channel.alloc().buffer(262144);
        buf.writeIntLE(startX);
        buf.writeIntLE(startY);
        buf.writeShortLE(width);
        buf.writeShortLE(height);
        writeTileData(buf);
        // Chest
        buf.writeShortLE(0);
        // Sign
        buf.writeShortLE(0);
        // Tile Entity
        buf.writeShortLE(0);
        return buf;
    }

    public void writeTileData(ByteBuf buf) {
        for(short y = 0; y < height; y++) {
            for(short x = 0; x < width; x++) {
                Tile tile = tiles[x][y];

                byte header1 = 0;
                byte header2 = 0;
                byte header3 = 0;
                byte header4 = 0;

                Block block = tile.getBlock();
                Wall wall = tile.getWall();

                boolean additBlockByte = false;
                boolean additWallByte = false;

                if(block != null) {
                    header1 |= blockBit;
                    if(block.getID() > 255) {
                        header1 |= additionalBlockByteBit;
                        additBlockByte = true;
                    }
                }

                if(wall != null) {
                    header1 |= wallBit;
                    if(wall.getID() > 255) {
                        header3 |= additionalWallByteBit;
                        additWallByte = true;
                    }
                }

                /*Liquid tileLiquid = tile.getLiquid();
                if(tileLiquid == Liquid.Shimmer) {
                    header3 |= 0b1000_0000;
                    header1 |= 0b0000_1000;
                } else if(tileLiquid == Liquid.Water) header1 |= waterBit;
                else if(tileLiquid == Liquid.Lava) header1 |= lavaBit;
                else if(tileLiquid == Liquid.Honey) header1 |= honeyBit;*/

                /*if(tile.getWire() == WireType.Red) header2 |= redWireBit;

                if(tile.getWire() == WireType.Blue) header2 |= blueWireBit;

                if(tile.getWire() == WireType.Green) header2 |= greenWireBit;

                if(block != null) header2 |= (block.getShape() << shapeShift) & shapeBits;

                if(tile.getWire() == WireType.Yellow) header3 |= yellowWireBit;

                if(tile.hasActuator()) header3 |= actuatorBit;

                if(tile.isActuated()) header3 |= actuatedBit;*/

                if(header4 != 0) {
                    header3 |= header4Bit;
                }

                if(header3 != 0) {
                    header2 |= header3Bit;
                }

                if(header2 != 0) header1 |= header2Bit;

                buf.writeByte(header1);
                if(header2 != 0) buf.writeByte(header2);
                if(header3 != 0) buf.writeByte(header3);
                if(header4 != 0) buf.writeByte(header4);

                if(block != null) {
                    if(additBlockByte) buf.writeShortLE(block.getID());
                    else buf.writeByte((byte)block.getID());

                    if(Pluto.world.getWorldData().importantTiles[block.getID()]) {
                        buf.writeShortLE(block.getFrameX());
                        buf.writeShortLE(block.getFrameY());
                    }
                }

                if(wall != null) buf.writeByte((byte)wall.getID());

                //if(tileLiquid != null) buf.writeByte(tile.getLiquidAmount());

                if(additWallByte) buf.writeByte((byte)(wall.getID() >>> 8));
            }
        }
    }

    @Override
    public PacketType getType() {
        return PacketType.SendTileSection;
    }

    // Header 1
    private static final byte header2Bit = 0b0000_0001;
    private static final byte blockBit = 0b0000_0010;
    private static final byte wallBit = 0b0000_0100;
    private static final byte waterBit = 0b0000_1000;
    private static final byte lavaBit = 0b0001_0000;
    private static final byte honeyBit = 0b0001_1000;
    private static final byte additionalBlockByteBit = 0b0010_0000;

    // Header 2
    private static final byte header3Bit = 0b0000_0001;
    private static final byte redWireBit = 0b0000_0010;
    private static final byte blueWireBit = 0b0000_0100;
    private static final byte greenWireBit = 0b0000_1000;
    private static final byte shapeBits = 0b0111_0000;

    // Header 3
    private static final byte header4Bit = 0b0000_0001;
    private static final byte actuatorBit = 0b0000_0010;
    private static final byte actuatedBit = 0b0000_0100;
    private static final byte colorBit = 0b0000_1000;
    private static final byte yellowWireBit = 0b0010_0000;
    private static final byte additionalWallByteBit = 0b0100_0000;

    // Shifts
    private static final byte liquidShift = 3;
    private static  final byte shapeShift = 4;
}
