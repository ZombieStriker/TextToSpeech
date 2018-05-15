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

public enum Sound {
	a('a',1), b('b',1), c('c',1), d('d',1), e('e',1), f('f',0), g('g',0), h('h',0), i('i',1), j(
			'j',0), l('l',1), m('m',1), n('n',1), o('o',1), p('p',1), q('q',0), r('r',1), s('s',0), t(
			't',0), u('u',1), v('v',0), w('w',1), x('x',0), y('y',1), z('z',0), th('#',0), ch(
			'~',1), E('&',1), A('@',1), I('!',1), oo('%',0), ow('(',1), oh(')',0), space(' ',-1);
	public char chars;
	public int pitch;

	private Sound(char chars,int pitch) {
		this.chars = chars;
		this.pitch = pitch;
	}

	public static Sound getClosestChar(String chars) {
		if(chars.equals("k"))return c;
		for (Sound s : Sound.values()) {
			if (chars.equals(s.chars + ""))
				return s;
		}
		return space;
	}
}