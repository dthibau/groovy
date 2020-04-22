package step6.model

import groovy.transform.TupleConstructor

@TupleConstructor
class Distance {
	Number amount
	DistanceUnit unit
	String toString() { "$amount$unit" }
}
