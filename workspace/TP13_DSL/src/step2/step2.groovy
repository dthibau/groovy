package step2



def shell = new GroovyShell(this.class.classLoader)
shell.evaluate '''
import step2.model.Robot
import static step2.model.Direction.*
def robot = new Robot()
robot.move left
'''

