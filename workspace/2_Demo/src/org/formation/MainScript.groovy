package org.formation

import java.time.LocalDate

import javax.swing.text.html.MinimalHTMLWriter

import org.formation.model.Index
import org.formation.model.Persistent
import org.formation.model.PersistentIndex
import org.formation.service.Indexer
import org.formation.service.PersistentService

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


def index = new Index(source : text, created : LocalDate.now())

println index

Indexer indexer = new Indexer(tokenizer : /\b/, minimalSize: 3)

indexer.buildIndex(index)

println index

// Trait implements statique
PersistentService persistentService = new PersistentService()

PersistentIndex persistentIndex = new PersistentIndex(source : text, created : LocalDate.now())
persistentService.save(persistentIndex)

println '******'
index = new Index(source : text, created : LocalDate.now()) as Persistent

persistentService.save(index)

