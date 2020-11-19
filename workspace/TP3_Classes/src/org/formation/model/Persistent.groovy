package org.formation.model

trait Persistent {

	boolean save() {
		println "Saving ${this.dump()}"
		true
	}
}
