package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.component.Mode;
import org.richfaces.component.UIDropDownMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.UIToolbar;

import br.com.callink.sso.authlib.exception.SessionException;
import br.com.callink.sso.authlib.ws.session.WsSsoSession;
import br.com.callink.sso.web.ws.WsMenuItem;
import br.com.callink.sso.web.ws.WsSistema;

@ManagedBean
@SessionScoped
public class MenuBB implements Serializable {

    private static final long serialVersionUID = 5567235429982902401L;
    private transient UIToolbar menuBar;
    private int dropDownMenuCount = 0;
    private int menuGroupCount = 0;
    private int menuItemCount = 0;
    private transient WsSsoSession wsSsoSession;
    private transient HttpSession session;
    private String userLogin = "";
    private static final Logger LOGGER = Logger.getLogger(MenuBB.class);

    public MenuBB() {
    }

    @PostConstruct
    public void init() {

        session = (HttpSession) getFacesContext().getExternalContext().getSession(false);
        wsSsoSession = (WsSsoSession) session.getAttribute("ssoSession");

        if (wsSsoSession != null && wsSsoSession.getUserInfo() != null) {
            Map<Integer, List<WsMenuItem>> menuMap = wsSsoSession.getUserInfo().getMenuMap();
            Set<WsSistema> systems = wsSsoSession.getUserInfo().getUserSystems();
            buildMenuBar(menuMap, systems);
            this.userLogin = wsSsoSession.getUserInfo().getUserLogin();
            session.setAttribute("userLogin", userLogin);
            session.setAttribute("userHost", wsSsoSession.getHostId());
        }
    }

    public void logout() {
        try {

            wsSsoSession.destroy();
            HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
            session.invalidate();

            response.sendRedirect(request.getContextPath());
            FacesContext.getCurrentInstance().responseComplete();

        } catch (SessionException | IOException e) {
            error(e);
        }
    }

    protected void buildMenuBar(Map<Integer, List<WsMenuItem>> menuItemMap, Set<WsSistema> systems) {

        if (menuItemMap != null) {

            menuBar = new UIToolbar();

            // 1st level menu items
            if (menuItemMap.containsKey(null)) {
                for (WsMenuItem m : menuItemMap.get(null)) {
                    UIDropDownMenu dropDown = addDropDownMenu(menuBar, m.getNomeMenuItem());
                    buildMenuBarRecursively(dropDown, m, menuItemMap);
                }
            }

            buildSystemMenu(systems);
        }
    }

    private String getMenuItemUrl(WsMenuItem menuItem, String protocol) {

        if (menuItem.getUri() == null) {
            return null;
        }
        String urlMenuItem = menuItem.getUri().getUri();
        if (urlMenuItem == null || "".equals(urlMenuItem)) {
            return null;
        }
        return protocol + urlMenuItem;
    }

    private UIDropDownMenu addDropDownMenu(UIToolbar parent, String name) {

        UIDropDownMenu dropDownMenu = (UIDropDownMenu) getApplication()
                .createComponent(getFacesContext(), UIDropDownMenu.COMPONENT_TYPE, "org.richfaces.DropDownMenuRenderer");

        dropDownMenu.setLabel(name);
        dropDownMenu.setId("dropDown" + dropDownMenuCount++);
        parent.getChildren().add(dropDownMenu);

        return dropDownMenu;
    }

    private void buildMenuBarRecursively(UIComponent parent,
            WsMenuItem parentMenuItem,
            Map<Integer, List<WsMenuItem>> menuItemMap) {

        if (menuItemMap.containsKey(parentMenuItem.getId())) {

            List<WsMenuItem> children = menuItemMap.get(parentMenuItem.getId());
            for (WsMenuItem m : children) {

                if (menuItemMap.containsKey(m.getId())) {

                    UIComponent menuGroup = addMenuGroup(parent,
                            m.getNomeMenuItem());
                    buildMenuBarRecursively(menuGroup, m, menuItemMap);
                } else {
                    String url = getMenuItemUrl(m, "");
                    MethodExpression met = getApplication()
                            .getExpressionFactory().createMethodExpression(
                            getFacesContext().getELContext(), url,
                            String.class, new Class[]{});
                    addMenuItem(parent, m.getNomeMenuItem(), met);
                }
            }
        }
    }

    private UIMenuGroup addMenuGroup(UIComponent parent, String name) {

        UIMenuGroup menuGroup = (UIMenuGroup) getApplication()
                .createComponent(getFacesContext(), UIMenuGroup.COMPONENT_TYPE, "org.richfaces.MenuGroupRenderer");

        menuGroup.setValue(name);
        menuGroup.setLabel(name);
        menuGroup.setId("menuGroup" + menuGroupCount++);
        parent.getChildren().add(menuGroup);
        return menuGroup;
    }

    private UIMenuItem addMenuItem(UIComponent parent, String name, String url) {

        UIMenuItem menuItem = (UIMenuItem) getApplication()
                .createComponent(getFacesContext(), UIMenuItem.COMPONENT_TYPE, "org.richfaces.MenuItemRenderer");

        if (url != null) {
            menuItem.setOnclick("document.location.href='" + url + "'");
        }

        menuItem.setLabel(name);
        menuItem.setValue(name);
        menuItem.setMode(Mode.client);
        menuItem.setId("menuItem" + menuItemCount++);
        parent.getChildren().add(menuItem);

        return menuItem;
    }

    private void buildSystemMenu(Set<WsSistema> systems) {

        UIDropDownMenu dropDown = addDropDownMenu(menuBar, "Sistema");

        for (WsSistema s : systems) {
            addMenuItem(dropDown, s.getNomeSistema(),
                    getSystemUrl(s, "http://"));
        }

        // logout link
        ExpressionFactory exFactory = getFacesContext().getApplication().getExpressionFactory();
        MethodExpression method = exFactory
                .createMethodExpression(getFacesContext().getELContext(), "#{menuBB.logout}", String.class, new Class[]{});

        addMenuItem(dropDown, "Sair", method);
    }

    private UIMenuItem addMenuItem(UIComponent parent, String name, MethodExpression action) {

        UIMenuItem menuItem = (UIMenuItem) getApplication().createComponent(
                getFacesContext(), UIMenuItem.COMPONENT_TYPE,
                "org.richfaces.MenuItemRenderer");

        if (action != null) {
            menuItem.setActionExpression(action);
        }

        menuItem.setMode(Mode.server);
        menuItem.setLabel(name);
        menuItem.setValue(name);
        menuItem.setId("menuItem" + menuItemCount++);
        parent.getChildren().add(menuItem);

        return menuItem;
    }

    private String getSystemUrl(WsSistema sistema, String protocol) {

        // Format:
        StringBuilder url = new StringBuilder();

        url.append(sistema.getServidor().getDominio());
        if (sistema.getServidor().getPorta() != 0) {
            url.append(':');
            url.append(sistema.getServidor().getPorta());
        }
        url.append('/');
        url.append(sistema.getContextoSistema());
        url.append('/');

        String urlStr = url.toString();

        urlStr = urlStr.replaceAll("//", "/");

        return protocol + urlStr;
    }

    public WsSsoSession getWsSsoSession() {
        return wsSsoSession;
    }

    public void setWsSsoSession(WsSsoSession wsSsoSession) {
        this.wsSsoSession = wsSsoSession;
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    private Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    public UIToolbar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(UIToolbar menuBar) {
        this.menuBar = menuBar;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    protected void error(Exception summary) {
        LOGGER.error(summary);
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary.getMessage(), null));
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
