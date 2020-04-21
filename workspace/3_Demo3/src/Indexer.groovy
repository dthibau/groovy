
def text='''Pour le deuxi�me hiver cons�cutif, Delhi �touffe sous la pollution. Dans la nuit du 6 au 7 novembre, alors que les temp�ratures chutaient � 
l�approche de l�hiver, quand le vent s�est arr�t� de souffler, des milliards de milliards de particules fines ont �t� prises au pi�ge dans l�atmosph�re 
de la capitale indienne. En 2015, la pollution atmosph�rique a entra�n� 525 000 morts pr�matur�es en Inde. En Chine, en d�cembre 2016, quelque 
460 millions de personnes ont �t� affect�es par le smog de P�kin.
Les r�sultats d�une �tude publi�e en novembre 2016 sur le site de la revue 
Proceedings of the National Academy of Sciences montrent que le smog en Chine et le 
brouillard de Londres qui, au cours de l�hiver 1952, tua quelque 12 000 personnes en cinq 
jours sont dus � des processus de r�action chimique similaires. Le responsable n�est autre que 
le dioxyde d�azote issu de la combustion du charbon. M�lang� au dioxyde de soufre, issu de la 
m�me combustion, il cr�e un acide sulfurique et un brouillard �pais'''

def words = text.split(/\b/)

def map = [:]

def fillMap = { element -> 
	def occurence = map.get(element,0)
	map[element] = occurence+1
}

words.each fillMap

println map


// Step 2 :Filtrer les mots de moins de 2 caract�res
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

