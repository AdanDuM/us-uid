package net.douglashiura.scenario.plugin.view;

import java.util.List;

import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ItemScenario;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import net.douglashiura.scenario.plugin.editor.run.Executor;
import net.douglashiura.scenario.plugin.type.Rateable;
import net.douglashiura.scenario.project.util.FileScenario;
import net.douglashiura.us.serial.Result;

public class ViewTests extends ViewPart {

	private Tree arvoreDeElementos;
	private Label saidaValor;
	private Label saidaFixture;
	private Text saidaResultado;
	private Composite resultado;
	private Text entradaRuns;
	private Text entradaErrors;
	private Text entradaFailures;

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout containerLayout = new GridLayout();
		containerLayout.numColumns = 1;
		container.setLayout(containerLayout);

		construirPainelDeComandos();
		construirPainelExecucao(container);
		construirPainelArvore(container);
		construirPainelResultado(container);
		Runner.getRunner().setViewTests(this);
	}

	private void construirPainelDeComandos() {
		IActionBars bars = getViewSite().getActionBars();
		ActionRun actionRun = new ActionRun(this);
		ActionRunAll actionRunAll = new ActionRunAll(this);
		bars.getMenuManager().add(actionRun);
		bars.getMenuManager().add(new Separator());
		bars.getMenuManager().add(actionRunAll);
		bars.getToolBarManager().add(actionRun);
		bars.getToolBarManager().add(actionRunAll);
	}

	private Composite construirPainelExecucao(Composite container) {
		Composite execucao = new Composite(container, SWT.NONE);
		GridLayout execucaoLayout = new GridLayout();
		GridData execucaoLayoutData = new GridData();
		execucaoLayout.numColumns = 3;
		execucaoLayoutData.horizontalAlignment = GridData.CENTER;
		execucaoLayoutData.grabExcessHorizontalSpace = true;
		execucao.setLayout(execucaoLayout);
		execucao.setLayoutData(execucaoLayoutData);

		GridLayout caixaLayout = new GridLayout();
		caixaLayout.numColumns = 2;
		Composite caixaRuns = new Composite(execucao, SWT.BORDER);
		Composite caixaErros = new Composite(execucao, SWT.BORDER);
		Composite caixaFailures = new Composite(execucao, SWT.BORDER);
		caixaRuns.setLayout(caixaLayout);
		caixaErros.setLayout(caixaLayout);
		caixaFailures.setLayout(caixaLayout);

		Label etiquetaRuns = new Label(caixaRuns, SWT.NONE);
		Label etiquetaErrors = new Label(caixaErros, SWT.NONE);
		Label etiquetaFailures = new Label(caixaFailures, SWT.NONE);
		etiquetaRuns.setText("Runs:");
		etiquetaErrors.setText("Erros:");
		etiquetaFailures.setText("Failures:");

		entradaRuns = new Text(caixaRuns, SWT.SINGLE);
		entradaErrors = new Text(caixaErros, SWT.SINGLE);
		entradaFailures = new Text(caixaFailures, SWT.SINGLE);
		entradaRuns.setEnabled(false);
		entradaRuns.setEditable(false);
		entradaErrors.setEnabled(false);
		entradaFailures.setEnabled(false);

		return execucao;
	}

	private Composite construirPainelArvore(Composite container) {
		Composite arvore = new Composite(container, SWT.NONE);
		GridLayout arvoreLayout = new GridLayout();
		GridData arvoreLayoutData = new GridData();
		arvoreLayout.numColumns = 1;
		arvoreLayoutData.horizontalAlignment = GridData.FILL;
		arvoreLayoutData.grabExcessHorizontalSpace = true;
		arvoreLayoutData.verticalAlignment = GridData.FILL;
		arvoreLayoutData.grabExcessVerticalSpace = true;
		arvore.setLayout(arvoreLayout);
		arvore.setLayoutData(arvoreLayoutData);

		arvoreDeElementos = new Tree(arvore, SWT.BORDER);
		GridData arvoreElementosLayoutData = new GridData();
		arvoreElementosLayoutData.horizontalAlignment = GridData.FILL;
		arvoreElementosLayoutData.verticalAlignment = GridData.FILL;
		arvoreElementosLayoutData.grabExcessHorizontalSpace = true;
		arvoreElementosLayoutData.grabExcessVerticalSpace = true;
		arvoreDeElementos.setLayoutData(arvoreElementosLayoutData);
		arvoreDeElementos.addListener(SWT.Selection, new TreeSelection(this));
		return arvore;
	}

	private Composite construirPainelResultado(Composite container) {
		resultado = new Composite(container, SWT.NONE);
		GridLayout resultadoLayout = new GridLayout();
		GridData resultadoLayoutData = new GridData();
		resultadoLayout.numColumns = 1;
		resultadoLayoutData.horizontalAlignment = GridData.FILL;
		resultadoLayoutData.grabExcessHorizontalSpace = true;
		resultado.setLayout(resultadoLayout);
		resultado.setLayoutData(resultadoLayoutData);

		Composite caixaFixture = new Composite(resultado, SWT.BORDER);
		GridLayout caixaFixtureLayout = new GridLayout();
		GridData caixaFixtureLayoutData = new GridData();
		caixaFixtureLayout.numColumns = 2;
		caixaFixtureLayoutData.horizontalAlignment = GridData.FILL;
		caixaFixtureLayoutData.grabExcessHorizontalSpace = true;
		caixaFixture.setLayout(caixaFixtureLayout);
		caixaFixture.setLayoutData(caixaFixtureLayoutData);
		Label etiquetaFixture = new Label(caixaFixture, SWT.NONE);
		etiquetaFixture.setText("Fixture:");
		saidaFixture = new Label(caixaFixture, SWT.NONE);

		Composite caixaValor = new Composite(resultado, SWT.BORDER);
		GridLayout caixaValorLayout = new GridLayout();
		GridData caixaValorLayoutData = new GridData();
		caixaValorLayout.numColumns = 2;
		caixaFixtureLayout.numColumns = 2;
		caixaValorLayoutData.horizontalAlignment = GridData.FILL;
		caixaValorLayoutData.grabExcessHorizontalSpace = true;
		caixaValor.setLayout(caixaValorLayout);
		caixaValor.setLayoutData(caixaValorLayoutData);
		Label etiquetaValor = new Label(caixaValor, SWT.NONE);
		etiquetaValor.setText("Value:");
		saidaValor = new Label(caixaValor, SWT.NONE);

		Composite caixaResultado = new Composite(resultado, SWT.BORDER);
		GridLayout caixaResultadoLayout = new GridLayout();
		GridData caixaResultadoLayoutData = new GridData();
		caixaResultadoLayout.numColumns = 1;
		caixaResultadoLayoutData.horizontalAlignment = GridData.FILL;
		caixaResultadoLayoutData.grabExcessHorizontalSpace = true;
		caixaResultadoLayoutData.heightHint = 150;
		caixaResultado.setLayout(caixaResultadoLayout);
		caixaResultado.setLayoutData(caixaResultadoLayoutData);

		Label etiquetaResultado = new Label(caixaResultado, SWT.NONE);
		etiquetaResultado.setText("Result:");
		saidaResultado = new Text(caixaResultado, SWT.MULTI);
		return resultado;
	}

	public void popularArvore(List<FileScenario> nodes) {
		arvoreDeElementos.removeAll();
		for (FileScenario node : nodes) {
			ItemScenario senario = new ItemScenario(arvoreDeElementos, node);
			node.setNotificable(senario);
		}
	}

	public void setResult(Result result, Rateable rateable) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				saidaValor.setText(rateable.getValue());
				saidaFixture.setText(rateable.getFixtureName());
				saidaResultado.setText(result.getActual() == null ? "" : result.getActual());
				resultado.layout();
				saidaValor.getParent().layout();
				saidaFixture.getParent().layout();
				saidaResultado.getParent().layout();
			}
		});
	}

	@Override
	public void setFocus() {

	}

	public void setExecutionAmounts(Executor executor) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				entradaErrors.setText(executor.getErrors().toString());
				entradaFailures.setText(executor.getFaults().toString());
				entradaRuns.setText(String.format("%s/%s", executor.getComplete(), executor.getTotal()));
			}
		});
	}

	public Tree getTree() {
		return arvoreDeElementos;
	}

}