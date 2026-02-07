package com.example.path;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class PathTraceMod implements ClientModInitializer {
    // Hai biến này là quan trọng nhất để Shader đọc
    public static boolean isGenerating = false;
    public static int frameCount = 0;
    
    private static KeyBinding renderBtn;

    @Override
    public void onInitializeClient() {
        renderBtn = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pathtrace.gen", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Path Tracing"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (renderBtn.wasPressed()) {
                isGenerating = !isGenerating;
                frameCount = 0; // Reset khi bắt đầu
                if (client.player != null) {
                    client.player.sendMessage(Text.of(isGenerating ? "§aBẮT ĐẦU RENDER..." : "§cĐÃ DỪNG."), true);
                }
            }
            if (isGenerating) {
                frameCount++; // Tăng số khung hình để Shader cộng dồn
            }
        });
    }
}