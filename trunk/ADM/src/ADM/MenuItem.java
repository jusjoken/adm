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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;
import sagex.UIContext;


public class MenuItem {
    public String Parent = "";
    public String Name = "";
    public String ButtonText = "";
    public String SubMenu = "";
    public String Action = "";
    public String ActionType = "";
    public String BGImageFile = "";
    public static Map<String,MenuItem> MenuItemList = new LinkedHashMap<String,MenuItem>();
    public static ArrayList<String> ParentList = new ArrayList<String>();
    public static ArrayList<String> NameList = new ArrayList<String>();
    public static ArrayList<String> ButtonTextList = new ArrayList<String>();
    public static ArrayList<String> SubMenuList = new ArrayList<String>();
    public static ArrayList<String> ActionList = new ArrayList<String>();
    public static ArrayList<String> ActionTypeList = new ArrayList<String>();
    public static ArrayList<String> BGImageFileList = new ArrayList<String>();

    
    public MenuItem(String bParent, String bName, String bButtonText, String bSubMenu, String bActionType, String bAction, String bBGImageFile){
        Parent = bParent;
        Name = bName;
        ButtonText = bButtonText;
        SubMenu = bSubMenu;
        ActionType = bActionType;
        Action = bAction;
        //see if using a GlobalVariable from a Theme or a path to an image file
        if (bBGImageFile.contains("\\")){
            //a path to the image file is being used
            BGImageFile = bBGImageFile;
        }else{
            //expect a Global Variable from the theme
            BGImageFile = sagex.api.WidgetAPI.EvaluateExpression(new UIContext(sagex.api.Global.GetUIContextName()), bBGImageFile).toString();
        }
        ParentList.add(Parent);
        NameList.add(Name);
        ButtonTextList.add(ButtonText);
        SubMenuList.add(SubMenu);
        ActionTypeList.add(ActionType);
        ActionList.add(Action);
        BGImageFileList.add(BGImageFile);
        MenuItemList.put(this.Name, this);
        
    }
    
    //returns the full list of ALL menu items regardless of parent
    public static Set<String> GetMenuItemNameList(){
        return MenuItemList.keySet();
    }
    
    //returns only menu items for a specific parent
    public static Set<String> GetMenuItemNameList(String Parent){
        Set<String> bParentList = new LinkedHashSet<String>();
        
        Iterator<Entry<String,MenuItem>> itr = MenuItemList.entrySet().iterator(); 
        while (itr.hasNext()) {
            Entry<String,MenuItem> entry = itr.next();
            if (entry.getValue().Parent == null ? Parent == null : entry.getValue().Parent.equals(Parent)){
                bParentList.add(entry.getValue().Name);
            }
        }         
        
        return bParentList;
    }
    
    public static String GetMenuItemParent(String Name){
        return MenuItemList.get(Name).Parent;
    }

    public static String GetMenuItemButtonText(String Name){
        return MenuItemList.get(Name).ButtonText;
    }

    public static String GetMenuItemSubMenu(String Name){
        return MenuItemList.get(Name).SubMenu;
    }

    public static String GetMenuItemAction(String Name){
        return MenuItemList.get(Name).Action;
    }

    public static String GetMenuItemActionType(String Name){
        return MenuItemList.get(Name).ActionType;
    }

    public static String GetMenuItemBGImageFile(String Name){
        return MenuItemList.get(Name).BGImageFile;
    }

    public static int GetMenuItemCount(){
        return MenuItemList.size();
    }
    
    
}
