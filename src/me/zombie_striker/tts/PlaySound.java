package me.zombie_striker.tts;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class PlaySound {

//	private final static Class<?> CRAFTPLAYERCLASS = ReflectionUtil
//			.getCraftbukkitClass("CraftPlayer", "entity");
//	private static final Class<?> PACKET_PLAY_OUT_NAMED_SOUND = /*ReflectionUtil
//			.isVersionHigherThan(1, 7) ?*/ReflectionUtil
//			.getNMSClass("PacketPlayOutNamedSoundEffect")/*: ReflectionUtil
//			.getNMSClass("Packet201PlayerInfo")*/; 
//	private static final Class<?> MINECRAFT_KEY = ReflectionUtil
//			.getNMSClass("MinecraftKey");
//	private static final Class<?> SOUND_EFFECT = ReflectionUtil
//			.getNMSClass("SoundEffect");
//	private static final Class<?> PACKET_CLASS = ReflectionUtil
//			.getNMSClass("Packet");

	public static void playSound(Player p, Location l, String sound, float vol, float pitch){
	//	PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(new SoundEffect(new MinecraftKey(sound)), SoundCategory.VOICE, l.getX(), l.getY(), l.getZ(), vol, pitch);
		/*Object packet = ReflectionUtil
				.instantiate((Constructor<?>) ReflectionUtil
						.getConstructor(PACKET_PLAY_OUT_NAMED_SOUND)
						.get());
		
		try {
			Object mckey = ReflectionUtil.instantiate((Constructor<?>) ReflectionUtil
						.getConstructor(MINECRAFT_KEY)
						.get());
			ReflectionUtil.setInstanceField(mckey, "a", sound);
			
			Object sf = ReflectionUtil.instantiate((Constructor<?>) ReflectionUtil
					.getConstructor(SOUND_EFFECT)
					.get());
		ReflectionUtil.setInstanceField(sf, "a", mckey);*
			
			ReflectionUtil.setInstanceField(packet, "a", sf);
			ReflectionUtil.setInstanceField(packet, "b", 1);
			ReflectionUtil.setInstanceField(packet, "c", l.getBlockX()*32);
			ReflectionUtil.setInstanceField(packet, "d", l.getBlockY()*32);
			ReflectionUtil.setInstanceField(packet, "e", l.getBlockZ()*32);
			ReflectionUtil.setInstanceField(packet, "f", vol);
			ReflectionUtil.setInstanceField(packet, "g", pitch);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	//	Object handle = ReflectionUtil.invokeMethod(
	//			CRAFTPLAYERCLASS.cast(p), "getHandle", null);
	//	Object playerConnection = ReflectionUtil.getInstanceField(handle,
	//			"playerConnection");
	//	ReflectionUtil.invokeMethod(playerConnection, "sendPacket",
	//			new Class[] { PACKET_CLASS }, packet);
		
	}
}
