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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.zombie_striker.tts.util.TalkUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

public class TTSCommand implements CommandExecutor, TabCompleter {

	CoreTTS tts;

	public TTSCommand(CoreTTS tts) {
		this.tts = tts;// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1,
			String arg2, String[] args) {
		List<String> ls = new ArrayList<String>();
		if (args.length == 1) {
			startsWith(ls, "get", args[0]);
			startsWith(ls, "join", args[0]);
			startsWith(ls, "leave", args[0]);
			startsWith(ls, "kick", args[0]);
			startsWith(ls, "ban", args[0]);
			startsWith(ls, "unban", args[0]);
			startsWith(ls, "setpitch", args[0]);
			startsWith(ls, "download", args[0]);
			startsWith(ls, "ban", args[0]);
			startsWith(ls, "readBook", args[0]);
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("join")) {
				startsWith(ls, "public", args[1]);
				for (String groups : tts.groups.values()) {
					if (!ls.contains(groups))
						startsWith(ls, groups, args[1]);
				}
			} else if (args[0].equalsIgnoreCase("kick")
					|| args[0].equalsIgnoreCase("ban")) {
				for (UUID player : tts.groups.keySet()) {
					if (tts.groups.get(player).equalsIgnoreCase(
							tts.groups.get(Bukkit.getPlayer(arg0.getName())
									.getUniqueId())))
						startsWith(ls, tts.groups.get(player), args[1]);
				}
			}
		}
		return ls;
	}

	public void startsWith(List<String> ls, String s, String arg) {
		if (s.startsWith(arg))
			ls.add(s);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You need to be a player in order to issue these commands.");
			return true;
		}
		final Player p = (Player) sender;
		if (args.length == 0) {
			sendHelp(p);
			return true;
		}
		if (args[0].equalsIgnoreCase("join")) {
			if (args.length < 2) {
				sender.sendMessage(tts.prefix
						+ "You need to specify a group to join");
				return true;
			}
			String group = args[1];
			if (tts.bannedG.containsKey(group)
					&& tts.bannedG.get(group).contains(p.getUniqueId())) {
				sender.sendMessage(tts.prefix
						+ " You have been banned from this group. If you feel this is a mistake, please talk to an admin.");
				return true;
			}
			tts.groups.put(p.getUniqueId(), group);
			sender.sendMessage(tts.prefix + " You have been added to group "
					+ group);
			sender.sendMessage(ChatColor.BOLD+tts.prefix+ChatColor.BOLD+" If you do not have the voice resource pack, you can download it here. Current Version 1.3");
			sender.sendMessage(CoreTTS.RES);
			p.sendTitle("Downloading will begin soon", "If the prompt does not display in 5 seconds, use the link in the chat.");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.tts, new Runnable(){public void run(){p.setResourcePack(CoreTTS.RES);}}, 20*5);
		} else if (args[0].equalsIgnoreCase("readbook")){
			if(p.getInventory().getItemInMainHand()!=null&&p.getInventory().getItemInMainHand().getType()==Material.BOOK_AND_QUILL){
				BookMeta bm = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
				StringBuilder fullBook = new StringBuilder();
				for(String page : bm.getPages()){
					fullBook.append(page+" .. ");
				}
				TalkUtil.speakOut(fullBook.toString(), p);
				
				
			}else{
				p.sendMessage("You must have the book in your hand.");
			}
		} else if (args[0].equalsIgnoreCase("get")){
			sender.sendMessage(ChatColor.BOLD+tts.prefix+ChatColor.BOLD+" If you do not have the voice resource pack, you can download it here. Current Version 1.3");
			sender.sendMessage(CoreTTS.RES);
			p.sendTitle("Downloading will begin soon", "If the prompt does not display in 5 seconds, use the link in the chat.");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.tts, new Runnable(){public void run(){p.setResourcePack(CoreTTS.RES);}}, 20*5);
		} else if (args[0].equalsIgnoreCase("leave")) {
			tts.groups.put(p.getUniqueId(), "none");
			sender.sendMessage(tts.prefix
					+ " You have been removed from your groups.");
		} else if (args[0].equalsIgnoreCase("kick")) {
			if (sender.hasPermission("tts.ban") || sender.isOp()) {
				if (args.length < 2) {
					sender.sendMessage(tts.prefix
							+ " You have to specify which player to " + args[0]);
					return true;
				}
				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) {
					sender.sendMessage(tts.prefix + " The player you want to "
							+ args[0] + " must be on the server.");
					return true;
				}
				tts.groups.put(p2.getUniqueId(), "none");
				p2.sendMessage(tts.prefix + " You have been " + args[0]
						+ " from your group.");
				p.sendMessage(tts.prefix + " You have " + args[0] + " "
						+ p2.getName() + " from your group.");
			}
		} else if (args[0].equalsIgnoreCase("ban")
				|| args[0].equalsIgnoreCase("unban")) {
			if (sender.hasPermission("tts.ban") || sender.isOp()) {
				if (args.length < 2) {
					sender.sendMessage(tts.prefix
							+ " You have to specify which player to " + args[0]);
					return true;
				}
				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) {
					sender.sendMessage(tts.prefix + " The player you want to "
							+ args[0] + " must be on the server.");
					return true;
				}
				if (args.length < 3) {
					sender.sendMessage(tts.prefix
							+ " You have to specify which group to unban the player from");
					return true;
				}
				String group = args[2];
				if (args[0].equalsIgnoreCase("ban")) {
					tts.groups.put(p2.getUniqueId(), "none");
					if (!tts.bannedG.containsKey(group))
						tts.bannedG.put(group, new ArrayList<UUID>());
					tts.bannedG.get(group).add(p2.getUniqueId());
				} else {
					if (tts.bannedG.containsKey(group))
						tts.bannedG.get(group).remove(p2.getUniqueId());
				}
				tts.groups.put(p2.getUniqueId(), "none");
				p2.sendMessage(tts.prefix + " You have been " + args[0]
						+ " from the group " + group);
				p.sendMessage(tts.prefix + " You have " + args[0] + " "
						+ p2.getName() + " from the group." + group);
			}
		}else if (args[0].equalsIgnoreCase("setPitch")){
			if (args.length < 2) {
				sender.sendMessage("You need to specify the pitch between 0 and 10. Default is 5");
				return true;
			}
			int pitch=5;			
			try{
				pitch = Integer.parseInt(args[1]);
			}catch(Exception e){
				sender.sendMessage("You need to specify a number");
				return true;
			}
			tts.pitch.put(p.getUniqueId(), pitch);
			p.sendMessage("Your pitch has been set to "+pitch+".");
		} else if (args[0].equalsIgnoreCase("type")) {
			if (args.length < 2) {
				sender.sendMessage("You need to specify the type <local, world, or global>");
				return true;
			}
			String t = args[1];
			if (t.equalsIgnoreCase("local")) {
				tts.lType.put(p.getUniqueId(), 1);
				sender.sendMessage(tts.prefix
						+ " Your chattype has been set to Local.");
			} else if (t.equalsIgnoreCase("world")) {
				tts.lType.put(p.getUniqueId(), 2);
				sender.sendMessage(tts.prefix
						+ " Your chattype has been set to World.");
			} else if (t.equalsIgnoreCase("global")) {
				tts.lType.put(p.getUniqueId(), 3);
				sender.sendMessage(tts.prefix
						+ " Your chattype has been set to Global.");
			} else {
				sender.sendMessage("Choose either 'Local', 'World', or 'Global'.");
			}
		} else if (args[0].equalsIgnoreCase("download")) {
			sender.sendMessage(tts.prefix+" Link to resourcepack. Download it and activate it to hear the voices.");
			sender.sendMessage("https://dev.bukkit.org/bukkit-plugins/texttospeach/files/4-voice-pack-1-1/");
		} else {
			sendHelp(p);
		}
		return true;
	}

	public void sendHelp(Player p) {
		p.sendMessage(ChatColor.GRAY + " /tts type <local,world,global>:"
				+ ChatColor.RESET + " changes the chat type");
		p.sendMessage(ChatColor.GRAY + " /tts join <group>:" + ChatColor.RESET
				+ " Join a selected group");
		p.sendMessage(ChatColor.GRAY + " /tts leave :" + ChatColor.RESET
				+ " Leave the group you are in");
		p.sendMessage(ChatColor.GRAY + " /tts get:"
				+ ChatColor.RESET + " gives the player the link to the resourcepack.");
		p.sendMessage(ChatColor.GRAY + " /tts kick <player> :"
				+ ChatColor.RESET + " Kicks a player from their group.");
		p.sendMessage(ChatColor.GRAY + " /tts ban/unban <player> <group>:" + ChatColor.RESET
				+ "(un)Bans a player from the group.");
		p.sendMessage(ChatColor.GRAY + " /tts setPitch <pitch> :" + ChatColor.RESET
				+ " sets the pitch for your player.");
		p.sendMessage(ChatColor.GRAY + " /tts readbook :" + ChatColor.RESET
				+ " Reads the book in your hand.");
		p.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"If a word is not right, please go here:");
		p.sendMessage("https://dev.bukkit.org/bukkit-plugins/texttospeach/");
	}
}
