/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.gtfsloader;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor.Panel;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;

/**
 *
 * @author paulbutler
 */
public class GTFSImporterFileSelectorPanel implements Panel {
    private Component component;
    private ChangeListener changelistener;
    private File file;

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new GTFSImporterFileSelectorSwing(this);
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
        return this.file != null;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        this.changelistener = cl;
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
    }

    public void setFile(File file) {
        this.file = file;
        changelistener.stateChanged(new ChangeEvent(this));
    }

}
