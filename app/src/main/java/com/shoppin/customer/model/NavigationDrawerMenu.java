package com.shoppin.customer.model;

/**
 * Model class for navigation left side drawer
 *
 * @author chitrang
 */
public class NavigationDrawerMenu {
    public String menuName;
    public int menuTagId;
    public int menuIcon;

    public NavigationDrawerMenu(String menuName, int menuTagId, int menuIcon) {
        this.menuName = menuName;
        this.menuTagId = menuTagId;
        this.menuIcon = menuIcon;
    }
}
