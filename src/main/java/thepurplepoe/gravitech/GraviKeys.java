/*
 * Decompiled with CFR 0_124.
 * 
 * Could not load the following classes:
 *  ic2.core.IC2
 *  ic2.core.Platform
 *  ic2.core.util.Keyboard
 *  ic2.core.util.Keyboard$IKeyWatcher
 *  ic2.core.util.Keyboard$Key
 *  ic2.core.util.ReflectionUtil
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.common.util.EnumHelper
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.apache.commons.lang3.ArrayUtils
 */
package thepurplepoe.gravitech;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import ic2.core.IC2;
import ic2.core.util.Keyboard;
import ic2.core.util.ReflectionUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class GraviKeys
extends Keyboard {
    private static final Keyboard.IKeyWatcher FLY_KEY = new KeyWatcher(GraviKey.fly);

    private GraviKeys() {
    }

    static void addFlyKey() {
        IC2.keyboard.addKeyWatcher(FLY_KEY);
    }

    public static boolean isFlyKeyDown(EntityPlayer player) {
        return IC2.keyboard.isKeyDown(player, FLY_KEY);
    }

    private static class KeyWatcher
    implements Keyboard.IKeyWatcher {
        private final GraviKey key;

        public KeyWatcher(GraviKey key) {
            this.key = key;
        }

        public Keyboard.Key getRepresentation() {
            return this.key.key;
        }

        @SideOnly(value=Side.CLIENT)
        public void checkForKey(Set<Keyboard.Key> pressedKeys) {
            if (GameSettings.isKeyDown((KeyBinding)this.key.binding)) {
                pressedKeys.add(this.getRepresentation());
            }
        }
    }

    private static enum GraviKey {
        fly(33, "Gravi Fly Key");
        
        private final Keyboard.Key key;
        @SideOnly(value=Side.CLIENT)
        private KeyBinding binding;

        private static Field getKeysField() {
            try {
                Field field = ReflectionUtil.getField(Keyboard.Key.class, (String[])new String[]{"keys"});
                ReflectionUtil.getField(Field.class, (String[])new String[]{"modifiers"}).setInt(field, field.getModifiers() & -17);
                return field;
            }
            catch (Exception e) {
                throw new RuntimeException("Error reflecting keys field!", e);
            }
        }

        private GraviKey(int keyID, String description) {
            this.key = this.addKey(this.name());
            if (IC2.platform.isRendering()) {
                this.binding = new KeyBinding(description, keyID, "gravisuite".substring(0, 1).toUpperCase(Locale.ENGLISH) + "gravisuite".substring(1));
                ClientRegistry.registerKeyBinding((KeyBinding)this.binding);
            }
        }

        private Keyboard.Key addKey(String name) {
            Keyboard.Key key = (Keyboard.Key)EnumHelper.addEnum(Keyboard.Key.class, (String)name, (Class[])new Class[0], (Object[])new Object[0]);
            ReflectionUtil.setValue((Object)null, (Field)GraviKey.getKeysField(), (Object)ArrayUtils.add((Object[])Keyboard.Key.keys, (Object)key));
            return key;
        }
    }

}

