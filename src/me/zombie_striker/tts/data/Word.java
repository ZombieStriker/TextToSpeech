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
package me.zombie_striker.tts.data;

import java.util.ArrayList;
import java.util.List;

public class Word {

	public List<Sound> ls = new ArrayList<Sound>();

	public Word(String s) {
		String[] g = s.replace(",", "").split("");
		for (int i = 0; i < g.length; i++) {
			ls.add(Sound.getClosestChar(g[i].toLowerCase()));
		}
	}

	public Word(Object[] g) {
		for (int i = 0; i < g.length; i++) {
			ls.add((Sound) (g[i]));
		}
	}

	public static Word parseString(String s1) {
		List<Sound> so = new ArrayList<Sound>();
		String s = s1
				// four letters
				.replaceAll("able", "@bl")
				.replaceAll("que", "qu")
				// Three letters
				.replaceAll("awe", "aw")
				.replaceFirst("age", "@j")
				.replaceAll("ele", "el&")
				.replaceAll("ght", "t")
				// two letters
				.replaceAll("kn", "n").replaceAll("ph", "f").replaceAll("en", "ehn")
				.replaceAll("gh", "g").replaceAll("ch", "~")
				.replaceAll("ee", "&").replaceAll("eo", "&")
				.replaceAll("ea", "&").replaceAll("oo", "%")
				.replaceAll("ou", "%").replaceAll("oi", "%!")
				.replaceAll("ia", "!a").replaceAll("ou", ")")
				.replaceAll("ow", "(").replaceAll("qu", "q%")
				.replaceAll("oa", "%").replaceAll("th", "#")
				.replaceAll("ai", "@").replaceAll("iece", "&s")
				.replaceAll("ie", "&").replaceAll("ce", "s")
				.replaceAll("gg", "g").replaceAll("nn", "n")
				.replaceAll("wh", "w").replaceAll("tt", "t")
				.replaceAll("le", "l").replaceAll("ll", "l")
				.replaceAll("ow", "(").replaceAll("dd", "d")
				.replaceAll("pp", "p").replaceAll("rr", "r")
				.replaceAll("ey", "&").replaceAll("ay", "@")
				.replaceAll("ck", "c")
				// One letter.
				.replaceAll("k", "c")

				// For small sounds
				// .replaceAll("x", "xx").replaceAll("v","vv")

				// Make sure no existing, common letters are read
				.replaceAll("!", "");

		if (s.endsWith("o")) {
			s = s.substring(0, s.length() - 1) + ")";
		}

		if ((s.length() - 3 >= 0 && (s.charAt(s.length() - 1) == 'e') && !(s
				.equals("the")))
				|| (s.length() - 4 >= 0 && (s.charAt(s.length() - 2) == 'e' && s
						.charAt(s.length() - 1) == 'r'))) {

			int er = 0;
			if ((s.length() - 4 >= 0 && (s.charAt(s.length() - 2) == 'e' && s
					.charAt(s.length() - 1) == 'r')))
				er = 1;

			s = s.substring(0, s.length() - 1-er);
			if (s.charAt(s.length() - 2 ) == 'a')
				s = s.substring(0, s.length() - 2 ) + "@"
						+ s.substring(s.length() - 1);
			else if (s.charAt(s.length() - 2) == 'e')
				s = s.substring(0, s.length() - 2) + "&"
						+ s.substring(s.length() - 1);
			else if (s.charAt(s.length() - 2 ) == 'i')
				s = s.substring(0, s.length() - 2 ) + "!"
						+ s.substring(s.length() - 1 );
			else if (s.charAt(s.length() - 2) == 'o')
				s = s.substring(0, s.length() - 2 ) + ")"
						+ s.substring(s.length() - 1 );
			else if (s.charAt(s.length() - 2 ) == 'u')
				s = s.substring(0, s.length() - 2 ) + "%"
						+ s.substring(s.length() - 1 );
			
			if(er==1)s=s+"er";
		}
		//Secondary stuff
		s=s.replaceAll("er", "r");
		
		
		for (int chars = 0; chars < s.length(); chars++) {
			for (Sound s2 : Sound.values()) {
				if (s.charAt(chars) == (s2.chars)) {
					so.add(s2);
					break;
				}
			}
		}
		return new Word(so.toArray());
	}
}
