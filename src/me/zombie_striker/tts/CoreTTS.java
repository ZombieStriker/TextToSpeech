/*
 *  Copyright (C) 2016 Zombie_Striker
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */
package me.zombie_striker.tts;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zombie_striker.tts.data.*;
import me.zombie_striker.tts.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreTTS extends JavaPlugin implements Listener {
	public HashMap<StoreableStrings, Word> words = new HashMap<StoreableStrings, Word>();
	public HashMap<Long, PlayObject> map = new HashMap<Long, PlayObject>();
	HashMap<Long, Integer> mapP = new HashMap<Long, Integer>();

	HashMap<String, List<UUID>> bannedG = new HashMap<String, List<UUID>>();

	HashMap<UUID, String> groups = new HashMap<UUID, String>();
	HashMap<UUID, Integer> lType = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> pitch = new HashMap<UUID, Integer>();

	String prefix = ChatColor.RED + "[TTS]" + ChatColor.RESET;

//	public static String RES = "https://dev.bukkit.org/media/files/956/355/TextToSpeech.zip";
	public static String RES = "https://dev.bukkit.org/projects/texttospeach/files/956355/download";

	private long tick = 0;

	private File langFile = null;
	
	private int speed;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);

		// Do it afterwords so words can be overwritten

		try {
			langFile = new File(this.getDataFolder(), "lang.txt");
			LangFile.verify(langFile);

			BufferedReader br = new BufferedReader(new FileReader(langFile));
			for (int i = 0; i < 2000; i++) {
				String word = br.readLine();
				if (word == null)
					break;
				if (word.trim().length() == 0)
					continue;
				if (word.trim().startsWith("+"))
					continue;

				StoreableStrings ss = StoreableStrings.getStoreableString(word
						.split(" : ")[0].trim());

				words.put(ss, new Word(word.split(" : ")[1].trim()));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			initG(p);
		}
		for (String group : getConfig().getStringList("group.list")) {
			List<UUID> pp = new ArrayList<UUID>();
			bannedG.put(group, pp);
			for (String uuid : getConfig().getStringList(
					"group." + group + ".banned")) {
				pp.add(UUID.fromString(uuid));
			}
		}
		TTSCommand ttsc = new TTSCommand(this);
		getCommand("tts").setExecutor(ttsc);
		getCommand("tts").setTabCompleter(ttsc);
		
		if(!getConfig().contains("SpeechDelay")){
			getConfig().set("SpeechDelay", 3);
			saveConfig();
		}
		speed = getConfig().getInt("SpeechDelay");

		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				tick();
				if (!map.containsKey(getTick()))
					return;
				PlayObject po = map.get(getTick());
				if (po == null)
					return;
				Location locationOfVoice = po.locationOfVoice;
					for (UUID uuidS : groups.keySet()) {
						Player p = Bukkit.getPlayer(uuidS);

						if ((po.sound.get(locationOfVoice) == null))
							System.out
									.println(" Owner is null. This should not be happening.");

						if (p != null) {
							Location loc = null;
							if (lType.get(uuidS) == 1) {// local
								loc = locationOfVoice;
								// po.sound.get(owner).chars + "", 1, 0.5f +
								// ((float) po.pitch.get(owner) / 10));
							} else if (lType.get(uuidS) == 2) {// world
								if (locationOfVoice.getWorld().equals(p.getWorld()))
									loc = p.getLocation();
								// PlaySound.playSound(p, p.getLocation(),
								// po.sound.get(owner).chars + "", 1, 0.5f +
								// ((float) po.pitch.get(owner) / 10));
							} else if (lType.get(uuidS) == 3) {// global
								loc = p.getLocation();
								// PlaySound.playSound(p, p.getLocation(),
								// po.sound.get(owner).chars + "", 1, 0.5f +
								// ((float) po.pitch.get(owner) / 10));
							}
							if (loc != null) {
								p.playSound(
										locationOfVoice,
										po.sound.get(locationOfVoice).chars + "",
										3,
										0.5f + ((float) po.pitch.get(locationOfVoice) / 10));

								if (map.containsKey(getTick()+1)) {
									PlayObject po2 = map.get(getTick() + 1);

									if ((po2.sound.get(locationOfVoice) != null)) {
										int sP = po2.sound.get(locationOfVoice).pitch;
										String extSound = "";
										if(sP==0&&po.sound.get(locationOfVoice).pitch==0)extSound="xaa";
										if(sP==1&&po.sound.get(locationOfVoice).pitch==0)extSound="xab";
										if(sP==0&&po.sound.get(locationOfVoice).pitch==1)extSound="xba";
										
										
										p.playSound(locationOfVoice,
												extSound,
												3, 0.5f + ((float) po.pitch
														.get(locationOfVoice) / 10));
									}
								}
							}
					}
				}
				map.remove(getTick());
			}
		}, 0, speed);

		if (!getConfig().contains("auto-update")) {
			getConfig().set("auto-update", true);
			saveConfig();
		}

		new Updater(this, 103001, getConfig().getBoolean("auto-update"));

		// bStats metrics
		Metrics met = new Metrics(this);
		met.addCustomChart(new Metrics.SimplePie("updater-active") {
			@Override
			public String getValue() {
				return String.valueOf(getConfig().getBoolean("auto-update"));
			}
		});

	}

	@Override
	public void onDisable() {
		reloadConfig();
		LangFile.words = null;
		CoreTTS.RES = null;
		for (UUID k : groups.keySet()) {
			int l = lType.get(k);
			String g = groups.get(k);
			getConfig().set("users." + k.toString() + ".g", g);
			getConfig().set("users." + k.toString() + ".l", l);
		}
		for (String group : bannedG.keySet()) {
			List<String> ls = new ArrayList<String>();
			for (UUID uuid : bannedG.get(group)) {
				ls.add(uuid.toString());
			}
			getConfig().set("group." + group + ".banned", ls);
		}
		List<String> groups = new ArrayList<String>(bannedG.keySet());
		getConfig().set("group.list", groups);
		saveConfig();
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		initG(e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				TalkUtil.speakOut("Text To Speech has been activated", e.getPlayer());
			}
		}, 10L);
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getClickedBlock()!=null){
			if(e.getClickedBlock().getType()==Material.WALL_SIGN||e.getClickedBlock().getType()==Material.SIGN_POST){
				Sign sign = (Sign)e.getClickedBlock().getState();
				String text = sign.getLine(0)+" . "+sign.getLine(1)+" . "+sign.getLine(2)+" . "+sign.getLine(3);
				TalkUtil.speakOut(text, e.getPlayer());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		if (groups.get(e.getPlayer().getUniqueId()).equalsIgnoreCase("none"))
			return;

		Pattern noRepeats = Pattern.compile(".*([a-z[A-Z[0-9]]])\\1{5,}.*");
		Matcher match = noRepeats.matcher(e.getMessage());
		boolean repeats = match.find();
		if (repeats) {
			e.getPlayer().sendMessage(
					prefix + "Do not mic spam. Voice over canceled.");
			return;
		}

		String mF = e.getMessage();
		TalkUtil.speakOut(mF, e.getPlayer());
	}

	public void initG(Player p) {
		if (!groups.containsKey(p.getUniqueId().toString())) {
			String group = getConfig().getString(
					"users." + p.getUniqueId().toString() + ".g");
			if (group == null || group.length() == 0) {
				getConfig().set("users." + p.getUniqueId().toString() + ".g",
						"none");
				saveConfig();
				groups.put(p.getUniqueId(), "none");
				group = "none";
			}
			groups.put(p.getUniqueId(), group);
		}
		if (!lType.containsKey(p.getUniqueId().toString())) {
			int l = getConfig().getInt(
					"users." + p.getUniqueId().toString() + ".t");
			if (l == 0 || l == -1) {
				int lt = 2;

				getConfig().set("users." + p.getUniqueId().toString() + ".l",
						lt);
				saveConfig();
				lType.put(p.getUniqueId(), lt);
				l = lt;
			}
			lType.put(p.getUniqueId(), l);
		}
		if (!pitch.containsKey(p.getUniqueId().toString())) {
			int l = getConfig().getInt(
					"users." + p.getUniqueId().toString() + ".p");
			if (l == 0 || l == -1) {
				int pi = 6;

				getConfig().set("users." + p.getUniqueId().toString() + ".p",
						pi);
				saveConfig();
				pitch.put(p.getUniqueId(), pi);
				l = pi;
			}
			pitch.put(p.getUniqueId(), l);
		}
	}

	public void tick() {
		tick++;
	}

	public long getTick() {
		return tick;
	}
}
