package net.douglashiura.us.run;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.us.ExceptionInExecution;
import net.douglashiura.us.picon.PiconWithUsuid;
import net.douglashiura.us.serial.InputFile;
import net.douglashiura.us.serial.Interaction;
import net.douglashiura.us.serial.Result;
import net.douglashiura.us.serial.Results;

public class Executor {

	private ObjectOutputStream write;
	private PiconWithUsuid picon;

	private Executor(ObjectOutputStream write) {
		this.write = write;

	}

	public void execute(Interaction firstState, Integer index) throws UnknownHostException, IOException {
		try {
			picon = new PiconWithUsuid();
		} catch (ProblemaDeCompilacaoException e) {
			message(null, 0, Results.END, e.getMessage());
			e.printStackTrace();
		}
		try {

			new RecursiveExecutor(this, firstState, index);
			message(null, index, Results.END, null);
		} catch (ExceptionInExecution e) {
			message(e.getUuid(), index, e.getResult(), e.getMessage());
		}
		write.flush();
	}

	public void message(UUID uuid, Integer index, Results result, String actual) {
		try {
			write.writeObject(new Result(uuid, index, result, actual));
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", Integer.valueOf(args[0]));
		ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		Executor executor = new Executor(write);
		InputFile message;
		while ((message = (InputFile) inputStream.readObject()) != null) {
			executor.execute(message.getScenario(), message.getIndex());
		}
		socket.close();
	}

	public PiconWithUsuid withPicon() {
		return picon;
	}

}