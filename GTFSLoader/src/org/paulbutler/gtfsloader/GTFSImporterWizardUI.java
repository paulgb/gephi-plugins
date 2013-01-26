/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.gtfsloader;

import org.gephi.io.importer.spi.Importer;
import org.gephi.io.importer.spi.ImporterWizardUI;
import org.gephi.io.importer.spi.SpigotImporter;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ImporterWizardUI.class)
public class GTFSImporterWizardUI implements ImporterWizardUI {
    private Panel[] panels;
    
    @Override
    public String getDisplayName() {
        return "GTFS Importer";
    }

    @Override
    public String getCategory() {
        return "GTFS";
    }

    @Override
    public String getDescription() {
        return "Description1";
    }

    @Override
    public Panel[] getPanels() {
        if (panels == null) {
            panels = new Panel[2];
            panels[0] = new GTFSImporterFileSelectorPanel();
            panels[1] = new GTFSImporterOptionsPanel();
        }
        return panels;
    }

    @Override
    public void setup(Panel pnl) {
        
    }

    @Override
    public void unsetup(SpigotImporter si, Panel panel) {
        GTFSImporter importer = (GTFSImporter) si;
        
        if (panel instanceof GTFSImporterFileSelectorPanel) {
            GTFSImporterFileSelectorPanel fsPanel = (GTFSImporterFileSelectorPanel) panel;
            importer.setFile(fsPanel.getFile());
        }
    }

    @Override
    public boolean isUIForImporter(Importer importer) {
        return importer instanceof GTFSImporter;
    }
    
}
