package net.guardiandev.pluto.network.handler;

import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.entity.Player;
import net.guardiandev.pluto.network.packet.client.ConnectRequest;

@Data
public class LoginHandler {
    private final Player player;
    private int state = 0;

    public void handleConnectRequest(ConnectRequest request) {
        Pluto.logger.info(request.version);
    }
}
