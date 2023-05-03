package org.formation.service

import java.time.LocalDate

import org.formation.model.Index

import groovy.transform.Memoized
import groovy.transform.builder.Builder
import groovy.util.logging.Log

@Builder(excludes='profilers')
@Log
class Indexer {
	String tokenizer

	List<Closure> filters
	
	List profilers = []

	@Memoized
	def buildIndex(Index index) {
		log.info('Building index')
		index.map = _index(index.source)
		index.indexed = LocalDate.now()
	}

	private Map _index(String text) {
		def map = [:]
		def words = text.split(tokenizer)

		//		words.each fillMap

		for (closure in filters) {
			log.fine("Owner is ${closure.owner}")
			log.fine("Delegate is ${closure.delegate}")
			log.fine("Statégie de résolution ${closure.resolveStrategy}")
			
			closure.delegate = this
			
			words = closure.call(words);
			log.fine(" Words after filter applied : $words")
		}

		
		words.each { element ->
			def occurence = map.get(element,0)
			map[element] = occurence+1
		}

		map
	}
}
