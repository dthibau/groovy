package step3

import step2.model.Robot
import step2.model.Direction

def binding = new Binding(
		robot: new Robot(),
		*: Direction.values().collectEntries {

			[(it.name()): it]
		}
		)
def shell = new GroovyShell(this.class.classLoader, binding)
shell.evaluate '''
robot.move left
'''