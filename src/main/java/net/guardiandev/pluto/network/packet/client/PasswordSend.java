package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;

public class PasswordSend implements ClientPacket {
    public String password;

    @Override
    public PacketType getType() {
        return PacketType.PasswordSend;
    }

    @Override
    public void readPacket(ByteBuf buf) {
        ByteBufUtil.readTString(buf);
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public void processPacket(PlayHandler handler) {

    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Login;
    }
}
