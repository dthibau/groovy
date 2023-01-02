package org.formation.test

import static org.junit.Assert.*

import org.formation.model.Index
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static groovy.test.GroovyAssert.shouldFail

class IndexTest {
	
	def index = new Index()
	
	@BeforeEach
	public void setUp () {
		index.map=[toto:1,titi:2]
	}
	
	@Test
	public void testHasMethod() {
		
		assertTrue(index.hasToto())
		assert !index.hasTutu() 
		shouldFail(MissingMethodException) {
			index.isTiti()
		}
	}
	
	@Test
	public void testGetProperty() {		
		assert index.toto
		assert !index.tutu
		
	}

}
