package org.formation

import org.formation.model.Index
import org.formation.service.Indexer

import groovy.transform.Field
import groovy.xml.MarkupBuilder



@Field
def builder = Indexer.builder()
@Field @Delegate
Indexer indexer = builder.tokenizer('b').filters(_getFilters()).profilers([]).build()

Index[] indexes = []
Thread[] threads = []
def pattern = ~/text.*\.txt/
def dirname = /C:\Users\Administrateur\groovy/
new File("$dirname").eachDirRecurse { dir ->

	dir.eachFileMatch(pattern) {    myfile ->
		println  "$myfile"
		def i = new Index(myfile.text)
		indexes += i
		threads += Thread.start({ 
			println delegate 
			println indexes
			buildIndex(i) })
	} // eachFileMatch

}


for ( t in threads ) {
	t.join();
}

println indexes


def writer = new StringWriter()
markupBuilder = new MarkupBuilder(writer)
markupBuilder.indexes {
	description 'Content of all indexes'
	for (i in indexes) {
		index (begin: i.source[0..20]) {
			i.map.each { entry ->
				keyword(occurence:entry.value, entry.key)
			}
		}
	}
}

println writer


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