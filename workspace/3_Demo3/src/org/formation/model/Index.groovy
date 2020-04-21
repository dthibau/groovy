package org.formation.model

import java.time.LocalDate

class Index {
	LocalDate created,indexed
	String source
	Map map
	
	@Override
	public String toString() {
		return "Index [created=" + created + ", indexed=" + indexed + ", map=" + map + "]";
	}
	
	
}
