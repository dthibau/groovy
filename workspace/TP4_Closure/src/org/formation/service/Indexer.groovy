package org.formation.service

import org.formation.model.Index

class Indexer {
	String tokenizer
	List<Closure> filters =[]

	List<Integer> profilers = []

	public Index doIndex(Index index) {
		println 'Indexation with tokenizer ' + tokenizer + ' and filters :' + filters
		String texte = index.source.toString()
		def words = texte.split(/\${tokenizer}/)


		for (closure in filters) {
			closure.delegate = this
			closure.resolveStrategy = Closure.OWNER_FIRST
			words = closure.call(words);
			println 'Words after filter applied : ' + words
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
