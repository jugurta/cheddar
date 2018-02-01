package ubo.plugin.cheddar.handlers;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.window.Window;
import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.ui.dialogs.Dialog;
import org.osate.ui.handlers.AaxlReadOnlyHandlerAsJob;

import com.jugurta.Launch;

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
		
		try {
			String response = new Util().features(si);
			
			
		//	Dialog.showInfo("Ferhat Touahria", "Fichier XML généré chemin: \n " + response);
			
			 new Launch().executeCommand("cheddar " + response);
			
		} catch (IOException e) {
			Dialog.showError("Erreur fichier", "Ecriture échouée");
		}

	}

	public void invoke(IProgressMonitor monitor, SystemInstance root) {
		actionBody(monitor, root);
	}
	
	
	
}
