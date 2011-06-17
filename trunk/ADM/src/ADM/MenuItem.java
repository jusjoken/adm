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
import java.lang.reflect.*;
import sagex.UIContext;


public class MenuItem {
    public String Parent = "";
    public String Name = "";
    public String ButtonText = "";
    public String SubMenu = "";
    public String Action = "";
    public String ActionType = "";
    public String BGImageFile = "";
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
        
    }
            
    //use this contructor for Action Item type items - based on ActionType
//    public MenuItem(String bName, String bButtonText, String bActionType, String bAction, String bBGImageFile){
//        this(bName,bButtonText,null,bActionType,bAction,bBGImageFile);
//    }
//    
//    //use this contructor for SubMenu type items
//    public MenuItem(String bName, String bButtonText, String bSubMenu, String bBGImageFile){
//        this(bName,bButtonText,bSubMenu,null,null,bBGImageFile);
//    }
//    
    public static String GetMenuItemParent(int Item){
        return ParentList.get(Item);
    }

    public static String GetMenuItemName(int Item){
        return NameList.get(Item);
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

    public static String GetMenuItemBGImageFile(int Item){
        return BGImageFileList.get(Item);
    }

    public static int GetMenuItemCount(){
        return ButtonTextList.size();
    }
    
    
}
