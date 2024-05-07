package org.formation.model

trait Persistent {

	boolean save() {
		println '#############################'
		println "Saving ${this.dump()}"
		true
	}
}
