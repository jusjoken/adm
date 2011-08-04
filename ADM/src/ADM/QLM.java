/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */
public class QLM {

    //special case so SageSubMenus do not get displayed
    public static String GetMenuItemSubMenu(String Name){
        try {
            return MenuNode.MenuNodeList().get(Name).getSubMenuExcludingSageMenus();
        } catch (Exception e) {
            System.out.println("ADM: mGet... ERROR: Value not available for '" + Name + "' Exception = '" + e + "'");
            return null;
        }
    }

    //TODO: need function to get menu item list EXCLUDING items with a Sage Submenu and no default action
    
}
