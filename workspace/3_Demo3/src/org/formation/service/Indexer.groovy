package org.formation.service

import java.time.LocalDate

import org.formation.model.Index

class Indexer {
	String tokenizer

	List<Closure> filters

	def buildIndex(Index index) {
		index.map = _index(index.source)
		index.indexed = LocalDate.now()
	}

	private Map _index(String text) {
		def map = [:]
		def words = text.split(tokenizer)

		//		words.each fillMap

		for (closure in filters) {
			println closure.owner
			println closure.delegate
			
			words = closure.call(words);
			println 'Words after filter applied : ' + words
		}

		
		words.each { element ->
			def occurence = map.get(element,0)
			map[element] = occurence+1
		}

		map
	}
}
