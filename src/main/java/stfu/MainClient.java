package stfu;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import stfu.config.Config;

@Mod(value = Main.MODID, dist = Dist.CLIENT)
public class MainClient {
    public MainClient(IEventBus bus, ModContainer modContainer){
        Config.HANDLER.load();
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (mod, parent) -> Config.HANDLER.generateGui().generateScreen(parent));
    }
}