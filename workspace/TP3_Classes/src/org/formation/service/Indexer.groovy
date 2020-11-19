package org.formation.service

import org.formation.model.Index

class Indexer {
	String tokenizer
	int minimalSize =2

	

	private def fillMap = {
		int occurence = map.get(it.toLowerCase(),0);
		map[it.toLowerCase()]=occurence+1;
	}

	public Index buildIndex(Index index) {
		println 'Indexation with ' + minimalSize + " " + tokenizer
		String texte = index.source.toString()
		index.map = _index(texte)
		index.indexed = new Date()
		index
	}

	private Map _index(String texte) {
		def words = texte.split(/\${tokenizer}/)
		def map = [:]

		words.findAll({it.size()>minimalSize}).each({
			int occurence = map.get(it.toLowerCase(),0);
			map[it.toLowerCase()]=occurence+1;
		})
		return map
	}
}
