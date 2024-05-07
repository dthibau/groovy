package org.formation.model

import java.time.LocalDate

import groovy.transform.ToString

@ToString(excludes="source")
class Index {
	def source
	LocalDate createdDate = LocalDate.now(), indexedDate
	def keywords = [:]
	
	
	def methodMissing(String name, Object args) {
		if ( !name.startsWith('has') )
			throw new MissingMethodException(name, Index, args)
		def terms = (name - 'has').split('And')
		def ret = true
		for ( term in terms ) {
			ret =  keywords.any{entry -> entry.key.toLowerCase() == term.toLowerCase()}
			if ( !ret )
				break;
		}
		ret
	}
	

}
