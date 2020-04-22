package step7

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import step7.model.Direction
import step7.model.DistanceCategory
import step7.model.Robot
import step7.model.Duration

def binding = new Binding(
	robot: new Robot(),
	h: Duration.hour
	)

def importCustomizer = new ImportCustomizer()
importCustomizer.addStaticStars Direction.name
def config = new CompilerConfiguration()
config.addCompilationCustomizers importCustomizer
config.scriptBaseClass = RobotBaseScript.name

def shell = new GroovyShell(this.class.classLoader, binding, config)
use(DistanceCategory) {
  shell.evaluate new File(/C:\Users\Administrateur\groovy\workspace\TP13_DSL\src\step7\move.txt/)
}