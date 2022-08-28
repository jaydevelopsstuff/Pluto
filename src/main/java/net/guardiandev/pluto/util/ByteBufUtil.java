package net.guardiandev.pluto.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public abstract class ByteBufUtil {
    /**
     * Reads a string using C#'s BinaryWriter format.
     * @return The resulting string.
     */
    public static String readTString(ByteBuf buf) {
        int length = read7BitEncodedInt(buf);
        return buf.readCharSequence(length, StandardCharsets.UTF_8).toString();
    }

    public static void writeTString(String s, ByteBuf buf) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        write7BitEncodedInt(bytes.length, buf);
        buf.writeCharSequence(s, StandardCharsets.UTF_8);
    }

    public static int read7BitEncodedInt(ByteBuf buf) {
        int num = 0;
        int num2 = 0;
        byte b;
        do {
            if(num2 == 35) throw new IllegalStateException();
            b = buf.readByte();
            num |= (b & 0x7F) << num2;
            num2 += 7;
        } while((b & 0x80) != 0);
        return num;
    }

    public static void write7BitEncodedInt(int i, ByteBuf buf) {
        int num;
        for(num = i; num >= 128; num >>= 7) {
            buf.writeByte((byte)(num | 0x80));
        }
        buf.writeByte((byte)num);
    }
}
