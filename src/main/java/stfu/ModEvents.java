package stfu;

import com.mojang.blaze3d.audio.Channel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Map;

import static stfu.Main.*;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (client.player == null) return;
        if (SKIP_MUSIC_KEY.consumeClick()) {
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
    }
}
