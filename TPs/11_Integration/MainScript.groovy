package org.formation

import org.formation.dynamic.CSVExporter
import org.formation.dynamic.JsonExporter
import org.formation.dynamic.XMLExporter
import org.formation.model.DBHelper
import org.formation.model.Index
import org.formation.model.IndexDAO
import org.formation.service.IndexService
import org.formation.service.Indexer

import groovy.transform.Field
import groovy.xml.MarkupBuilder
import groovy.sql.Sql
import java.sql.Timestamp


@Field
def builder = Indexer.builder()
@Field @Delegate
Indexer indexer = builder.tokenizer('b').filters(_getFilters()).profilers([]).build()

Index[] indexes = []
Thread[] threads = []
def pattern = ~/text.*\.txt/
def dirname = '/home/dthibau/Formations/Groovy'


new File("$dirname").eachDirRecurse { dir ->

	dir.eachFileMatch(pattern) {    myfile ->
		println  "$myfile"
		def i = new Index(source : myfile.text, created : new Date())
		indexes += i
		threads += Thread.start({ buildIndex(i) })
	} // eachFileMatch

}


for ( t in threads ) {
	t.join();
}

println "Build ${indexes.size()} index(es)"


def connection= DBHelper.connection
def indexDAO = new IndexDAO(db : connection)


// Store in DB
use (XMLExporter, JsonExporter) {
	indexes.each {
		it.metaClass.xmlMap = it.asXML()
		it.metaClass.jsonMap = it.asJson()
		indexDAO.create([new Timestamp(it.created.time),new Timestamp(it.indexed.time),it.xmlMap,it.jsonMap])
	}
}

// Read From DB
indexDAO.findAll().each { 
	println "One Index with id ${it.id}"
}

// Update tout
indexDAO.findAll().each {	
	indexDAO.update('xmlMap','dummy',it.id)
}


// Supprimer tout
indexDAO.findAll().each {
	println "Removing ${it.id}"
	indexDAO.delete(it.id)
}

// Check there is nobody in table
assert indexDAO.findAll().isEmpty()

connection.close()


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
