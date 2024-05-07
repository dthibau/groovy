package org.formation.service

import java.time.LocalDate

import org.formation.model.Index

import groovy.util.logging.Log

@Log
class Indexer {
	String tokenizer = /\b/
	int minimalSize =2
	List<Closure> filters = []

	
	public Index buildIndex(source) {
		log.info("On rentre dans buildIndex")
		def texte = source.toString()
		def words = texte.split(tokenizer)
		log.fine("Voici la liste de mots $words")
		
		filters.each { c ->
			words = c.call(words);
			println 'Words after filter applied : ' + words
		}
		Index index = new Index(source:source)
		
		words.findAll({it.size()>minimalSize}).each({
			def occurence = index.keywords.get(it.toLowerCase(),0);
			index.keywords[it.toLowerCase()]=occurence+1;
		})
		
		index.indexedDate = LocalDate.now()
		index
	}


}
