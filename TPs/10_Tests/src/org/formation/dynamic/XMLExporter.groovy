package org.formation.dynamic

import org.formation.model.Index

import groovy.xml.MarkupBuilder

@Category(Index)
class XMLExporter {

	def asXML() {
		def writer = new StringWriter()
		def markupBuilder = new MarkupBuilder(writer)
		markupBuilder.tokens {
			markupBuilder.description 'All tokens'
			map.each { entry ->
				markupBuilder.token(occurence:entry.value, entry.key)
			}
		}
		writer.toString();
	}
}
