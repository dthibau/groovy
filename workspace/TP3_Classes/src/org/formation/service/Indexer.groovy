package org.formation.service

import java.time.LocalDate
import org.formation.model.Index

class Indexer {
	String tokenizer
	int minimalSize =2

	
	public Index buildIndex(Index index) {
		def texte = index.source.toString()
		def words = texte.split(tokenizer)
		
		words.findAll({it.size()>minimalSize}).each({
			def occurence = index.keywords.get(it.toLowerCase(),0);
			index.keywords[it.toLowerCase()]=occurence+1;
		})
		
		index.indexedDate = LocalDate.now()
		index
	}


}
