package ubo.plugin.cheddar.handlers;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.ui.dialogs.Dialog;
import org.osate.ui.handlers.AaxlReadOnlyHandlerAsJob;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CheddarLaunch extends AaxlReadOnlyHandlerAsJob {

	@Override
	public String getMarkerType() {
		return "org.osate.analysis.resource.budgets.CheddarLaunch";
	}

	@Override
	protected String getActionName() {
		return "Cheddar Action Plugin";
	}

	@Override
	public boolean initializeAction(NamedElement obj) {
		setCSVLog("Cheddar", obj);
		return true;
	}

	public void setSummaryReport() {
		this.summaryReport = new StringBuffer();
	}

	@Override
	public final void doAaxlAction(final IProgressMonitor monitor, final Element obj) {

		if (!(obj instanceof ComponentInstance)) {

			Dialog.showError("Erreur fichier", "Ce n'est pas le bon fichier");
			monitor.done();
			return;
		}

		SystemInstance si = ((ComponentInstance) obj).getSystemInstance();

		Dialog.showWarning("Ferhat Touahria", Util.features(si));

	}

	public void invoke(IProgressMonitor monitor, SystemInstance root) {
		actionBody(monitor, root);
	}
}
