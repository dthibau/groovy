package org.formation.service

class IndexerFactory {

	static getDefaultIndexer() {
		def builder = Indexer.builder()
		builder.tokenizer('b').filters(_getFilters()).profilers([]).build()
	}

	static _getFilters() {
		List<Closure> filters =[]
		filters += { words ->
			def start = System.nanoTime()
			words = words.collect({it.toLowerCase()})
			def stop = System.nanoTime()
			profilers += (stop-start)
			words
		}
		filters += { words ->
			def start = System.nanoTime()
			words = words.findAll({it.size() > 3})
			def stop = System.nanoTime()
			profilers += (stop-start)
			words
		}
		filters += { words ->
			def start = System.nanoTime()
			words = words.findAll({!(it ==~ /delhi|chine|pekin|londres/)})
			def stop = System.nanoTime()
			profilers += (stop-start)
			words
		}
		filters
	}
}
