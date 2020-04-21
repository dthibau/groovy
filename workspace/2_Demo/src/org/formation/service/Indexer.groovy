package org.formation.service

import java.time.LocalDate

import org.formation.model.Index

class Indexer {
	String tokenizer

	int minimalSize

	def buildIndex(Index index) {
		index.map = _index(index.source)
		index.indexed = LocalDate.now()
	}

	private Map _index(String text) {
		def map = [:]
		def words = text.split(tokenizer)

		//		words.each fillMap

		words.findAll { it.size() > minimalSize } each { element ->
			def occurence = map.get(element,0)
			map[element] = occurence+1
		}

		map
	}
}
