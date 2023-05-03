package org.formation

import org.formation.dynamic.CSVExporter
import org.formation.dynamic.ChainedInterceptor
import org.formation.model.Index
import org.formation.service.Indexer


def text='Pour le deuxième hiver consécutif, Delhi étouffe sous la pollution. Dans la nuit du 6 au 7 novembre, alors que les températures chutaient à \
l’approche de l’hiver, quand le vent s’est arrêté de souffler, des milliards de milliards de particules fines ont été prises au piège dans l’atmosphère \
de la capitale indienne. En 2015, la pollution atmosphérique a entraîné 525 000 morts prématurées en Inde. En Chine, en décembre 2016, quelque \
460 millions de personnes ont été affectées par le smog de Pékin.\
Les résultats d’une étude publiée en novembre 2016 sur le site de la revue \
Proceedings of the National Academy of Sciences montrent que le smog en Chine et le \
brouillard de Londres qui, au cours de l’hiver 1952, tua quelque 12 000 personnes en cinq \
jours sont dus à des processus de réaction chimique similaires. Le responsable n’est autre que \
le dioxyde d’azote issu de la combustion du charbon. Mélangé au dioxyde de soufre, issu de la \
même combustion, il crée un acide sulfurique et un brouillard épais'


def proxyMetaClass = ProxyMetaClass.getInstance(Indexer)
proxyMetaClass.interceptor = new ChainedInterceptor()

indexer = new Indexer([tokenizer:'b'])
indexer.metaClass = proxyMetaClass


indexer.filters += { words -> 
	def start = System.nanoTime()
	words = words.collect({it.toLowerCase()})
	def stop = System.nanoTime()
	profilers += (stop-start)
	words
}
indexer.filters += { words ->
	def start = System.nanoTime()
	words = words.findAll({it.size() > 4})
	def stop = System.nanoTime()
	profilers += (stop-start)
	words
}
indexer.filters += { words ->
	def start = System.nanoTime()
	words = words.findAll({!(it ==~ /delhi|chine|pekin|londres/)})
	def stop = System.nanoTime()
	profilers += (stop-start)
	words
}

index = new Index(text);

index = indexer.doIndex(index) 

println "Benchmark \n" + proxyMetaClass.interceptor.benchmark.statistic()
println "Tracer \n" + proxyMetaClass.interceptor.tracer.writer.toString()

println "filter performances :" + indexer.profilers
println index

try {
	index.isToto()
} catch (MissingMethodException e) {
	println 'isToto missing !'
}
println 'hasToto returns ' + index.hasToto()
println 'hasHiver returns ' + index.hasHiver()
println 'hasHiverAndPollution returns ' + index.hasHiverAndPollution()
println 'hasHiverAndTotoAndPollution returns ' + index.hasHiverAndTotoAndPollution()

println 'II : hasHiverAndPollution returns ' + index.hasHiverAndPollution
println 'index.hiver ' + index.hiver


index.save()

// Ajout dynamiquement de la méthode store via la méta-classe
println '***** Ajout dynamique d\'une méthode export'
index.metaClass.export = {-> def ret=''
	 map.each {
		 ret += it.key + '\t'
		 ret += it.value + '\n'
	 }
	 ret
}
println index.export()

// Usage d'une Catégorie
println '***** Utilisation Category CSVExporter'
use (CSVExporter) {
	println index.exportCSV()
}

