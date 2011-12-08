package info.netinho.util.ui.web.input;

import java.util.ArrayList;
import java.util.Collection;

public class SelectData {

    private String identifier;
    private Collection<SelectDataOption> option = new ArrayList();

    public SelectData(String identifier) {
        this.identifier = identifier;
    }

    public boolean add(SelectDataOption arg0) {
        return this.option.add(arg0);
    }
}