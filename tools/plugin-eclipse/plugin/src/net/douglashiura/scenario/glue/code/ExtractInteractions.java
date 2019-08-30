package net.douglashiura.scenario.glue.code;

import java.util.ArrayList;
import java.util.List;

import net.douglashiura.us.serial.Interaction;

public class ExtractInteractions {

	public static List<Interaction> from(List<Interaction> scenarios) {

		List<Interaction> interactions = new ArrayList<>();
		for (Interaction interaction : scenarios) {
			add(interaction, interactions);
		}
		return interactions;
	}

	private static void add(Interaction interaction, List<Interaction> interactions) {
		interactions.add(interaction);
		if(interaction.getTransaction()!=null){
			add(interaction.getTransaction().getTarget(), interactions);
		}
	}

}
