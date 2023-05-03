package org.formation.model



import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes=['source'])
@EqualsAndHashCode(excludes=['created','indexed'])
class Index implements Persistent {
	Date created,indexed
	def source

	Map map
	
	public Index (def source) {
		this.source = source
		created = new Date()
		map = [:]
		indexed = null
	}

	
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
//		print 'size='+this.metaClass.methods.size()
//		this.metaClass."$name" = { ret }
//		print 'size='+this.metaClass.methods.size()
		ret
	}
	def getProperty(String propertyName) {
		if (metaClass.hasProperty(this, propertyName)) {
		return metaClass.getProperty(this, propertyName)
		}
		invokeMethod 'has'+propertyName, null

	}
	

}

