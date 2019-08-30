package net.douglashiura.scenario.project.util;

import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public interface Notificable {

	void addResult(Result result, Rateable element);

	void finishyATestExecution(Results generalResult);

}
