package stfu;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Main.MODID)
public class Main {
    // Happy Deprecated Annotation Day !!!
    public static final String MODID = "stfu";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final KeyMapping NARRATOR_KEY = new KeyMapping(
            "key.stfu.narrator_hotkey",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            KeyMapping.Category.register(Identifier.parse("key.categories.misc")));

    public static final KeyMapping TOGGLE_MUSIC_KEY = new KeyMapping(
            "key.stfu.toggle_music",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            KeyMapping.Category.register(Identifier.parse("key.categories.misc")));

    public static final KeyMapping SKIP_MUSIC_KEY = new KeyMapping(
            "key.stfu.skip_music",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            KeyMapping.Category.register(Identifier.parse("key.categories.misc")));

    public static final Minecraft client = Minecraft.getInstance();

    public static boolean musicPaused = false;

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::registerKeys);

        NeoForge.EVENT_BUS.register(this);
    }

    private void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(NARRATOR_KEY);
        event.register(SKIP_MUSIC_KEY);
        event.register(TOGGLE_MUSIC_KEY);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
