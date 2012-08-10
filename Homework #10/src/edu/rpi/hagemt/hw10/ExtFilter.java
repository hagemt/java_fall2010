package edu.rpi.hagemt.hw10;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Specialized FileFilter implementation that requires Mode-enumerated file extensions.
 * @see Mode
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class ExtFilter extends FileFilter {
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String path = f.getName().toUpperCase();
		for (Mode m : Mode.values()) {
			if (path.endsWith("." + m.name())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder("Only");
		for (Mode m : Mode.values()) {
			sb.append(" *." + m.name());
		}
		return sb.append(" files").toString();
	}
}
