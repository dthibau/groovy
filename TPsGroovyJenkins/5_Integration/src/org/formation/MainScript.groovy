package org.formation

import java.util.logging.Logger

import org.formation.service.Indexer

import groovy.beans.Bindable
import groovy.json.JsonOutput



Logger log = Logger.getLogger("org.formation.MainScript")



def text='''Pour le deuxième hiver consécutif, Delhi étouffe sous la pollution. Dans la nuit du 6 au 7 novembre, alors que les températures chutaient à 
l’approche de l’hiver, quand le vent s’est arrêté de souffler, des milliards de milliards de particules fines ont été prises au piège dans l’atmosphère 
de la capitale indienne. En 2015, la pollution atmosphérique a entraîné 525 000 morts prématurées en Inde. En Chine, en décembre 2016, quelque 
460 millions de personnes ont été affectées par le smog de Pékin.
Les résultats d’une étude publiée en novembre 2016 sur le site de la revue 
Proceedings of the National Academy of Sciences montrent que le smog en Chine et le 
brouillard de Londres qui, au cours de l’hiver 1952, tua quelque 12 000 personnes en cinq 
jours sont dus à des processus de réaction chimique similaires. Le responsable n’est autre que 
le dioxyde d’azote issu de la combustion du charbon. Mélangé au dioxyde de soufre, issu de la 
même combustion, il crée un acide sulfurique et un brouillard épais'''

Closure toLowerCase = { words ->
	words.collect({it.toLowerCase()})
}

Closure minimal3 = { w ->
	w.findAll {it.size() >= 3 }
}

Closure stopWords = { words ->
	words.findAll {![
			'delhi',
			'londres',
			'pékin',
			'chine'
		].contains(it.toLowerCase())}
}

// Instancier un indexeur
def indexer = new Indexer()

println "$dirname"
println "$pattern"

def indexes = []
new File("$dirname").eachDirRecurse { dir ->

	// println "$dir"
	
	dir.eachFileMatch(~pattern) { file ->
		println  "$file"
		indexes << indexer.buildIndex(file.text)
	} // eachFileMatch
}
log.info("From MainScript $indexes");


def writer = new StringWriter()
def builder = new groovy.xml.MarkupBuilder(writer)

builder.indexes {
	description 'Tous les index'
	indexes.each { i ->
		index (creationDate: i.createdDate, indexationDate: i.indexedDate) {		
			i.keywords.each { entry ->
				keyword(occurence:entry.value, entry.key)
			}
		}
	}
}

println writer

indexes.each { 
	log.info(JsonOutput.prettyPrint(JsonOutput.toJson(it)))
}



