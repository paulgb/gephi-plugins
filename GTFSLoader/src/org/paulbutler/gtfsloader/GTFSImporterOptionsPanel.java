
package org.paulbutler.gtfsloader;

import java.awt.Component;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;

public class GTFSImporterOptionsPanel implements Panel {
    private GTFSImporterOptionsSwing component;
    private GTFSImportOptions options = new GTFSImportOptions();
    private ChangeListener changeListener;
    
    public GTFSImportOptions getOptions () {
        return options;
    }
    
    public void setOptions (GTFSImportOptions options) {
        this.options = options;
        changeListener.stateChanged(new ChangeEvent(this));
    }
    
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new GTFSImporterOptionsSwing(this);
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
        return !options.getTransitTypes().isEmpty();
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        this.changeListener = cl;
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        
    }
}
