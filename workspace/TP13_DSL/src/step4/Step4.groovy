package step4

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
@BaseScript(step4.RobotBaseScript)
import groovy.transform.BaseScript
move left
'''