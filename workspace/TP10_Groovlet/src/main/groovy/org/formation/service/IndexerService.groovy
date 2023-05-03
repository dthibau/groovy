package org.formation.service

import org.formation.service.Indexer

@Singleton(lazy=true,strict=false)
class IndexerService {
    
    @Delegate
    Indexer indexer

    public IndexerService() {
        indexer = Indexer.builder().tokenizer('b').filters(_getFilters()).profilers([]).build()
    }

    def _getFilters() {
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
            words = words.findAll({it.size() > 4})
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