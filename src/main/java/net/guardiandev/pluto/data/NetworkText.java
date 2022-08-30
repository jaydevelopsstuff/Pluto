package net.guardiandev.pluto.data;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import net.guardiandev.pluto.util.ByteBufUtil;

import java.io.IOException;

// TODO: Non literals
@Data
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

    public static NetworkText deserialize(ByteBuf buf) {
        Mode mode = Mode.fromId(buf.readByte());
        if(mode == null) return null;
        String text = ByteBufUtil.readTString(buf);
        return new NetworkText(text, mode);
    }

    public enum Mode {
        LITERAL((byte)0),
        FORMATTABLE((byte)1),
        LOCALIZATIONKEY((byte)2);

        public final byte ID;

        Mode(byte ID) {
            this.ID = ID;
        }

        public static Mode fromId(int id) {
            for(Mode mode : values()) {
                if(mode.ID == id) return mode;
            }
            return null;
        }
    }
}
