package step5

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import step2.model.Direction
import step2.model.Robot

def binding = new Binding(robot: new Robot())

def importCustomizer = new ImportCustomizer()
importCustomizer.addStaticStars Direction.name
def config = new CompilerConfiguration()
config.addCompilationCustomizers importCustomizer
config.scriptBaseClass = RobotBaseScript.name

def shell = new GroovyShell(this.class.classLoader, binding, config)
shell.evaluate new File('/home/dthibau/Formations/Groovy/MyWork/workspace/TP13_DSL/src/step5/move.txt')