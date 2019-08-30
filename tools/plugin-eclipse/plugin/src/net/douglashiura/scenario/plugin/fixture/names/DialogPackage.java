package net.douglashiura.scenario.plugin.fixture.names;


import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogPackage extends TitleAreaDialog {

    private Text txtPackage;

    private String _package;

    public DialogPackage(Shell parentShell) {
        super(parentShell);
    }

    @Override
    public void create() {
        super.create();
        setTitle("Package");
        setMessage("Enter with package", IMessageProvider.INFORMATION);
    }

    @Override
    protected org.eclipse.swt.widgets.Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createFirstName(container);

        return area;
    }

    private void createFirstName(Composite container) {
        Label lbtFirstName = new Label(container, SWT.NONE);
        lbtFirstName.setText("Package");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        txtPackage = new Text(container, SWT.BORDER);
        txtPackage.setLayoutData(dataFirstName);
    }

    



    @Override
    protected boolean isResizable() {
        return true;
    }

    private void saveInput() {
        _package = txtPackage.getText();

    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    public String getPackage() {
        return _package;
    }

}