package org.formation.model

import java.time.LocalDate

class PersistentIndex implements Persistent {
	LocalDate created,indexed
	String source
	Map map
	
	@Override
	public String toString() {
		return "Index [created=" + created + ", indexed=" + indexed + ", map=" + map + "]";
	}
	
	
}
