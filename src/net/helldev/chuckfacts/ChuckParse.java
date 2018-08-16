package net.helldev.chuckfacts;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class ChuckParse {

	private static Pattern comp = Pattern.compile("<br><blockquote>(.+?)</blockquote>");

	private static HashMap<String, String> edefs = new HashMap<String, String>();

	static {
		edefs.put("&lt;", "<");
		edefs.put("&gt;", ">");
		edefs.put("&quot;", "\"");
		edefs.put("&apos;", "'");
		edefs.put("&amp;", "&");
	}

	public static String unescapeXML(String data) {
		Set<String> es = edefs.keySet();
		for (String edef : es) {
			data = data.replaceAll(edef, edefs.get(edef));
		}
		return data;
	}

	public static String getFact(Context context) {
		String result;
		try {
			URL link = new URL("http://chucknorrisfacts.ru/random");
			URLConnection fp = link.openConnection();
			InputStreamReader reader = new InputStreamReader(fp.getInputStream(), "cp1251");
			StringBuilder page = new StringBuilder();
			int number = 0;
			char[] ls = new char[4096];
			while (number >= 0) {
				number = reader.read(ls, 0, ls.length);
				if (number > 0) {
					page.append(ls, 0, number);
				}
			}
			reader.close();
			Matcher seeker = comp.matcher(page.toString());
			if (seeker.find()) {
				result = unescapeXML(seeker.group(1));
			} else {
				result = context.getString(R.string.fail0);
			}
		} catch (IOException exc) {
			result = context.getString(R.string.fail1);
		} catch (Exception exc) {
			result = exc.toString();
		}
		return result;
	}

}
