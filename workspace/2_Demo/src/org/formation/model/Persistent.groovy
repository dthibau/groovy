package org.formation.model

trait Persistent {
	
	long id
	
	boolean save() {
		println "Saving ${this.dump()}"
		true
	}
}
