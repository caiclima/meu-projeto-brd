package br.com.callink.bradesco.seguro.web.faces.utils.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;


/**
 * Classe AbstractGenericConverter, contem a implmentação para projeto(s) web
 * terem um conversor generico bastando herdar esta classe e ser anotado com
 * 
 * @FacesConverter(value="nomeConversorGenerico")
 * 
 * @author bruno.camargo
 * 
 */

public abstract class AbstractGenericConverter implements Converter {

	private int index = -1;

	public final Object getAsObject(FacesContext ctx, UIComponent component, String value) {

		if (StringUtils.isEmpty(value)) {
			return "";
		}
		SelectItem selectedItem;
		try {
			selectedItem = this.getSelectedItemByIndex(component, Integer.parseInt(value));
		} catch (NumberFormatException e) {
			return null;
		}

		if (selectedItem != null) {
			return selectedItem.getValue();
		}

		return null;
	}

	public final String getAsString(FacesContext ctx, UIComponent component, Object value) {

		index++;
		return String.valueOf(index);

	}

	/**
	 * Obtem o SelecItem de acordo com a opção selecionada pelo usuário
	 */
	protected final SelectItem getSelectedItemByIndex(UIComponent component, int index) {

		List<SelectItem> items = this.getSelectItems(component);
		int size = items.size();

		if (index > -1 && size > index) {
			return items.get(index);
		}

		return null;
	}

	protected final List<SelectItem> getSelectItems(UIComponent component) {

		List<SelectItem> items = new ArrayList<SelectItem>();

		int childCount = component.getChildCount();
		if (childCount == 0) {
			return items;
		}

		List<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {
			if (child instanceof UISelectItem) {
				this.addSelectItem((UISelectItem) child, items);
			} else if (child instanceof UISelectItems) {
				this.addSelectItems((UISelectItems) child, items);
			}
		}

		return items;
	}

	protected final void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {

		boolean isRendered = uiItem.isRendered();
		if (!isRendered) {
			items.add(null);
			return;
		}

		Object value = uiItem.getValue();
		SelectItem item;

		if (value instanceof SelectItem) {
			item = (SelectItem) value;
		} else {
			Object itemValue = uiItem.getItemValue();
			String itemLabel = uiItem.getItemLabel();
			// JSF throws a null pointer exception for null values and labels,
			// which is a serious problem at design-time.
			item = new SelectItem(itemValue == null ? "" : itemValue, itemLabel == null ? "" : itemLabel, uiItem.getItemDescription(), uiItem.isItemDisabled());
		}

		items.add(item);
	}

	@SuppressWarnings("unchecked")
	protected final void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {

		boolean isRendered = uiItems.isRendered();
		if (!isRendered) {
			items.add(null);
			return;
		}

		Object value = uiItems.getValue();
		if (value instanceof SelectItem) {
			items.add((SelectItem) value);
		} else if (value instanceof Object[]) {
			Object[] array = (Object[]) value;
			for (int i = 0; i < array.length; i++) {
				// TODO test - this section is untested
				if (array[i] instanceof SelectItemGroup) {
					resolveAndAddItems((SelectItemGroup) array[i], items);
				} else {
					items.add((SelectItem) array[i]);
				}
			}
		} else if (value instanceof Collection) {
			SelectItem[] arrayItens = ((Collection<SelectItem>) value).toArray(new SelectItem[((Collection<SelectItem>) value).size()]);
			//Iterator<SelectItem> iter = ((CopyOnWriteArrayList<SelectItem>) value).iterator();
			//SelectItem item;
			
			if(arrayItens != null && arrayItens.length > 0){
				for (SelectItem selectItem : arrayItens) {
					items.add(selectItem);
				}
			}
			//while (iter.hasNext()) {
				//item = iter.next();
				// if (item instanceof SelectItemGroup) {
				// resolveAndAddItems((SelectItemGroup) item, items);
				// } else {
				//items.add(item);
				// }
			//}
		} else if (value instanceof Map) {
			for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
				Object label = entry.getKey();
				SelectItem item = new SelectItem(entry.getValue(), label == null ? (String) null : label.toString());
				// TODO test - this section is untested
				// if (item instanceof SelectItemGroup) {
				// resolveAndAddItems((SelectItemGroup) item, items);
				// } else {
				items.add(item);
				// }
			}
		}

	}

	protected final void resolveAndAddItems(SelectItemGroup group, List<SelectItem> items) {

		for (SelectItem item : group.getSelectItems()) {
			// if (item instanceof SelectItemGroup) {
			// resolveAndAddItems((SelectItemGroup) item, items);
			// } else {
			items.add(item);
			// }
		}

	}
}
