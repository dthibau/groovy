package org.formation.service

import org.formation.dynamic.JsonExporter
import org.formation.dynamic.XMLExporter
import org.formation.model.Index
import org.formation.model.IndexDAO

public class IndexService {

	Indexer indexer 
	def dao 
	
	def reindex(int id, String text) {
		// find the one
		def oldIndex = dao.findById(id)
		def index = new Index(source : text, created: oldIndex.created)
		
		index = indexer.buildIndex(index)
		use (XMLExporter, JsonExporter) {
			index.metaClass.xmlMap = index.asXML()
			index.metaClass.jsonMap = index.asJson()
			dao.update('indexed',index.indexed.toTimestamp(),id)
			dao.update('xmlmap',index.xmlMap,id)
			dao.update('jsonmap',index.jsonMap,id)
		}
		
		index
	}
}
