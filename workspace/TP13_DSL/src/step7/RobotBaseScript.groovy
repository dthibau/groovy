package step7

import step7.model.Robot

abstract class RobotBaseScript extends Script {
	// @Lazy est nécessaire car les bindings après l’instanciation
	@Delegate @Lazy Robot robot = this.binding.robot
	}