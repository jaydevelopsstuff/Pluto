package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;
import net.guardiandev.pluto.util.ByteBufUtil;
import net.guardiandev.pluto.util.TColor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

// TODO: Figure this out
@AllArgsConstructor
public class LoadNetModule implements ClientPacket, ServerPacket {
    public NetModulePacket packet = null;

    public LoadNetModule() {}

    @Override
    public void readPacket(ByteBuf buf) {
        NetModule module = NetModule.fromId(buf.readShortLE());
        if(module == null) return;
        packet = module.instantiateClass();
        if(packet == null) return;
        packet.readPacket(buf);
    }

    @Override
    public void processPacket(LoginHandler handler) {}

    @Override
    public void processPacket(PlayHandler handler) {
        if(packet != null) packet.processPacket(handler);
    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Play;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(packet.getModule().id);
        packet.writePacket(buf);
    }

    @Override
    public PacketType getType() {
        return PacketType.LoadNetModule;
    }

    public static class Text implements NetModulePacket {
        // CLIENT STUFF
        public String commandId;
        public String content;

        // SERVER STUFF
        public byte authorIndex;
        public NetworkText networkText;
        public TColor color;

        public Text() {}

        public Text(byte authorIndex, NetworkText networkText, TColor color) {
            this.authorIndex = authorIndex;
            this.networkText = networkText;
            this.color = color;
        }

        @Override
        public void readPacket(ByteBuf buf) {
            commandId = ByteBufUtil.readTString(buf);
            content = ByteBufUtil.readTString(buf);
        }

        @Override
        public void processPacket(PlayHandler handler) {
            handler.handleNetModuleText(this);
        }

        @Override
        public void writePacket(ByteBuf buf) {
            buf.writeByte(authorIndex);
            networkText.serialize(buf);
            color.serialize(buf);
        }

        @Override
        public NetModule getModule() {
            return NetModule.Text;
        }
    }

    public interface NetModulePacket {
        void readPacket(ByteBuf buf);

        void processPacket(PlayHandler handler);

        void writePacket(ByteBuf buf);

        NetModule getModule();
    }

    @RequiredArgsConstructor
    public enum NetModule {
        Liquid(0),
        Text(1, LoadNetModule.Text.class),
        Ping(2),
        Ambience(3),
        Bestiary(4),
        CreativeUnlocks(5),
        CreativePowers(6),
        CreativeUnlocksPlayerReport(7),
        TeleportPylon(8),
        Particles(9),
        CreativePowerPermissions(10);

        public final int id;
        public final Class<? extends NetModulePacket> packetClass;

        NetModule(int id) {
            this.id = id;
            this.packetClass = null;
        }

        public NetModulePacket instantiateClass() {
            if(packetClass == null) return null;
            try {
                return packetClass.getDeclaredConstructor().newInstance();
            } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return null;
            }
        }

        public static NetModule fromId(int id) {
            for(NetModule module : values()) {
                if(module.id == id) return module;
            }
            return null;
        }
    }
}
