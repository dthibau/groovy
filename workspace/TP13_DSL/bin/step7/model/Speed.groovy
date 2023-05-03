package step7.model

import groovy.transform.TupleConstructor

@TupleConstructor
class Speed {
	Number amount
	DistanceUnit unit
	String toString() { "$amount $unit/h" }
}
