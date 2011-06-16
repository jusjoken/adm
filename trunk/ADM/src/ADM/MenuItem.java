/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author bir0012
 */

import java.util.ArrayList;

public class MenuItem {
    public String Name = "";
    public String ButtonText = "";
    public String SubMenu = "";
    public String Action = "";
    public String ActionType = "";
    public static ArrayList<String> ButtonTextList = new ArrayList<String>();
    public static ArrayList<String> SubMenuList = new ArrayList<String>();
    public static ArrayList<String> ActionList = new ArrayList<String>();
    public static ArrayList<String> ActionTypeList = new ArrayList<String>();

    
    public MenuItem(String bName, String bButtonText, String bSubMenu, String bActionType, String bAction){
        Name = bName;
        ButtonText = bButtonText;
        SubMenu = bSubMenu;
        ActionType = bActionType;
        Action = bAction;
        ButtonTextList.add(ButtonText);
        SubMenuList.add(SubMenu);
        ActionTypeList.add(ActionType);
        ActionList.add(Action);
        
    }
            
    //use this contructor for Action Item type items - based on ActionType
    public MenuItem(String bName, String bButtonText, String bActionType, String bAction){
        this(bName,bButtonText,null,bActionType,bAction);
    }
    
    //use this contructor for SubMenu type items
    public MenuItem(String bName, String bButtonText, String bSubMenu){
        this(bName,bButtonText,bSubMenu,null,null);
    }
    
    public static String GetMenuItemButtonText(int Item){
        return ButtonTextList.get(Item);
    }

    public static ArrayList<String> GetMenuItemButtonTextList(){
        return ButtonTextList;
    }
    
    public static String GetMenuItemSubMenu(int Item){
        return SubMenuList.get(Item);
    }

    public static String GetMenuItemAction(int Item){
        return ActionList.get(Item);
    }

    public static String GetMenuItemActionType(int Item){
        return ActionTypeList.get(Item);
    }

    public static int GetMenuItemCount(){
        return ButtonTextList.size();
    }
    
    
}
