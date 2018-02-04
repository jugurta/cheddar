package ubo.plugin.cheddar.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.ui.dialogs.Dialog;
import org.osate.ui.handlers.AaxlReadOnlyHandlerAsJob;

import com.jugurta.Launch;


public class CheddarLaunch extends AaxlReadOnlyHandlerAsJob {

	public final static String cheddarPath = "cheddar  ";

	@Override
	public String getMarkerType() {
		return "org.osate.CheddarLaunch";
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
			Dialog.showError("Error", "The selected file is not at the right format");
			monitor.done();
			return;
		}

		SystemInstance si = ((ComponentInstance) obj).getSystemInstance();
		try {
			String response = new Util().features(si);
			Dialog.showInfo("Info", "The XML file is generated on the path : \n " + response);
			String log_info = WriteLog(new Launch().executeCommand(cheddarPath + response));
			Dialog.showInfo("Info", "The Log file is generated on the path : \n " + log_info);
		} catch (IOException e) {
			Dialog.showError("Error ", "Error when writing the file");
		}

	}

	public void invoke(IProgressMonitor monitor, SystemInstance root) {
		actionBody(monitor, root);
	}

	private String WriteLog(String Logcontent) throws IOException {
		String directory_name = "Logs/";
		String log_filename = "log.txt";
		String path = "";
		new File(directory_name).mkdir();
		BufferedWriter out = new BufferedWriter(new FileWriter(directory_name + log_filename));

		try {
			out.write(Logcontent);
			out.close();
			path = new File(directory_name + log_filename).getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
