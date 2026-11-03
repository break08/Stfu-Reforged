package stfu;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import stfu.config.Config;

@Mod(value = Main.MODID)
public class MainClient {
    public MainClient(IEventBus bus, ModContainer modContainer){
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Config.HANDLER.load();
            modContainer.registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (minecraft, parent) -> Config.HANDLER.generateGui().generateScreen(parent)));
        }
    }
}