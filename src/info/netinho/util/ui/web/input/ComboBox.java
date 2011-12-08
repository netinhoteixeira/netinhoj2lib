package info.netinho.util.ui.web.input;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import java.util.ArrayList;
import java.util.List;

public class ComboBox {

    public static SimpleComboBox<String> createComboBox(String name, String value, String label, Boolean isRequired, String[] options) {
        SimpleComboBox box = new SimpleComboBox();
        box.setName(name);
        box.setFieldLabel(label);
        box.setEditable(false);
        box.setForceSelection(true);
        box.setTypeAhead(true);
        //box.setTriggerAction(ComboBox.TriggerAction.ALL);
        box.setFireChangeEventOnSetValue(true);
        box.setAllowBlank(!isRequired.booleanValue());
        for (String option : options) {
            box.add(option);
        }
        if (value != null) {
            List selected = new ArrayList();
            /*
            for (SimpleComboValue current : box.getStore().getModels()) {
            if (value.equals(current.getValue())) {
            selected.add(current);
            }
            }*/
            box.setSelection(selected);
        }
        return box;
    }
}