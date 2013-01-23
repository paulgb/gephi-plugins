
package org.paulbutler.topolayout;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutUI;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=LayoutBuilder.class)
public class TopologicalLayoutBuilder implements LayoutBuilder {
    private TopologicalLayoutUI ui = new TopologicalLayoutUI();
    
    @Override
    public String getName() {
        return "Topological Layout";
    }
    
    @Override
    public Layout buildLayout () {
        return new TopologicalLayout(this);
    }
    
    @Override
    public LayoutUI getUI() {
        return ui;
    }
    
    private static class TopologicalLayoutUI implements LayoutUI {
        @Override
        public String getDescription() {
            return "Description";
        }

        @Override
        public Icon getIcon() {
            return null;
        }

        @Override
        public JPanel getSimplePanel(Layout layout) {
            return null;
        }

        @Override
        public int getQualityRank() {
            return -1;
        }

        @Override
        public int getSpeedRank() {
            return -1;
        }
    }
}
