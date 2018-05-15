package me.zombie_striker.tts.data;

import java.util.HashMap;

import org.bukkit.Location;

public class PlayObject {
	public HashMap<Location,Sound> sound = new HashMap<>();
	public HashMap<Location,Integer> pitch = new HashMap<>();
	
	public Location locationOfVoice;
}
