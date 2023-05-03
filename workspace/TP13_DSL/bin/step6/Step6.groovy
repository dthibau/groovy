package step6

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import step6.model.Direction
import step6.model.DistanceCategory
import step6.model.Robot

def binding = new Binding(robot: new Robot())

def importCustomizer = new ImportCustomizer()
importCustomizer.addStaticStars Direction.name
def config = new CompilerConfiguration()
config.addCompilationCustomizers importCustomizer
config.scriptBaseClass = RobotBaseScript.name

def shell = new GroovyShell(this.class.classLoader, binding, config)

use(DistanceCategory) {
  shell.evaluate new File(/C:\Users\Administrateur\groovy\workspace\TP13_DSL\src\step6\move.txt/)
}