package org.formation.service

import org.formation.model.Index

class Indexer {
	String tokenizer
	
	List<Closure> filters = []

	List<Integer> profilers = []
	
	public Index buildIndex(Index index) {
		def texte = index.source.toString()
		def words = texte.split(tokenizer)
		
		println words;		
		
		filters.each { c -> 
			c.delegate = this
			c.resolveStrategy = Closure.DELEGATE_FIRST
			words = c.call(words);
			println 'Words after filter applied : ' + words
		}
		words.each({
			def occurence = index.keywords.get(it,0);
			index.keywords[it]=occurence+1;
		})
		
		index
	}


}
