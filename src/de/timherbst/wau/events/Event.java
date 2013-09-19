package de.timherbst.wau.events;

public class Event {
	private Integer event_id;

	public static final Event WETTKAMPFTAG_CHANGED = new Event(0);

	public static final Event TURNER_CHANGED = new Event(1);

	public static final Event WETTKAMPF_CHANGED = new Event(2);

	public static final Event MANNSCHAFT_CHANGED = new Event(3);

	public static final Event RIEGE_CHANGED = new Event(4);

	public static final Event NEW_WETTKAMPFTAG = new Event(5);

	public static final Event WERTUNG_CHANGED = new Event(6);

	private Event(int event_id) {
		this.event_id = event_id;
	}

	@Override
	public int hashCode() {
		return event_id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Integer && obj != null)
			return ((Integer) obj).equals(event_id);
		return super.equals(obj);
	}
}
