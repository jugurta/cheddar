package ubo.plugin.cheddar.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;
import java.lang.Thread;
import org.eclipse.core.runtime.IProgressMonitor;

import org.osate.aadl2.*;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.ui.dialogs.Dialog;
import org.osate.ui.handlers.AaxlReadOnlyHandlerAsJob;

import com.jugurta.Launch;

public class CheddarLaunch extends AaxlReadOnlyHandlerAsJob {

	public final static String cheddarPath = "cheddar.exe  ";

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

			Dialog.showError("Error ", new Util().Connection(si));
			Dialog.showInfo("Components", new Util().parse(si));
			String response = new Util().features(si);
			System.out.println("The XML file is generated on the path :  " + response);

			new Thread(new Runnable() {
				@Override
				public void run() {
					String log_info = WriteLog(new Launch().executeCommand(cheddarPath + response));
					System.out.println("The Log file is generated on the path : " + log_info);
				}
			}).start();

		} catch (IOException e) {
			System.err.println("Error when writing the file");
			Dialog.showError("Error ", "Error when writing the file");
		}

	}

	public void invoke(IProgressMonitor monitor, SystemInstance root) {
		actionBody(monitor, root);
	}

	private String WriteLog(String Logcontent) {

		String directory_name = "Logs/";
		String log_filename = "log.txt";
		String path = "";
		new Thread(new Runnable() {

			@Override
			public void run() {
				new File(directory_name).mkdir();

				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(directory_name + log_filename));
					out.write(Logcontent);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		path = new File(directory_name + log_filename).getAbsolutePath();

		return path;
	}

}
