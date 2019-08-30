package net.douglashiura.leb.uid.scenario.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.douglashiura.leb.uid.scenario.data.ProjectScenario;

@WebListener
public class ListenerConfigProjectScenario implements ServletContextListener {

	public static final String SCENARIO_APP = "SCENARIO";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg) {
		arg.getServletContext().setAttribute(SCENARIO_APP, new ProjectScenario());
	}

}
