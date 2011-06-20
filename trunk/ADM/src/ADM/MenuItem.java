/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author bir0012
 */

//import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;
import sagex.UIContext;


public class MenuItem {
    public static final String SagePropertyLocation = "ADM/menuitem/";
    private String Parent = "";
    private String Name = "";
    private String ButtonText = "";
    private String SubMenu = "";
    private String Action = "";
    private String ActionType = "";
    private String BGImageFile = "";
    private String BGImageFilePath = "";
    private Boolean IsDefault = false;
    public static Map<String,MenuItem> MenuItemList = new LinkedHashMap<String,MenuItem>();

    public MenuItem(String bName){
        //create a MenuItem with just default values
        Parent = "xTopMenu";
        Name = bName;
        ButtonText = "<Not defined>";
        SubMenu = null;
        ActionType = null;
        Action = null;
        SetBGImageFileandPath(null);
        IsDefault = false;
        AddMenuItemtoList(this);
        
    }
    
    public MenuItem(String bParent, String bName, String bButtonText, String bSubMenu, String bActionType, String bAction, String bBGImageFile, Boolean bIsDefault){
        Parent = bParent;
        Name = bName;
        ButtonText = bButtonText;
        SubMenu = bSubMenu;
        ActionType = bActionType;
        Action = bAction;
        SetBGImageFileandPath(bBGImageFile);
        IsDefault = bIsDefault;
        AddMenuItemtoList(this);
        //MenuItemList.put(this.Name, this);
        
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

    public String getBGImageFile() {
        return BGImageFile;
    }

    public void setBGImageFile(String BGImageFile) {
        SetBGImageFileandPath(BGImageFile);
    }

//    public String getBGImageFilePath() {
//        return BGImageFilePath;
//    }
//
//    public void setBGImageFilePath(String BGImageFilePath) {
//        this.BGImageFilePath = BGImageFilePath;
//    }
//
    public String getButtonText() {
        return ButtonText;
    }

    public void setButtonText(String ButtonText) {
        this.ButtonText = ButtonText;
    }

    public Boolean getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(Boolean IsDefault) {
        this.IsDefault = IsDefault;
    }

    public void setIsDefault(String IsDefault) {
        if ("true".equals(IsDefault)){
            this.IsDefault = true;
        }else{
            this.IsDefault = false;
        }
    }

    public static Map<String, MenuItem> getMenuItemList() {
        return MenuItemList;
    }

    public static void setMenuItemList(Map<String, MenuItem> MenuItemList) {
        MenuItem.MenuItemList = MenuItemList;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String Parent) {
        this.Parent = Parent;
    }

    public String getSubMenu() {
        return SubMenu;
    }

    public void setSubMenu(String SubMenu) {
        this.SubMenu = SubMenu;
    }
    
    final void AddMenuItemtoList(MenuItem NewMenuItem){
        MenuItemList.put(NewMenuItem.Name, NewMenuItem);
    }
    
    private void SetBGImageFileandPath(String bBGImageFile){
        //see if using a GlobalVariable from a Theme or a path to an image file
        if (bBGImageFile==null){
            System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - null found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = bBGImageFile;
        }else if (bBGImageFile.contains("\\")){
            //a path to the image file is being used
            System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - path found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = bBGImageFile;
        }else{
            //expect a Global Variable from the theme
            System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - variable found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = sagex.api.WidgetAPI.EvaluateExpression(new UIContext(sagex.api.Global.GetUIContextName()), bBGImageFile).toString();
        }
        
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
        System.out.println("ADM: GetMenuItemNameList for '" + Parent + "' :" + bParentList);
        
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

    public static String GetMenuItemBGImageFilePath(String Name){
        return MenuItemList.get(Name).BGImageFilePath;
    }

    public static String GetMenuItemBGImageFile(String Name){
        return MenuItemList.get(Name).BGImageFile;
    }

    public static Boolean GetMenuItemIsDefault(String Name){
        return MenuItemList.get(Name).IsDefault;
    }

    public static int GetMenuItemCount(){
        return MenuItemList.size();
    }
    
//    //saves all MenuItems to Sage properties
//    public static void SaveMenuItemsToSage(){
//        String PropLocation = "";
//        
//        Iterator<Entry<String,MenuItem>> itr = MenuItemList.entrySet().iterator(); 
//        while (itr.hasNext()) {
//            Entry<String,MenuItem> entry = itr.next();
//            PropLocation = SagePropertyLocation + entry.getValue().Name;
//            sagex.api.Configuration.SetProperty(PropLocation + "/Parent", entry.getValue().Action);
//            sagex.api.Configuration.SetProperty(PropLocation + "/ActionType", entry.getValue().ActionType);
//            sagex.api.Configuration.SetProperty(PropLocation + "/BGImageFile", entry.getValue().BGImageFile);
//            sagex.api.Configuration.SetProperty(PropLocation + "/ButtonText", entry.getValue().ButtonText);
//            sagex.api.Configuration.SetProperty(PropLocation + "/Name", entry.getValue().Name);
//            sagex.api.Configuration.SetProperty(PropLocation + "/Parent", entry.getValue().Parent);
//            sagex.api.Configuration.SetProperty(PropLocation + "/SubMenu", entry.getValue().SubMenu);
//            sagex.api.Configuration.SetProperty(PropLocation + "/IsDefault", entry.getValue().IsDefault.toString());
//        }         
//        System.out.println("ADM: SaveMenuItemsToSage: saved " + MenuItemList.size() + " MenuItems");
//        
//        return;
//    }
    
}
