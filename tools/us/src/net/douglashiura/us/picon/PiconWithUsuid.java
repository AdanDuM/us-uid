package net.douglashiura.us.picon;

import java.lang.reflect.Field;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.Arquivos;
import net.douglashiura.picon.linguagem.Bloco;
import net.douglashiura.picon.preguicoso.Contexto;

public class PiconWithUsuid {

	private Contexto contexto;

	public PiconWithUsuid() throws ProblemaDeCompilacaoException {
		contexto = new Contexto(Arquivos.getInstance().explodir());
	}

	public void settings(Object object) throws ProblemaDeCompilacaoException, IllegalAccessException {

		Field[] declarado = object.getClass().getDeclaredFields();
		for (Field field : declarado) {
			field.setAccessible(true);
			if (field.get(object) == null) {
				try {
					field.set(object, contexto.get(Bloco.camelCase(field.getName())));
				} catch (Exception nonInstance) {
				}
			}
		}
	}
}
