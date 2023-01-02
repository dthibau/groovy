package org.formation.test

import org.junit.Test
import static groovy.test.GroovyAssert.assertEquals
import static groovy.test.GroovyAssert.notYetImplemented
import static groovy.test.GroovyAssert.fail

class SimpleUnitTest {

	@Test
	void testEquals() {
		assertEquals("Groovy should add correctly", 2, 1 + 1)
	}
	
	@Test
	public void testNotImplemented() {
		
		if (notYetImplemented(this)) return
			fail("will be implemented tomorrow")
	}
	
}
