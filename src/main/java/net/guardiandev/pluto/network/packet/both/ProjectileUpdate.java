package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;
import net.guardiandev.pluto.util.BitsByte;

public class ProjectileUpdate implements ClientPacket, ServerPacket {
    public short projectileUid;
    public float positionX;
    public float positionY;
    public float velocityX;
    public float velocityY;
    public byte owner;
    public short projectileId;
    public byte flags1;
    public byte flags2;
    public float ai1;
    public float ai2;
    public short bannerIdToRespondTo;
    public short damage;
    public float knockback;
    public short originalDamage;
    public short projectileUUID;
    public float ai3;

    @Override
    public PacketType getType() {
        return PacketType.ProjectileNew;
    }

    @Override
    public void readPacket(ByteBuf buf) {
        projectileUid = buf.readShortLE();
        positionX = buf.readFloatLE();
        positionY = buf.readFloatLE();
        velocityX = buf.readFloatLE();
        velocityY = buf.readFloatLE();
        owner = buf.readByte();
        projectileId = buf.readShortLE();

        flags1 = buf.readByte();
        BitsByte bb1 = new BitsByte(flags1);

        if(bb1.getBit(2)) {
            flags2 = buf.readByte();
        }
        BitsByte bb2 = new BitsByte(flags2);

        if(bb1.getBit(0)) ai1 = buf.readFloatLE();
        if(bb1.getBit(1)) ai2 = buf.readFloatLE();

        if(bb1.getBit(3)) bannerIdToRespondTo = buf.readShortLE();
        if(bb1.getBit(4)) damage = buf.readShortLE();
        if(bb1.getBit(5)) knockback = buf.readFloatLE();
        if(bb1.getBit(6)) originalDamage = buf.readShortLE();
        if(bb1.getBit(7)) projectileUUID = buf.readShortLE();
        if(bb2.getBit(0)) ai3 = buf.readFloatLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {}

    @Override
    public void processPacket(PlayHandler handler) {
        handler.handleProjectileUpdate(this);
    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Play;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeShortLE(projectileUid);
        buf.writeFloatLE(positionX);
        buf.writeFloatLE(positionY);
        buf.writeFloatLE(velocityX);
        buf.writeFloatLE(velocityY);
        buf.writeByte(owner);
        buf.writeShortLE(projectileId);
        buf.writeByte(flags1);

        BitsByte bb1 = new BitsByte(flags1);

        if(bb1.getBit(2)) {
            buf.writeByte(flags2);
        }
        BitsByte bb2 = new BitsByte(flags2);

        if(bb1.getBit(0)) buf.writeFloatLE(ai1);
        if(bb1.getBit(1)) buf.writeFloatLE(ai2);

        if(bb1.getBit(3)) buf.writeShortLE(bannerIdToRespondTo);
        if(bb1.getBit(4)) buf.writeShortLE(damage);
        if(bb1.getBit(5)) buf.writeFloatLE(knockback);
        if(bb1.getBit(6)) buf.writeShortLE(originalDamage);
        if(bb1.getBit(7)) buf.writeShortLE(projectileUUID);
        if(bb2.getBit(0)) buf.writeFloatLE(ai3);
    }
}
