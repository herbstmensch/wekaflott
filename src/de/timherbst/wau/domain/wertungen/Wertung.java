package de.timherbst.wau.domain.wertungen;

import java.io.Serializable;
import java.util.Vector;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Wertung")
public abstract class Wertung {
	public static final Wertungsmodus WERTUNGSMODUS_CdP = new Wertungsmodus("Code de Pointage");
	public static final Wertungsmodus WERTUNGSMODUS_PStufen = new Wertungsmodus("P-Stufen");

	public abstract Double getEndwert();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getEndwert() + "";
	}

	public static class Wertungsmodus implements Serializable {

		private static final long serialVersionUID = 6410989739487689488L;
		private String name;

		private Wertungsmodus(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Wertungsmodus) {
				return ((Wertungsmodus) obj).getName().equals(getName());
			}
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return getName().hashCode();
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return getName();
		}

	}

	public static Vector<Wertungsmodus> getWertungsmodi() {
		Vector<Wertungsmodus> v = new Vector<Wertung.Wertungsmodus>();
		v.add(WERTUNGSMODUS_CdP);
		v.add(WERTUNGSMODUS_PStufen);
		return v;
	}

}
