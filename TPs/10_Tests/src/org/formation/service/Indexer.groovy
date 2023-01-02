package org.formation.service

import org.formation.model.Index

import groovy.transform.Memoized
import groovy.transform.builder.Builder
import groovy.util.logging.Log

@Builder 
@Log
class Indexer {
	String tokenizer
	List<Closure> filters 
	List<Integer> profilers 

	@Memoized
	public Index buildIndex(Index index) {
		log.info('Indexation with tokenizer ' + tokenizer + ' and filters :' + filters)
		String texte = index.source.toString()
		def words = texte.split(/\${tokenizer}/)

		log.fine('Words before filters applied : ' + words)
		for (closure in filters) {
			closure.delegate = this
			closure.resolveStrategy = Closure.DELEGATE_FIRST
			words = closure.call(words);
			log.fine('Words after filter applied : ' + words)
		}
		def map = [:]
		words.each({
			int occurence = map.get(it,0);
			map[it]=occurence+1;
		})
		index.map = map
		index.indexed = new Date()

		index
	}
}
