package stfu;

import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import stfu.config.StfuConfig;

import java.util.Map;

public class Main implements ModInitializer
{
    public static final String MODID = "stfu";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Minecraft client = Minecraft.getInstance();

    public static boolean musicPaused = false;

    public static final KeyMapping NARRATOR_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.stfu.narrator_hotkey",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.misc"));

    public static final KeyMapping TOGGLE_MUSIC_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.stfu.toggle_music",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.misc"));

    public static final KeyMapping SKIP_MUSIC_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.stfu.skip_music",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.misc"));


    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null){
                return;
            }
            if (SKIP_MUSIC_KEY.consumeClick()) {
                //$ overlay_message '"msg.stfu.skip_music"'
                client.player.displayClientMessage(Component.translatable("msg.stfu.skip_music"), true);
                client.getMusicManager().stopPlaying();
                client.getMusicManager().startPlaying(client.getSituationalMusic());
                musicPaused = false;
            }
            if (TOGGLE_MUSIC_KEY.consumeClick()) {
                musicPaused = !musicPaused;
                if (musicPaused) {
                    for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> entry : client.getSoundManager().soundEngine.instanceToChannel.entrySet()) {
                        if (entry.getKey().getSource() == SoundSource.MUSIC) entry.getValue().execute(Channel::pause);
                    }
                    //$ overlay_message '"msg.stfu.pause_music"'
                    client.player.displayClientMessage(Component.translatable("msg.stfu.pause_music"), true);
                } else {
                    for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> entry : client.getSoundManager().soundEngine.instanceToChannel.entrySet()) {
                        if (entry.getKey().getSource() == SoundSource.MUSIC) entry.getValue().execute(Channel::unpause);
                    }
                    //$ overlay_message '"msg.stfu.resume_music"'
                    client.player.displayClientMessage(Component.translatable("msg.stfu.resume_music"), true);
                }
            }
            if (musicPaused) client.getMusicManager().nextSongDelay++;
        });
    }

}