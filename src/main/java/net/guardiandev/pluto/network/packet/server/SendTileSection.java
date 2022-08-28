package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.world.tile.Block;
import net.guardiandev.pluto.world.tile.Tile;
import net.guardiandev.pluto.world.tile.Wall;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@AllArgsConstructor
public class SendTileSection implements ServerPacket {
    public Channel channel;
    public boolean compressed;
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
        buf.writeBoolean(compressed);
        ByteBuf uncompressed = writeUncompressedData();
        if(compressed) {
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
        } else {
            buf.writeBytes(uncompressed);
        }
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

                Block block = tile.getBlock();
                Wall wall = null;

                boolean additBlockByte = false;
                boolean additWallByte = false;

                if(block != null)
                {
                    header1 |= blockBit;
                    if(block.getID() > 255)
                    {
                        header1 |= additionalBlockByteBit;
                        additBlockByte = true;
                    }
                }

                if (wall != null)
                {
                    header1 |= wallBit;
                    if (wall.getID() > 255)
                    {
                        header3 |= additionalWallByteBit;
                        additWallByte = true;
                    }
                }

                /*if (tile.liquid_amount() != 0)
                    header |= tile.liquid() << m_liquid_shift;

                if (tile.has_red_wire())
                    header2 |= m_red_wire_bit;

                if (tile.has_blue_wire())
                    header2 |= m_blue_wire_bit;

                if (tile.has_green_wire())
                    header2 |= m_green_wire_bit;*/

                if (block != null)
                    header2 |= (block.getShape() << shapeShift) & shapeBits;

                /*if (tile.has_yellow_wire())
                    header3 |= m_yellow_wire_bit;

                if (tile.has_actuator())
                    header3 |= m_actuator_bit;

                if (tile.is_actuated())
                    header3 |= m_actuated_bit;*/

                if (header2 != 0)
                    header1 |= header2Bit;

                if (header3 != 0)
                {
                    header1 |= header2Bit;
                    header2 |= header3Bit;
                }

                buf.writeByte(header1);
                if (header2 != 0)
                    buf.writeByte(header2);
                if (header3 != 0)
                    buf.writeByte(header3);

                if (block != null)
                {
                    if (additBlockByte)
                        buf.writeShortLE(block.getID());
                    else
                        buf.writeByte((byte)block.getID());

                    if(Pluto.getWorld().getWorldData().importantTiles[block.getID()]) {
                        buf.writeShortLE(block.getFrameX());
                        buf.writeShortLE(block.getFrameY());
                    }
                }

                if (wall != null)
                    buf.writeByte((byte)wall.getID());

                /*if (tile.liquid_amount() != 0)
                    stream_deflated << tile.liquid_amount();*/

                if (additWallByte)
                    buf.writeByte((byte)(wall.getID() >>> 8));
            }
        }
    }

    @Override
    public PacketType getType() {
        return PacketType.SendTileSection;
    }

    // Header 1
    private static final byte header2Bit = 0b00000001;
    private static final byte blockBit = 0b00000010;
    private static final byte wallBit = 0b00000100;
    private static final byte additionalBlockByteBit = 0b00100000;
    private static final byte liquidBits = 0b00011000;

    // Header 2
    private static final byte header3Bit = 0b00000001;
    private static final byte redWireBit = 0b00000010;
    private static final byte blueWireBit = 0b00000100;
    private static final byte greenWireBit = 0b00001000;
    private static final byte shapeBits = 0b01110000;

    // Header 3
    private static final byte actuatorBit = 0b00000010;
    private static final byte actuatedBit = 0b00000100;
    private static final byte yellowWireBit = 0b00100000;
    private static final byte additionalWallByteBit = 0b01000000;

    // Shifts
    private static final byte liquidShift = 3;
    private static  final byte shapeShift = 4;
}
