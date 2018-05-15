package me.zombie_striker.tts.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import me.zombie_striker.tts.CoreTTS;
import me.zombie_striker.tts.data.Sound;
import me.zombie_striker.tts.data.StoreableStrings;
import me.zombie_striker.tts.data.Word;

public class LangFile {
	public static HashMap<StoreableStrings, Word> words = new HashMap<StoreableStrings, Word>();

	static double currentVersion = 1.2;

	public static void verify(File langFile) {
		try {
			boolean newFile = false;
			if (!langFile.exists()) {
				langFile.createNewFile();
				newFile = true;
			}
			BufferedReader br = new BufferedReader(new FileReader(langFile));
			if (newFile
					|| Double.parseDouble(br.readLine().split(": ")[1].trim()) != currentVersion) {

				for (int i = 0; i < 2000; i++) {
					String word = br.readLine();
					if (word == null)
						break;
					if (word.trim().length() == 0)
						continue;
					if (word.trim().startsWith("+"))
						continue;

					StoreableStrings ss = StoreableStrings
							.getStoreableString(word.split(" : ")[0].trim());

					words.put(ss, new Word(word.split(" : ")[1].trim()));
				}
				addWords();

				BufferedWriter bw = new BufferedWriter(new FileWriter(langFile));
				bw.write("+Version: " + currentVersion);
				bw.newLine();
				bw.write("+ This is a reminder that comments can be made by adding + to the beginning of a line.");
				bw.newLine();
				bw.write("+ ");
				bw.newLine();
				bw.write("+ To add a new word, create a new line starting with the word, in lower case, that you want MC to read out");
				bw.newLine();
				bw.write("+ and add a colon and then the sounds needed to read-out the word.");
				bw.newLine();
				bw.write("+ ");
				bw.newLine();
				bw.write("+ Known sounds : a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,#,~,&,@,!,%,( , )");
				bw.newLine();
				bw.write("+ '#' is 'th', '~' is 'ch', '& is 'E', '@' is A, '!' is 'I' '%' is 'oo' (as in Loot), '(' is 'ow', and ')' is 'oh'  ");
				bw.newLine();
				bw.write("+ Below is an example of how to add a words, and how to use the extra characters");
				bw.newLine();
				for (Entry<StoreableStrings, Word> wo : words.entrySet()) {
					StringBuilder sb = new StringBuilder();
					for (Sound s : wo.getValue().ls) {
						sb.append(s.chars);
					}
					bw.write(wo.getKey().s + " : " + sb.toString());
					bw.newLine();
				}

				bw.flush();
				bw.close();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		words.clear();
	}

	private static void addWords() {

		words.put(StoreableStrings.getStoreableString("1"), new Word("won"));
		words.put(StoreableStrings.getStoreableString("one"), new Word("won"));
		words.put(StoreableStrings.getStoreableString("won"), new Word("won"));

		words.put(StoreableStrings.getStoreableString("2"), new Word("t%"));
		words.put(StoreableStrings.getStoreableString("to"), new Word("t%"));
		words.put(StoreableStrings.getStoreableString("too"), new Word("t%"));
		words.put(StoreableStrings.getStoreableString("two"), new Word("t%"));

		words.put(StoreableStrings.getStoreableString("3"), new Word("#r&"));
		words.put(StoreableStrings.getStoreableString("three"), new Word("#r&"));

		words.put(StoreableStrings.getStoreableString("4"), new Word("f)r"));
		words.put(StoreableStrings.getStoreableString("four"), new Word("f)r"));

		words.put(StoreableStrings.getStoreableString("5"), new Word("f!vv"));
		words.put(StoreableStrings.getStoreableString("five"), new Word("f!vv"));

		words.put(StoreableStrings.getStoreableString("6"), new Word("sixx"));
		words.put(StoreableStrings.getStoreableString("six"), new Word("sixx"));

		words.put(StoreableStrings.getStoreableString("7"), new Word("seven"));
		words.put(StoreableStrings.getStoreableString("seven"), new Word(
				"seven"));

		words.put(StoreableStrings.getStoreableString("8"), new Word("@t"));
		words.put(StoreableStrings.getStoreableString("eight"), new Word("@t"));

		words.put(StoreableStrings.getStoreableString("9"), new Word("n!nne"));
		words.put(StoreableStrings.getStoreableString("nine"),
				new Word("n!nne"));

		words.put(StoreableStrings.getStoreableString("0"), new Word("z&ro"));
		words.put(StoreableStrings.getStoreableString("zero"), new Word("z&ro"));

		words.put(StoreableStrings.getStoreableString("10"), new Word("ten"));
		words.put(StoreableStrings.getStoreableString("ten"), new Word("ten"));

		words.put(StoreableStrings.getStoreableString("a"), new Word("a")); // Changed
																			// from
																			// @
																			// to
																			// a
																			// for
																			// multiple
																			// languages
		words.put(StoreableStrings.getStoreableString("b"), new Word("b&"));
		words.put(StoreableStrings.getStoreableString("c"), new Word("s&"));
		words.put(StoreableStrings.getStoreableString("d"), new Word("d&"));
		words.put(StoreableStrings.getStoreableString("e"), new Word("&"));
		words.put(StoreableStrings.getStoreableString("f"), new Word("ef"));
		words.put(StoreableStrings.getStoreableString("g"), new Word("j&"));
		words.put(StoreableStrings.getStoreableString("h"), new Word("@~"));
		words.put(StoreableStrings.getStoreableString("i"), new Word("!"));
		words.put(StoreableStrings.getStoreableString("j"), new Word("j@"));
		words.put(StoreableStrings.getStoreableString("k"), new Word("c@"));
		words.put(StoreableStrings.getStoreableString("l"), new Word("el"));
		words.put(StoreableStrings.getStoreableString("m"), new Word("em"));
		words.put(StoreableStrings.getStoreableString("n"), new Word("en"));
		words.put(StoreableStrings.getStoreableString("o"), new Word(")"));
		words.put(StoreableStrings.getStoreableString("p"), new Word("p&"));
		words.put(StoreableStrings.getStoreableString("q"), new Word("qy%"));
		words.put(StoreableStrings.getStoreableString("r"), new Word("ar"));
		words.put(StoreableStrings.getStoreableString("s"), new Word("es"));
		words.put(StoreableStrings.getStoreableString("t"), new Word("t&"));
		words.put(StoreableStrings.getStoreableString("u"), new Word("y%"));
		words.put(StoreableStrings.getStoreableString("v"), new Word("v&"));
		words.put(StoreableStrings.getStoreableString("w"), new Word("dubl y%"));
		words.put(StoreableStrings.getStoreableString("x"), new Word("exx"));
		words.put(StoreableStrings.getStoreableString("y"), new Word("w!"));
		words.put(StoreableStrings.getStoreableString("z"), new Word("z&"));

		words.put(StoreableStrings.getStoreableString("future"), new Word(
				"f%~or"));
		words.put(StoreableStrings.getStoreableString("damn"), new Word("dam"));
		words.put(StoreableStrings.getStoreableString("dance"),
				new Word("dans"));
		words.put(StoreableStrings.getStoreableString("cat"), new Word("cat"));
		words.put(StoreableStrings.getStoreableString("nice"), new Word("n!s"));
		words.put(StoreableStrings.getStoreableString("high"), new Word("h!"));
		words.put(StoreableStrings.getStoreableString("sky"), new Word("h!"));
		words.put(StoreableStrings.getStoreableString("skyblock"), new Word(
				"h!"));
		words.put(StoreableStrings.getStoreableString("eye"), new Word("!"));
		words.put(StoreableStrings.getStoreableString("eyes"), new Word("!s"));
		words.put(StoreableStrings.getStoreableString("have"), new Word("hav"));
		words.put(StoreableStrings.getStoreableString("fancy"), new Word(
				"fanc&"));
		words.put(StoreableStrings.getStoreableString("fantasy"), new Word(
				"fantas&"));
		words.put(StoreableStrings.getStoreableString("pineapple"), new Word(
				"p!napl"));
		words.put(StoreableStrings.getStoreableString("because"), new Word(
				"b&cus"));
		words.put(StoreableStrings.getStoreableString("makes"),
				new Word("m@cs"));
		words.put(StoreableStrings.getStoreableString("voice"),
				new Word("v%!s"));
		words.put(StoreableStrings.getStoreableString("thread"), new Word(
				"#red"));
		words.put(StoreableStrings.getStoreableString("easy"), new Word("&s&"));
		words.put(StoreableStrings.getStoreableString("easier"), new Word(
				"&s&r"));
		words.put(StoreableStrings.getStoreableString("easiest"), new Word(
				"&s&est"));
		words.put(StoreableStrings.getStoreableString("beautiful"), new Word(
				"b&yutiful"));
		words.put(StoreableStrings.getStoreableString("beautifull"), new Word(
				"b&yutiful"));
		words.put(StoreableStrings.getStoreableString("secret"), new Word(
				"s&cret"));
		words.put(StoreableStrings.getStoreableString("iron"), new Word("!ron"));
		words.put(StoreableStrings.getStoreableString("gold"), new Word("G)ld"));
		words.put(StoreableStrings.getStoreableString("queue"), new Word("q%"));
		words.put(StoreableStrings.getStoreableString("work"), new Word("wrc"));
		words.put(StoreableStrings.getStoreableString("working"), new Word(
				"wrcing"));
		words.put(StoreableStrings.getStoreableString("robot"), new Word(
				"r)bot"));
		words.put(StoreableStrings.getStoreableString("loot"), new Word("l%t"));
		words.put(StoreableStrings.getStoreableString("pi"), new Word("p!"));
		words.put(StoreableStrings.getStoreableString("pie"), new Word("p!"));
		words.put(StoreableStrings.getStoreableString("pizza"),
				new Word("p&za"));
		words.put(StoreableStrings.getStoreableString("lite"), new Word("l!t"));
		words.put(StoreableStrings.getStoreableString("light"), new Word("l!t"));
		words.put(StoreableStrings.getStoreableString("im"), new Word("!m"));
		words.put(StoreableStrings.getStoreableString("he"), new Word("h&"));
		words.put(StoreableStrings.getStoreableString("hes"), new Word("h&s"));
		words.put(StoreableStrings.getStoreableString("life"), new Word("l!f"));
		words.put(StoreableStrings.getStoreableString("man"), new Word("man"));
		words.put(StoreableStrings.getStoreableString("human"), new Word(
				"h%man"));
		words.put(StoreableStrings.getStoreableString("you"), new Word("y%"));
		words.put(StoreableStrings.getStoreableString("crazy"), new Word(
				"cr@z&"));
		words.put(StoreableStrings.getStoreableString("love"), new Word("luvv"));
		words.put(StoreableStrings.getStoreableString("once"), new Word("wons"));
		words.put(StoreableStrings.getStoreableString("me"), new Word("m&"));
		words.put(StoreableStrings.getStoreableString("now"), new Word("n(w"));
		words.put(StoreableStrings.getStoreableString("know"), new Word("n("));
		words.put(StoreableStrings.getStoreableString("knowledge"), new Word(
				"n(lej"));
		words.put(StoreableStrings.getStoreableString("ledge"), new Word("lej"));
		words.put(StoreableStrings.getStoreableString("edge"), new Word("ej"));
		words.put(StoreableStrings.getStoreableString("baby"), new Word("b@b&"));
		words.put(StoreableStrings.getStoreableString("like"), new Word("l!c"));
		words.put(StoreableStrings.getStoreableString("any"), new Word("a,n,&"));
		words.put(StoreableStrings.getStoreableString("anyway"), new Word(
				"a,n,&,w,a,y"));
		words.put(StoreableStrings.getStoreableString("anyways"), new Word(
				"a,n,&,w,a,y,s"));
		words.put(StoreableStrings.getStoreableString("were"),
				new Word("w,e,r"));
		words.put(StoreableStrings.getStoreableString("die"), new Word("d,!"));
		words.put(StoreableStrings.getStoreableString("friend"), new Word(
				"f,r,e,n,d"));
		words.put(StoreableStrings.getStoreableString("friends"), new Word(
				"f,r,e,n,d,s"));
		words.put(StoreableStrings.getStoreableString("no"), new Word("n,)"));
		words.put(StoreableStrings.getStoreableString("my"), new Word("m,!"));
		words.put(StoreableStrings.getStoreableString("so"), new Word("s,)"));
		words.put(StoreableStrings.getStoreableString("here"),
				new Word("h,&,r"));
		words.put(StoreableStrings.getStoreableString("some"),
				new Word("s,o,m"));
		words.put(StoreableStrings.getStoreableString("together"), new Word(
				"t,%,g,e,#,e,r"));
		words.put(StoreableStrings.getStoreableString("the"), new Word("#,&"));
		words.put(StoreableStrings.getStoreableString("whole"), new Word(
				"h,),l"));
		words.put(StoreableStrings.getStoreableString("we"), new Word("w,&"));
		words.put(StoreableStrings.getStoreableString("somebody"), new Word(
				"s,o,m,b,o,d,&"));
		words.put(StoreableStrings.getStoreableString("heres"), new Word(
				"h,&,r,s"));
		words.put(StoreableStrings.getStoreableString("over"), new Word(
				"%,v,e,r"));
		words.put(StoreableStrings.getStoreableString("what"), new Word(
				"w,a,h,t"));
		words.put(StoreableStrings.getStoreableString("angels"), new Word(
				"a,n,j,e,l,s"));
		words.put(StoreableStrings.getStoreableString("angel"), new Word(
				"a,n,j,e,l"));
		words.put(StoreableStrings.getStoreableString("are"), new Word("a,r"));
		words.put(StoreableStrings.getStoreableString("go"), new Word("g,%"));
		words.put(StoreableStrings.getStoreableString("sandstone"), new Word(
				"s,a,n,d,s,t,),n"));
		words.put(StoreableStrings.getStoreableString("stone"), new Word(
				"s,t,),n"));
		words.put(StoreableStrings.getStoreableString("going"), new Word(
				"g,%,i,n,g"));
		words.put(StoreableStrings.getStoreableString("oh"), new Word(")"));
		words.put(StoreableStrings.getStoreableString("fire"),
				new Word("f,!,r"));
		words.put(StoreableStrings.getStoreableString("do"), new Word("d,%"));
		words.put(StoreableStrings.getStoreableString("doing"), new Word(
				"d,%,i,n,g"));
		words.put(StoreableStrings.getStoreableString("people"), new Word(
				"p,&,p,),l"));
		words.put(StoreableStrings.getStoreableString("take"),
				new Word("t,@,c"));
		words.put(StoreableStrings.getStoreableString("sign"),
				new Word("s,!,n"));
		words.put(StoreableStrings.getStoreableString("town"),
				new Word("t,(,n"));
		words.put(StoreableStrings.getStoreableString("hi"), new Word("h,!"));
		words.put(StoreableStrings.getStoreableString("hia"), new Word("h,!,a"));
		words.put(StoreableStrings.getStoreableString("be"), new Word(
				"b&"));
		words.put(StoreableStrings.getStoreableString("cute"),
				new Word("c,%,t"));
		words.put(StoreableStrings.getStoreableString("maybe"), new Word(
				"m,@,b,&,&"));
		words.put(StoreableStrings.getStoreableString("wood"),
				new Word("w,%,d"));
		words.put(StoreableStrings.getStoreableString("ocean"), new Word(
				"),s,u,n"));
		words.put(StoreableStrings.getStoreableString("would"), new Word(
				"w,%,d"));
		words.put(StoreableStrings.getStoreableString("kind"), new Word(
				"c,!,n,d"));
		words.put(StoreableStrings.getStoreableString("kindly"), new Word(
				"c,!,n,d,l,y"));

		words.put(StoreableStrings.getStoreableString("omg"), new Word(
				"),e,m,j,&"));
		words.put(StoreableStrings.getStoreableString("lol"), new Word(
				"e,l,),e,l"));
		words.put(StoreableStrings.getStoreableString("gtfo"), new Word(
				"j,&,t,&,e,f,)"));
		words.put(StoreableStrings.getStoreableString("wtf"), new Word(
				"w,u,t, ,#,e, ,e,f"));
		words.put(StoreableStrings.getStoreableString("wth"), new Word(
				"w,u,t, ,#,e, ,@,~"));
		words.put(StoreableStrings.getStoreableString("wrench"), new Word(
				"w,r,e,n,~"));
		words.put(StoreableStrings.getStoreableString("makes"), new Word(
				"m,@,c,s"));
		

		words.put(StoreableStrings.getStoreableString("laugh"), new Word(
				"l,a,f"));

		words.put(StoreableStrings.getStoreableString("photo"), new Word(
				"f,),t,)"));
		words.put(StoreableStrings.getStoreableString("photograph"), new Word(
				"f,),t,),g,r,a,f"));

		// Italian
		words.put(StoreableStrings.getStoreableString("ciao"), new Word("sao"));
		words.put(StoreableStrings.getStoreableString("tutti"),
				new Word("t%t&"));
		words.put(StoreableStrings.getStoreableString("mi"), new Word("m&"));
		words.put(StoreableStrings.getStoreableString("chiamo"), new Word(
				"~amo"));
		words.put(StoreableStrings.getStoreableString("mio"), new Word("m&o"));
		words.put(StoreableStrings.getStoreableString("giocatore"), new Word(
				"z%gator@"));
		words.put(StoreableStrings.getStoreableString("moderatore"), new Word(
				"moderator@"));
		words.put(StoreableStrings.getStoreableString("amministratore"),
				new Word("aministrator@"));
		words.put(StoreableStrings.getStoreableString("passeggio"), new Word(
				"passezz)"));
		words.put(StoreableStrings.getStoreableString("esecuzione"), new Word(
				"esec%zune"));
		words.put(StoreableStrings.getStoreableString("collegare"), new Word(
				"c)legare"));
		words.put(StoreableStrings.getStoreableString("pelle"), new Word(
				"pel@"));
		words.put(StoreableStrings.getStoreableString("diamante"), new Word(
				"d&amonte"));
		words.put(StoreableStrings.getStoreableString("oro"), new Word(
				"or)"));
		words.put(StoreableStrings.getStoreableString("ferro"), new Word(
				"fe#ro"));
		words.put(StoreableStrings.getStoreableString("mela"), new Word(
				"mela"));
		words.put(StoreableStrings.getStoreableString("melone"), new Word(
				"mel)ne"));
		words.put(StoreableStrings.getStoreableString("rotaia"), new Word(
				"rotaia"));
		words.put(StoreableStrings.getStoreableString("pietra"), new Word(
				"p&etra"));
		words.put(StoreableStrings.getStoreableString("sporco"), new Word(
				"sporco"));
		words.put(StoreableStrings.getStoreableString("los"), new Word(
				"l)s"));
		words.put(StoreableStrings.getStoreableString("lo"), new Word(
				"l)s"));
		words.put(StoreableStrings.getStoreableString("granito"), new Word(
				"gran&to"));
		words.put(StoreableStrings.getStoreableString("ossidiana"), new Word(
				")sidana"));
		words.put(StoreableStrings.getStoreableString("letto"), new Word(
				"let)"));
		words.put(StoreableStrings.getStoreableString("uomini"), new Word(
				"%amin&"));
		words.put(StoreableStrings.getStoreableString("uomo"), new Word(
				"%om)"));
		words.put(StoreableStrings.getStoreableString("cane"), new Word(
				"cane"));
		words.put(StoreableStrings.getStoreableString("gatto"), new Word(
				"gat)"));
		words.put(StoreableStrings.getStoreableString("lupo"), new Word(
				"l%p)"));
		words.put(StoreableStrings.getStoreableString("calamaro"), new Word(
				"calamar)"));
		words.put(StoreableStrings.getStoreableString("mucca"), new Word(
				"m%ca"));
		words.put(StoreableStrings.getStoreableString("mucche"), new Word(
				"m%ce"));
		words.put(StoreableStrings.getStoreableString("pollo"), new Word(
				"p)lo"));
		words.put(StoreableStrings.getStoreableString("polli"), new Word(
				"poli"));
		words.put(StoreableStrings.getStoreableString("bloccare"), new Word(
				"blocare"));
		words.put(StoreableStrings.getStoreableString("blocchi"), new Word(
				"bloc&"));
		words.put(StoreableStrings.getStoreableString("cielo"), new Word(
				"~elo"));
		words.put(StoreableStrings.getStoreableString("Luna"), new Word(
				"l%na"));
		words.put(StoreableStrings.getStoreableString("saluti"), new Word(
				"sal%t&"));
		words.put(StoreableStrings.getStoreableString("saluto"), new Word(
				"saluto"));
		words.put(StoreableStrings.getStoreableString("bene"), new Word(
				"bene"));
		words.put(StoreableStrings.getStoreableString("cattivo"), new Word(
				"cat&v)"));
		words.put(StoreableStrings.getStoreableString("peggio"), new Word(
				"pedz)"));
	}
}
