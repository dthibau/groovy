package org.formation.dynamic

import org.formation.model.Index

@Category(Index)
class CSVExporter {

	def store() {
		def ret=''
		map.each {
			ret += it.key + '\t'
			ret += it.value + '\n'
		}
		ret
	}
}
