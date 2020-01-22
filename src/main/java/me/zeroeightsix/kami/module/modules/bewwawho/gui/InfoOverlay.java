package me.zeroeightsix.kami.module.modules.bewwawho.gui;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.bewwawho.InfoCalculator;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

/**
 * @author S-B99
 * Created by S-B99 on 04/12/19
 * Updated by S-B99 on 22/01/20
 */
@Module.Info(name = "InfoOverlay", category = Module.Category.GUI, description = "Configures game information overlay", showOnArray = Module.ShowOnArray.OFF)
public class InfoOverlay extends Module {

    public Setting<Boolean> version = register(Settings.b("Version", true));
    public Setting<Boolean> username = register(Settings.b("Username", true));
    public Setting<Boolean> tps = register(Settings.b("TPS", true));
    public Setting<Boolean> fps = register(Settings.b("FPS", true));
    public Setting<Boolean> speed = register(Settings.b("Speed", true));
    public Setting<Boolean> ping = register(Settings.b("Ping", false));
    public Setting<Boolean> durability = register(Settings.b("Durability", true));
    public Setting<Boolean> memory = register(Settings.b("Memory", false));
    public Setting<SpeedUnit> speedUnit = register(Settings.e("Speed Unit", SpeedUnit.KmH));

    public enum SpeedUnit {
        MpS, KmH
    }

    public boolean useUnitKmH() {
        return speedUnit.getValue().equals(SpeedUnit.KmH);
    }

    public String unitType(SpeedUnit s) {
        switch (s) {
            case MpS: return "m/s";
            case KmH: return "km/h";
            default: return "Invalid unit type (mps or kmh)";
        }
    }

    public ArrayList<String> infoContents() {
        ArrayList<String> infoContents = new ArrayList<>();
        if (version.getValue()) {
            infoContents.add(KamiMod.colour + "b" + KamiMod.KAMI_KANJI + KamiMod.colour + "3 " + KamiMod.MODVER);
        }
        if (username.getValue()) {
            infoContents.add(KamiMod.colour + "bWelcome" + KamiMod.colour + "3 " + mc.player.getName() + "!");
        }
        if (tps.getValue()) {
            infoContents.add(KamiMod.colour + "b" + InfoCalculator.tps() + KamiMod.colour + "3 tps");
        }
        if (fps.getValue()) {
            infoContents.add(KamiMod.colour + "b" + Minecraft.debugFPS + KamiMod.colour + "3 fps");
        }
        if (speed.getValue()) {
            infoContents.add(KamiMod.colour + "b" + InfoCalculator.speed() + KamiMod.colour + "3 " + unitType(speedUnit.getValue()));
        }
        if (ping.getValue()) {
            infoContents.add(KamiMod.colour + "b" + InfoCalculator.ping() + KamiMod.colour + "3 ms");
        }
        if (durability.getValue()) {
            infoContents.add(KamiMod.colour + "b" + InfoCalculator.dura() + KamiMod.colour + "3 dura");
        }
        if (memory.getValue()) {
            infoContents.add(KamiMod.colour + "b" + InfoCalculator.memory() + KamiMod.colour + "3mB free");
        }
        return infoContents;
    }

    public void onDisable() { this.enable(); }
}