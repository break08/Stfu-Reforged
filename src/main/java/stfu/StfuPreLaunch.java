package stfu;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import stfu.config.StfuConfig;

// cloth config is always late !!!
public class StfuPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        AutoConfig.register(StfuConfig.class, GsonConfigSerializer::new);
    }
}
