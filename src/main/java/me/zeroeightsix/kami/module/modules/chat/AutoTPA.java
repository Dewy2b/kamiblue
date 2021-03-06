package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.network.play.server.SPacketChat;

import java.util.function.Predicate;

/*
 * By Katatje 8 Dec 2019
 */

@Module.Info(name = "AutoTPA", description = "Automatically decline or accept TPA requests", category = Module.Category.CHAT)
public class AutoTPA extends Module {

    private Setting<mode> mod = register(Settings.e("Response", mode.DENY));

    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;

    public AutoTPA() {
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            SPacketChat packet;
            if (event.getPacket() instanceof SPacketChat && (packet = (SPacketChat) event.getPacket()).getChatComponent().getUnformattedText().contains(" has requested to teleport to you.")) {
                switch (mod.getValue()) {
                    case ACCEPT:
                        Wrapper.getPlayer().sendChatMessage("/tpaccept");
                        break;
                    case DENY:
                        Wrapper.getPlayer().sendChatMessage("/tpdeny");
                        break;
                }
            }
        }, (Predicate<PacketEvent.Receive>[]) new Predicate[0]);
    }

    public enum mode {
        ACCEPT, DENY
    }

}
