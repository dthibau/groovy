package org.formation.model

import java.time.LocalDate

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes=['source'])
@EqualsAndHashCode(excludes=['created','indexed'])
class Index {
	LocalDate created,indexed
	String source
	Map map
	
	
	def methodMissing(String name, Object args) {
		if ( !name.startsWith('has') )
			throw new MissingMethodException(name, Index, args)
			
		def terms = (name - 'has').split('And')
		def ret = true
		for ( term in terms ) {
			ret =  map.any{entry -> entry.key.toLowerCase() == term.toLowerCase()}
			if ( !ret )
				break;
		}
		ret
	}
	
	def getProperty(String propertyName) {
		if (metaClass.hasProperty(this, propertyName)) {
			return metaClass.getProperty(this, propertyName)
		}
		invokeMethod 'has' + propertyName, null
	}
}
