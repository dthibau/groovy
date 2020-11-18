package step1
import static step1.Direction.*
enum Direction {
left, right, forward, backward
}
class Robot {
void move(Direction dir) {
println "robot moved $dir"
}
}
def robot = new Robot()
robot.move left 