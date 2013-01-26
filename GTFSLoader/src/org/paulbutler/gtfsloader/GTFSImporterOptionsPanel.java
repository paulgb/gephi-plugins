
package org.paulbutler.gtfsloader;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;

public class GTFSImporterOptionsPanel implements Panel {
    private GTFSImporterOptionsSwing component;
    
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new GTFSImporterOptionsSwing();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void readSettings(Object data) {
        
    }

    @Override
    public void storeSettings(Object data) {
        
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        
    }
}
