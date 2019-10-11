package net.douglashiura.us.run;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.us.CamelCase;
import net.douglashiura.us.ExceptionInExecution;
import net.douglashiura.us.UtilsLog;
import net.douglashiura.us.serial.Elementable;
import net.douglashiura.us.serial.Input;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Output;
import net.douglashiura.us.serial.Results;
import net.douglashiura.us.serial.Transaction;

public class RecursiveExecutor {

	private Executor executor;
	private Integer index;

	public RecursiveExecutor(Executor executor, Interaction firstState, Integer index) throws ExceptionInExecution {
		this.executor = executor;
		this.index = index;
		execute(firstState);
	}

	private void execute(Interaction interaction) throws ExceptionInExecution {
		Object instance = null;
		try {
			Class<?> klass = Annotations.getFixture(interaction.getFixtureName());			
			instance = klass.getConstructors()[0].newInstance();
			executor.message(interaction.getUuid(), index, Results.OK, null);
			executor.withPicon().settings(instance);
		} catch (InstantiationException | IllegalAccessException | IOException | ClassNotFoundException
				| IllegalArgumentException | URISyntaxException | ProblemaDeCompilacaoException
				| InvocationTargetException | SecurityException e) {
			messageError(interaction, e);
		} catch (NullPointerException nil) {
			messageError(interaction,
					new Exception("There is no class to the fixture name [" + interaction.getFixtureName() + "]"));
		}
		for (Output aOutput : interaction.getOutputs()) {
			execute(aOutput, instance);
		}
		for (Input aInput : interaction.getInputs()) {
			execute(aInput, instance);
		}
		if (interaction.getTransaction() != null) {
			execute(interaction.getTransaction(), instance);
		}
	}

	private void execute(Transaction transaction, Object instance) throws ExceptionInExecution {
		try {
			Class<?>[] cArg = new Class[]{String.class};
			Method method = instance.getClass().getMethod(CamelCase.to(transaction.getTarget().getFixtureName()), cArg);
			method.invoke(instance, transaction.getAction());
			executor.message(transaction.getUuid(), index, Results.OK, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NullPointerException e) {
			messageError(transaction, e);
		}
		execute(transaction.getTarget());
	}

	private void execute(Input input, Object instance) throws ExceptionInExecution {
		try {
			Class<?>[] cArg = new Class[] { String.class };
			Method method = instance.getClass().getMethod(CamelCase.set(input.getFixtureName()), cArg);
			method.invoke(instance, input.getValue());
			executor.message(input.getUuid(), index, Results.OK, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NullPointerException e) {
			messageError(input, e);
		}
	}

	private void execute(Output output, Object object) throws ExceptionInExecution {
		Method method = null;
		try {
			Class<?>[] cArg = new Class[0];
			method = object.getClass().getMethod(CamelCase.get(output.getFixtureName()), cArg);
			Object return_ = method.invoke(object);
			if (return_ != null && return_.equals(output.getValue())) {
				executor.message(output.getUuid(), index, Results.OK, return_.toString());
			} else {
				throw new ExceptionInExecution(output.getUuid(), Results.FAIL,
						return_ == null ? "null" : return_.toString());
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NullPointerException e) {
			messageError(output, e);
		}
	}

	private void messageError(Elementable elementable, Exception e) throws ExceptionInExecution {
		String msg = e.getMessage();
		e.printStackTrace();
		if (msg == null || "".equals(msg))
			msg = UtilsLog.toString(e);
		throw new ExceptionInExecution(elementable.getUuid(), Results.ERROR, msg);
	}
}