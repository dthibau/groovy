package org.formation

import java.time.LocalDate

import org.formation.dynamic.CSVExporter
import org.formation.dynamic.ChainedInterceptor
import org.formation.model.Index
import org.formation.model.Persistent
import org.formation.model.PersistentIndex
import org.formation.service.Indexer
import org.formation.service.PersistentService

import groovy.transform.Field

@Field @Delegate
		Indexer indexer = Indexer.builder().tokenizer(/\b/).filters( _filters()).build()
def proxyMetaClass = ProxyMetaClass.getInstance(Indexer)
proxyMetaClass.interceptor = new ChainedInterceptor()
indexer.metaClass = proxyMetaClass

def indexes = []
def threads = []
def dirname = /C:\Users\Administrateur\groovy/
new File("$dirname").eachDirRecurse { dir ->

	dir.eachFileMatch(~/.*\.txt/) { f ->
		println f
		def i = new Index(source: f.text, created : LocalDate.now())
		indexes += i
		threads += Thread.start({ buildIndex(i) })
	}
}

for ( t in threads ) {
	t.join();
}

println "Benchmark \n" + proxyMetaClass.interceptor.benchmark.statistic()
// println "Tracer \n" + proxyMetaClass.interceptor.tracer.writer.toString()

// Utilisation de Use
use ( CSVExporter ) {
	indexes.each {
		println it.exportCSV()

	}
}

// Ajout dynamique de la méthode export via la méta-classe
println '***** Ajout dynamique d\'une méthode export'
println index.metaClass
index.metaClass.export = {
	-> def ret=''
	map.each {
		ret += it.key + '\t'
		ret += it.value + '\n'
	}
	ret
}
println index.metaClass
println index.export()


println "Benchmark \n" + proxyMetaClass.interceptor.benchmark.statistic()
println "Tracer \n" + proxyMetaClass.interceptor.tracer.writer.toString()

// Recherche dans l'index
assert !index.hasTitiAndTutu()

assert index.hasHiverAndPollution()

assert !index.hasHiverAndPollutionAndToto()

try {
	index.isHiver()
} catch (MissingMethodException e ) {
	println e
}

assert !index.titiAndtutu

assert index.hiverAndPollution

assert !index.HiverAndPollutionAndToto



private def _filters() {
	def lowerCase = { words ->
		def start = System.nanoTime()
		words = words.collect { it.toLowerCase() }
		profilers += (System.nanoTime()-start)
		words
	}
	def minimal4 = { words ->
		def start = System.nanoTime()
		words = words.findAll {it.size() > 4}
		profilers += (System.nanoTime()-start)
		words
	}
	def stopWords = { words ->
		def start = System.nanoTime()
		words = words.findAll {!(it ==~ /delhi|chine|pekin|londres/)}
		profilers += (System.nanoTime()-start)
		words
	}
	[
		lowerCase,
		minimal4,
		stopWords
	]
}


// Trait implements statique
PersistentService persistentService = new PersistentService()

PersistentIndex persistentIndex = new PersistentIndex(source : text, created : LocalDate.now())
persistentService.save(persistentIndex)

println '****** Dynamic implements *******'
index = new Index(source : text, created : LocalDate.now()) as Persistent

persistentService.save(index)