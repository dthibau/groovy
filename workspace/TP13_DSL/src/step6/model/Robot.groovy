package step6.model

import step6.model.Direction

class Robot {
	void move(Direction dir) {
		move([by:'1m'],dir)
	}
	void move(Map m, Direction dir) {
		println "robot moved $dir by $m.by at ${m.at ?: '1 km/h'}"
	}
}
