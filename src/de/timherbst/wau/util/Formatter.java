package de.timherbst.wau.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

	private static NumberFormat dnf = NumberFormat.getNumberInstance();
	private static NumberFormat inf = NumberFormat.getNumberInstance();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	static {
		dnf.setMinimumFractionDigits(2);
		dnf.setMaximumFractionDigits(2);
		dnf.setMaximumIntegerDigits(2);
	}

	public static String format(Double d) {
		return dnf.format(d);
	}

	public static String format(Integer i) {
		return inf.format(i);
	}

	public static Number parse(String s) throws ParseException {
		return dnf.parse(s);
	}

	public static String format(Date datum) {
		return sdf.format(datum);
	}

	public static Date parseDate(String s) throws ParseException {
		return sdf.parse(s);
	}

}
