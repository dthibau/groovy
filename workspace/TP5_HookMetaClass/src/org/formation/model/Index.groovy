package org.formation.model

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
	@Override
	public String toString() {
		return "Index [created=" + created + ", indexed=" + indexed + ",\n map=" + map + "]";
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
	
//	Object invokeMethod(String methodName, Object args) {
//		println 'Invoke Method for ' + methodName
//		return getMetaClass().invokeMethod(this, methodName, args);
//	}
}

