
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

def words = text.split(/\b/)

def map = [:]

def fillMap = { element -> 
	def occurence = map.get(element,0)
	map[element] = occurence+1
}

words.each fillMap

println map


// Step 2 :Filtrer les mots de moins de 2 caractères
map = [:]

words.findAll { w -> w.size() > 2 } each fillMap

println map

// Step 3 
map = new TreeMap()

words.findAll { w -> w.size() > 2 } each fillMap

println map

// Step 4
def invertedMap = new TreeMap()

map.each { entry ->
	int value = map.get(entry.key)
//	def mots = invertedMap.get(value,[])
//	mots += entry.key
	invertedMap[value] = invertedMap.get(value,[]) + entry.key
}

println invertedMap

// Step 5
println 'Top parade :'
(3..1).each {println invertedMap[it]}

