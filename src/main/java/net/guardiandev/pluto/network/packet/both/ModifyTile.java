package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class ModifyTile implements ClientPacket, ServerPacket {
    public byte action;
    public short tileX;
    public short tileY;
    public short data;
    public byte extraData;

    public ModifyTile() {
    }

    @Override
    public void readPacket(ByteBuf buf) {
        action = buf.readByte();
        tileX = buf.readShortLE();
        tileY = buf.readShortLE();
        data = buf.readShortLE();
        extraData = buf.readByte();
    }

    @Override
    public void processPacket(LoginHandler handler) {
    }

    @Override
    public void processPacket(PlayHandler handler) {
        handler.handleModifyTile(this);
    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Play;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(action);
        buf.writeShortLE(tileX);
        buf.writeShortLE(tileY);
        buf.writeShortLE(data);
        buf.writeByte(extraData);
    }

    @Override
    public PacketType getType() {
        return PacketType.ModifyTile;
    }

    @AllArgsConstructor
    public enum Action {
        RemoveBlock(0),
        PlaceBlock(1),
        RemoveWall(2),
        PlaceWall(3),
        RemoveTileNoItem(4),
        PlaceRedWire(5),
        RemoveRedWire(6),
        PoundBlock(7),
        PlaceActuator(8),
        RemoveActuator(9),
        PlaceBlueWire(10),
        RemoveBlueWire(11),
        PlaceGreenWire(12),
        RemoveGreenWire(13),
        SlopeBlock(14),
        FrameTrack(15),
        PlaceYellowWire(16),
        RemoveYellowWire(17),
        PokeLogicGate(18),
        Actuate(19),
        RemoveBlock2(20),
        ReplaceBlock(21),
        ReplaceWall(22),
        SlopePoundBlock(23);

        public final int id;

        public static Action fromId(int id) {
            for(Action action : values()) {
                if(action.id == id) return action;
            }
            return null;
        }
    }
}
