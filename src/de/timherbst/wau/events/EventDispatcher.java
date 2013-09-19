package de.timherbst.wau.events;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class EventDispatcher {

	private static HashMap<Event, List<EventListener>> listeners = new HashMap<Event, List<EventListener>>();

	public static void addListener(Event e, EventListener listener) {
		if (listeners.containsKey(e)) {
			List<EventListener> l = listeners.get(e);
			l.add(listener);
		} else {
			List<EventListener> l = new Vector<EventListener>();
			l.add(listener);
			listeners.put(e, l);
		}
	}

	public static void dispatchEvent(Event e) {
		if (listeners.containsKey(e)) {
			List<EventListener> l = listeners.get(e);
			for (EventListener el : l)
				el.inform(e);
		}
	}

	public static void removeListener(Event e, EventListener listener) {
		if (listeners.containsKey(e)) {
			List<EventListener> l = listeners.get(e);
			l.remove(listener);
		}
	}

}
