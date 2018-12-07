package br.com.callink.bradesco.seguro.web.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.web.util.StringUtil;

@FacesConverter(value = "PlanoConverter")
public class PlanoConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext ctx, UIComponent component, String value) {

		if (StringUtil.isEmpty(value)) {
			return "";
		}
		SelectItem selectedItem;
	
		selectedItem = this.getSelectedItemByIndex(component, value);
	

		if (selectedItem != null) {
			return selectedItem.getValue();
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null && value instanceof Plano) {
			Plano plano = (Plano) value;
			return plano.getId() != null ? plano.getNome() + " - " + plano.getTipoPlano().getNomeTipoPlano() : null;
		} else if (value instanceof Long) {
			return value != null ? value.toString() : null;
		}

		return null;
	}

	/**
	 * Obtem o SelecItem de acordo com a opção selecionada pelo usuário
	 */
	protected final SelectItem getSelectedItemByIndex(UIComponent component, String value) {

		List<SelectItem> items = this.getSelectItems(component);
		
		for (SelectItem item : items) {
			if (item.getLabel().equals(value)) {
				if (item.getLabel().equals(items.get(0))) {
					return null;
				} else {
					return item;
				}
			}
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
			Iterator<SelectItem> iter = ((Collection<SelectItem>) value).iterator();
			SelectItem item;
			while (iter.hasNext()) {
				item = iter.next();
				items.add(item);
			}
		} else if (value instanceof Map) {
			for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
				Object label = entry.getKey();
				SelectItem item = new SelectItem(entry.getValue(), label == null ? (String) null : label.toString());
				// TODO test - this section is untested
				items.add(item);
			}
		}

	}

	protected final void resolveAndAddItems(SelectItemGroup group, List<SelectItem> items) {

		for (SelectItem item : group.getSelectItems()) {
			items.add(item);
		}

	}
}
