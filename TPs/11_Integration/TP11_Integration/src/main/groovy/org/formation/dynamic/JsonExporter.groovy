package org.formation.dynamic

import org.formation.model.Index

import groovy.json.JsonOutput


@Category(Index)
class JsonExporter {

	def asJson() {
		
		JsonOutput.prettyPrint(JsonOutput.toJson(map))
	}
}
