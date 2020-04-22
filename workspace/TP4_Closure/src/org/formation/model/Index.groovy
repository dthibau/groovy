package org.formation.model

class Index implements Persistent {
	Date created,indexed
	def source
	Map map
	
	public Index (def source) {
		this.source = source
		created = new Date()
		map = [:]
		indexed = null
	}
	@Override
	public String toString() {
		return "Index [created=" + created + ", indexed=" + indexed + ",\n map=" + map + "]";
	}
	
	
}

