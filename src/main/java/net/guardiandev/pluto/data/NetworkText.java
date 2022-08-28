package net.guardiandev.pluto.data;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.util.ByteBufUtil;

import java.io.IOException;

public class NetworkText {
    public static final NetworkText Empty = new NetworkText("", Mode.LITERAL);

    private final String text;
    private final Mode mode;

    private NetworkText[] substitutions;

    public NetworkText(String text, Mode mode) {
        this.text = text;
        this.mode = mode;
    }

    public void serialize(ByteBuf buf) {
        buf.writeByte(mode.ID);
        ByteBufUtil.writeTString(text, buf);
    }

    public enum Mode {
        LITERAL((byte)0),
        FORMATTABLE((byte)1),
        LOCALIZATIONKEY((byte)2);

        public final byte ID;

        Mode(byte ID) {
            this.ID = ID;
        }
    }
}
