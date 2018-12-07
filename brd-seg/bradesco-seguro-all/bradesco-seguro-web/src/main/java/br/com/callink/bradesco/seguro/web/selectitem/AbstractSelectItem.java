package br.com.callink.bradesco.seguro.web.selectitem;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.model.SelectItem;

/**
 * @param <T>
 * @author fabiooliveira
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractSelectItem<T> extends ArrayList {

    private static final long serialVersionUID = -1480101999790808053L;
    protected Boolean optionDefault = Boolean.TRUE;
    protected String title = "Selecione...";

    public AbstractSelectItem() {
        addSelectItemDefault();
    }

    public AbstractSelectItem(Boolean optionDefault) {
        this.optionDefault = optionDefault;

        if (optionDefault) {
            addSelectItemDefault();
        }
    }

    protected void addSelectItemDefault() {

        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                Class newClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                Constructor ct = newClass.getConstructor();
                add(new SelectItem((T) ct.newInstance(), title));
            } catch (Throwable e) {
            }
        }
    }

    public void setList(Collection<T> collection) {
        clear();

        if (optionDefault) {
            addSelectItemDefault();
        }

        if (collection != null) {
            for (T entity : collection) {
                add(getSelectItemByObject(entity));
            }
        }
    }

    public void setListAndOrder(Collection<T> collection) {
        order(collection);
        setList(collection);
    }

    protected abstract SelectItem getSelectItemByObject(T entity);

    protected void order(Collection<T> collection) {
    }
}