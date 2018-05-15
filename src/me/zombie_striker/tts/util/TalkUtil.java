package me.zombie_striker.tts.util;

import me.zombie_striker.tts.CoreTTS;
import me.zombie_striker.tts.data.PlayObject;
import me.zombie_striker.tts.data.Sound;
import me.zombie_striker.tts.data.StoreableStrings;
import me.zombie_striker.tts.data.Word;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TalkUtil {
	
	private static CoreTTS core;
	
	static{
		core = (CoreTTS) Bukkit.getPluginManager().getPlugin("TextToSpeech");
	}

	public static void speakOut(String fullText, Player from){
		int pitch = core.pitch.get(from.getUniqueId());
		speakOut(fullText, from.getLocation(), pitch);
	}
	public static void speakOut(String fullText, Location from, int pitch){
		String[] message = ChatColor.stripColor(fullText.toLowerCase()).split(" ");
		long currentTick = core.getTick() + 3;
		for (int i = 0; i < message.length; i++) {
			int pause = 0;
			String m = message[i];
			//int pitch =core.pitch.get(e.getPlayer().getUniqueId());

			if (m.startsWith("<") && m.contains(":")) {
				pitch = Integer.parseInt(m.split(":")[1].replaceAll(">", ""));
				m = m.substring(1);
			}else{
				m = m.replaceAll("9", " 9 ").replaceAll("8", " 8 ")
						.replaceAll("7", " 7 ").replaceAll("6", " 6 ")
						.replaceAll("5", " 5 ").replaceAll("4", " 4 ")
						.replaceAll("3", " 3 ").replaceAll("2", " 2 ")
						.replaceAll("1", " 1 ").replaceAll("0", " 0 ");				
			}

			if (m.contains(",")) {
				m = m.replaceAll(",", "");
				pause += 2;
			}
			if (m.contains(".")) {
				m = m.replaceAll(".", "");
				pause += 3;
			}
			if (m.contains("'"))
				m = m.replaceAll("'", "");

			StoreableStrings ss = StoreableStrings.getStoreableString(m
					.toLowerCase());
			Word w = null;
			if (core.words.containsKey(ss)) {
				w = core.words.get(ss);
			} else {
				w = Word.parseString(ss.s.toLowerCase());
				core.words.put(ss, w);
			}
			for (int l = 0; l < w.ls.size(); l++) {
				PlayObject ls = core.map.get(currentTick);
				if (ls == null)
					ls = new PlayObject();
				ls.sound.put(from, w.ls.get(l));
				ls.pitch.put(from, pitch);
				ls.locationOfVoice=from;
				core.map.put(currentTick, ls);
				currentTick++;
				//Make the I sound longer
				if (w.ls.get(l) == Sound.I)
					currentTick++;
			}
			currentTick += 0 + pause;
		}
	}
}
