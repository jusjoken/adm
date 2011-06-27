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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import sage.bs;
import sagex.UIContext;


public class MenuItem {
    public static final String SagePropertyLocation = "ADM/menuitem/";
    public static final String TopMenu = "xTopMenu";
    private String Parent = "";
    private String Name = "";
    private String ButtonText = "";
    private String SubMenu = "";
    private String Action = "";
    private String ActionType = "";
    private String BGImageFile = "";
    private String BGImageFilePath = "";
    private Boolean IsDefault = false;
    private Boolean IsActive = true;
    private Boolean HasSubMenu = false;
    private Integer SortKey = 0;
    private Integer Level = 0;
    private static Integer SortKeyCounter = 0;
    public static Map<String,MenuItem> MenuItemList = new LinkedHashMap<String,MenuItem>();

    public MenuItem(String bName){
        //create a MenuItem with just default values
        Parent = TopMenu;
        Name = bName;
        ButtonText = "<Not defined>";
        SubMenu = null;
        ActionType = null;
        Action = null;
        BGImageFile = null;
        BGImageFilePath = null;
        IsDefault = false;
        IsActive = true;
        HasSubMenu = false;
        SortKey = 0;
        AddMenuItemtoList(this);
    }
    
    public MenuItem(String bParent, String bName, Integer bSortKey, String bButtonText, Boolean bHasSubMenu, String bSubMenu, String bActionType, String bAction, String bBGImageFile, Boolean bIsDefault, Boolean bIsActive){
        Parent = bParent;
        Name = bName;
        ButtonText = bButtonText;
        SubMenu = bSubMenu;
        HasSubMenu = bHasSubMenu;
        ActionType = bActionType;
        Action = bAction;
        SetBGImageFileandPath(bBGImageFile);
        IsDefault = bIsDefault;
        IsActive = bIsActive;
        SortKey = bSortKey;
        AddMenuItemtoList(this);
        
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public static String GetMenuItemAction(String Name){
        return MenuItemList.get(Name).getAction();
    }

    public static void SetMenuItemAction(String Name, String Setting){
        MenuItemList.get(Name).setAction(Setting);
        SaveMenuItemtoSage(Name, "Action", Setting);
    }

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

    public static String GetMenuItemActionType(String Name){
        return MenuItemList.get(Name).getActionType();
    }

    public static void SetMenuItemActionType(String Name, String Setting){
        MenuItemList.get(Name).setActionType(Setting);
        SaveMenuItemtoSage(Name, "ActionType", Setting);
    }

    public String getBGImageFile() {
        return BGImageFile;
    }

    public String getBGImageFilePath() {
        return BGImageFilePath;
    }

    public void setBGImageFile(String BGImageFile) {
        SetBGImageFileandPath(BGImageFile);
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
    
    public static String GetMenuItemBGImageFile(String Name){
        return MenuItemList.get(Name).getBGImageFile();
    }

    public static String GetMenuItemBGImageFilePath(String Name){
        return MenuItemList.get(Name).getBGImageFilePath();
    }

    public static void SetMenuItemBGImageFile(String Name, String Setting){
        MenuItemList.get(Name).setBGImageFile(Setting);
        SaveMenuItemtoSage(Name, "BGImageFile", Setting);
    }

    public String getButtonText() {
        return ButtonText;
    }

    public void setButtonText(String ButtonText) {
        this.ButtonText = ButtonText;
    }

    public static String GetMenuItemButtonText(String Name){
        if (Name.equals(TopMenu)){
            return "Top Level";
        }else{
            return MenuItemList.get(Name).getButtonText();
        }
    }

    public static void SetMenuItemButtonText(String Name, String Setting){
        MenuItemList.get(Name).setButtonText(Setting);
        SaveMenuItemtoSage(Name, "ButtonText", Setting);
    }

    public Boolean getHasSubMenu() {
        return HasSubMenu;
    }

    public void setHasSubMenu(Boolean HasSubMenu) {
        this.HasSubMenu = HasSubMenu;
    }

    public void setHasSubMenu(String HasSubMenu) {
        if ("true".equals(HasSubMenu)){
            this.HasSubMenu = true;
        }else{
            this.HasSubMenu = false;
        }
    }

    public static Boolean GetMenuItemHasSubMenu(String Name){
        return MenuItemList.get(Name).getHasSubMenu();
    }

    public static void SetMenuItemHasSubMenu(String Name, Boolean Setting){
        MenuItemList.get(Name).setHasSubMenu(Setting);
        SaveMenuItemtoSage(Name, "HasSubMenu", Setting.toString());
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    public void setIsActive(String IsActive) {
        if ("true".equals(IsActive)){
            this.IsActive = true;
        }else{
            this.IsActive = false;
        }
    }

    public static Boolean GetMenuItemIsActive(String Name){
        return MenuItemList.get(Name).IsActive;
    }

    public static void SetMenuItemIsActive(String Name, Boolean Setting){
        MenuItemList.get(Name).setIsActive(Setting);
        SaveMenuItemtoSage(Name, "IsActive", Setting.toString());
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

    public static Boolean GetMenuItemIsDefault(String Name){
        return MenuItemList.get(Name).getIsDefault();
    }

    public static void SetMenuItemIsDefault(String Name, Boolean Setting){
        MenuItemList.get(Name).setIsDefault(Setting);
        SaveMenuItemtoSage(Name, "IsDefault", Setting.toString());
    }

    public void setLevel(Integer Level){
        this.Level = Level;
    }
    
    public Integer getLevel() {
        return Level;
    }

    public static Integer GetMenuItemLevel(String Name){
        return MenuItemList.get(Name).getLevel();
    }
    
    public static void SetMenuItemLevel(String Name, Integer Setting){
        MenuItemList.get(Name).setLevel(Setting);
    }

    private static Integer GetMenuItemLevelInternal(String Name){
        if (MenuItemList.get(Name).Parent.equals(TopMenu)){
            System.out.println("ADM: GetMenuItemLevel: level - 1 returned for '" + Name + "'");
            return 1;
        }else{
            //find a MenuItem whose Name equals this Parent
            Collection<String> TempList = GetMenuItemNameList();
            for (String TempItem : TempList){
                if (MenuItemList.get(TempItem).Name.equals(MenuItemList.get(Name).Parent)){
                    if (MenuItemList.get(TempItem).Parent.equals(TopMenu)){
                        System.out.println("ADM: GetMenuItemLevel: level - 2 returned for '" + Name + "'");
                        return 2;
                    }else{
                        System.out.println("ADM: GetMenuItemLevel: level - 3 returned for '" + Name + "'");
                        return 3;
                    }
                }
            }
        }
        System.out.println("ADM: GetMenuItemLevel: failed to find level - 0 returned for '" + Name + "'");
        return 0;
    }

    //set the level field for all MenuItems
    public static void SetMenuItemLevels(){
        Collection<String> AllMenus = GetMenuItemSortedList(Boolean.TRUE);
        for (String Item : AllMenus){
            MenuItemList.get(Item).setLevel(GetMenuItemLevelInternal(Item));
        }
        System.out.println("ADM: SetMenuItemLevels: complete");
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

    public static void SetMenuItemName(String Name){
        SaveMenuItemtoSage(Name, "Name", Name);
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String Parent) {
        this.Parent = Parent;
    }

    public static String GetMenuItemParent(String Name){
        return MenuItemList.get(Name).getParent();
    }

    public static void SetMenuItemParent(String Name, String Setting){
        String OldParent = MenuItemList.get(Name).getParent();
        SaveMenuItemtoSage(Name, "Parent", Setting);
        MenuItemList.get(Name).setParent(Setting);
        
        if (OldParent.equals(Setting) || OldParent==null){
            System.out.println("ADM: SetMenuItemParent: Parent saved for '" + Name + "' to = '" + Setting + "'");
        }else{
            //check the new parent and set it's SubMenu properly
            MenuItemList.get(Setting).setSubMenu(null);
            MenuItemList.get(Setting).setHasSubMenu(Boolean.TRUE);

            //make sure the old and new SubMenus have a single default item
            ValidateSubMenuDefault(OldParent);
            ValidateSubMenuDefault(Setting);
            System.out.println("ADM: SetMenuItemParent: Parent changed for '" + Name + "' to = '" + Setting + "'");
        }
    }

    public Integer getSortKey() {
        return SortKey;
    }

    public void setSortKey(String SortKey) {
        Integer tSortKey = 0;
        try {
            tSortKey = Integer.valueOf(SortKey);
        } catch (NumberFormatException ex) {
            System.out.println("ADM: setSortKey: error converting '" + SortKey + "' " + util.class.getName() + ex);
            tSortKey = SortKeyCounter++;
        }
        //check if this SortKey is in use - if so then insert it after
        InsertSortKey(tSortKey);
        this.SortKey = tSortKey;
    }

    public static Integer GetMenuItemSortKey(String Name){
        return MenuItemList.get(Name).getSortKey();
    }

    public static void SetMenuItemSortKey(String Name, Integer Setting){
        SaveMenuItemtoSage(Name, "SortKey", Setting.toString());
        MenuItemList.get(Name).setSortKey(Setting.toString());
    }

    //Prepares for an insert of a MenuItem at a specific SortKey location by making that SortKey available
    private static void InsertSortKey(Integer bSortKey){
        SortedMap<Integer,String> SortedKeyList = new TreeMap<Integer,String>();
        
        //build the sorted list by SortKey
        Iterator<Entry<String,MenuItem>> itr = MenuItemList.entrySet().iterator(); 
        while (itr.hasNext()) {
            Entry<String,MenuItem> entry = itr.next();
            SortedKeyList.put(entry.getValue().SortKey,entry.getValue().Name);
        }
        System.out.println("ADM: InsertSortKey MenuItems ='" + MenuItemList.size() + "' SortedItems = '" + SortedKeyList.size() + "'");

        //if the sortKey is not in use then we are done
        if (!SortedKeyList.containsKey(bSortKey)){
            System.out.println("ADM: InsertSortKey for '" + bSortKey + "' : not found");
            return;
        }

        //increase the SortKey from bSortKey to the end (or a missing key)
        for(int i=bSortKey; i<SortedKeyList.lastKey()+1; i++){
            if (SortedKeyList.containsKey(i)){
                Integer NextSortKey = i + 1;
                MenuItemList.get(SortedKeyList.get(i)).SortKey = NextSortKey;
                SaveMenuItemtoSage(SortedKeyList.get(i), "SortKey", NextSortKey.toString());
                System.out.println("ADM: InsertSortKey changed '" + SortedKeyList.get(i) + "' from '" + i + "' to '" + (i+1) + "'");
            }else{
                //the SortKey was not found so we are done
                break;
            }
        }
        System.out.println("ADM: InsertSortKey for '" + bSortKey + "' : insertion point created");

    }
    
    public static void SwapSortKey(String Name1, String Name2){
        if (Name1==null||Name2==null){
            System.out.println("ADM: SwapSortKey: null values passed: Name1 = '" + Name1 + "' Name2 = '" + Name2 + "'");
        }else{
            //validate that the Parent is the same for the 2 MenuItems
            if (MenuItemList.get(Name1).Parent.equals(MenuItemList.get(Name2).Parent)){
                Integer SortKey1 = MenuItemList.get(Name1).getSortKey();
                Integer SortKey2 = MenuItemList.get(Name2).getSortKey();
                MenuItemList.get(Name2).setSortKey(SortKey1.toString());
                SaveMenuItemtoSage(Name1, "SortKey", SortKey1.toString());
                MenuItemList.get(Name1).setSortKey(SortKey2.toString());
                SaveMenuItemtoSage(Name2, "SortKey", SortKey2.toString());
                System.out.println("ADM: SwapSortKey BEFORE '" + Name1 + "' = " + SortKey1 + "' for '" + Name2 + "' = " + SortKey2 + "' - AFTER '" + Name1 + "' = " + MenuItemList.get(Name1).SortKey + "' for '" + Name2 + "' = " + MenuItemList.get(Name2).SortKey);
            }else{
                System.out.println("ADM: SwapSortKey: Parent missmatch. Parents: '" + Name1 + "' = " + MenuItemList.get(Name1).Parent + "' and '" + Name2 + "' = " + MenuItemList.get(Name2).Parent + "'");
            }
        }
    }
    
    //the SubMenu field is only filled in if using a built in Sage SubMenu
    // otherwise, the Name of the MenuItem is returned if the MenuItem has a SubMenu
    public String getSubMenu() {
        if (SubMenu==null){
            if (HasSubMenu){
                return Name;
            }else{
                return SubMenu;
            }
        }else{
            return SubMenu;
        }
    }

    public void setSubMenu(String SubMenu) {
        this.SubMenu = SubMenu;
    }
    
    public static String GetMenuItemSubMenu(String Name){
        return MenuItemList.get(Name).getSubMenu();
    }

    public static void SetMenuItemSubMenu(String Name, String Setting){
        MenuItemList.get(Name).setSubMenu(Setting);
        SaveMenuItemtoSage(Name, "SubMenu", Setting);
    }

    public static void ValidateSubMenuDefault(String bParent){
        Collection<String> SubMenuItems = GetMenuItemNameList(bParent,Boolean.TRUE);
        Boolean FoundDefault = false;
        String FirstItem = null;
        
        if (SubMenuItems.size()>0){
            for (String SubMenuItem : SubMenuItems){
                if (FirstItem==null){
                    FirstItem = MenuItemList.get(SubMenuItem).getName();
                }
                if (MenuItemList.get(SubMenuItem).getIsDefault()){
                    if (FoundDefault){
                        MenuItemList.get(SubMenuItem).setIsDefault(Boolean.FALSE);
                    }else{
                        FoundDefault = Boolean.TRUE;
                    }
                }
            }
            if (!FoundDefault){
                //no default found so set the first item as the default
                MenuItemList.get(FirstItem).setIsDefault(Boolean.TRUE);
            }
        }else{
            //no subMenu items so make sure this parent's SubMenu settings are correct
            MenuItemList.get(bParent).setSubMenu(null);
            MenuItemList.get(bParent).setHasSubMenu(Boolean.FALSE);
        }
    }
    
    final void AddMenuItemtoList(MenuItem NewMenuItem){
        MenuItemList.put(NewMenuItem.Name, NewMenuItem);
    }
    
    //returns the full list of ALL menu items regardless of parent
    public static Collection<String> GetMenuItemNameList(){
        return GetMenuItemNameList(null);
    }
    
    //returns only menu items for a specific parent that are active
    public static Collection<String> GetMenuItemNameList(String Parent){
        return GetMenuItemNameList(Parent, Boolean.FALSE);
    }
    
    //returns menu items for a specific parent
    public static Collection<String> GetMenuItemNameList(String Parent, Boolean IncludeInactive){
        SortedMap<Integer,String> bParentList = new TreeMap<Integer,String>();
        Collection<String> bSortedNames = new LinkedHashSet<String>();
        
        Iterator<Entry<String,MenuItem>> itr = MenuItemList.entrySet().iterator(); 
        while (itr.hasNext()) {
            Entry<String,MenuItem> entry = itr.next();
            //check for the correct parent
            if (entry.getValue().Parent == null ? Parent == null : entry.getValue().Parent.equals(Parent)){
                //only select Active MenuItems
                if (entry.getValue().IsActive==true || IncludeInactive==true){
                    bParentList.put(entry.getValue().SortKey,entry.getValue().Name);
                }
            }else if (Parent == null){
                bParentList.put(entry.getValue().SortKey,entry.getValue().Name);
            }
        }         
        bSortedNames = bParentList.values();
        System.out.println("ADM: GetMenuItemNameList for '" + Parent + "' : IncludeInactive = '" + IncludeInactive.toString() + "' " + bSortedNames);

        return bSortedNames;
    }
    
    //returns only menu items for a specific parent that are active
    public static Collection<String> GetMenuItemSortedList(Boolean Grouped){
        //first get all Top Level Menu Items
        Collection<String> TopMenus = GetMenuItemNameList(TopMenu,Boolean.TRUE);
        
        Collection<String> FinalList = new LinkedHashSet<String>();
        if (Grouped){
            FinalList.addAll(TopMenus);
        }
        
        //find all SubMenus and get those menus. 
        //If Grouped then append to the full list otherwise insert them in order
        for (String MenuName : TopMenus){
            if (!Grouped){
                FinalList.add(MenuName);
            }
            if (MenuItemList.get(MenuName).HasSubMenu) {
                Collection<String> SubMenusLevel2 = GetMenuItemNameList(MenuItemList.get(MenuName).getSubMenu(),Boolean.TRUE);
                if (Grouped){
                    FinalList.addAll(SubMenusLevel2);
                }
                for (String MenuNameLevel2 : SubMenusLevel2){
                    if (!Grouped){
                        FinalList.add(MenuNameLevel2);
                    }
                    if (MenuItemList.get(MenuNameLevel2).HasSubMenu) {
                        Collection<String> SubMenusLevel3 = GetMenuItemNameList(MenuItemList.get(MenuNameLevel2).getSubMenu(),Boolean.TRUE);
                        FinalList.addAll(SubMenusLevel3);
                    }
                }
           }
        }
        System.out.println("ADM: GetMenuItemSortedList: Grouped = '" + Grouped.toString() + "' :" + FinalList);
        return FinalList;
    }

    public static Collection<String> GetMenuItemParentList(){
        Collection<String> FullSortedList = GetMenuItemNameList(null,Boolean.TRUE);
        Collection<String> ValidParentList = new LinkedHashSet<String>();
        //Add Top Level as a valid parent
        ValidParentList.add(TopMenu);
        for (String TempName : FullSortedList){
            if (MenuItemList.get(TempName).Level<3){
                ValidParentList.add(TempName);
            }
        }
        System.out.println("ADM: GetMenuItemParentList: '" + ValidParentList + "'");
        return ValidParentList;
    }
    
    //get a '/' delimitted path for the menu item
    public static String GetMenuItemFullPath(String Name){
        return GetMenuItemButtonTextFormatted(Name,null);
    }

    public static String GetMenuItemButtonTextFormatted(String Name, String PrefixPadding){
        String FullName = "";
        if (MenuItemList.get(Name).Parent.equals(TopMenu)){
            FullName = MenuItemList.get(Name).ButtonText;
            System.out.println("ADM: GetMenuItemButtonTextFormatted: level - 1 for '" + Name + "' Path = '" + FullName + "'");
            return FullName;
        }else{
            //find a MenuItem whose SubMenu equals this Parent
            Collection<String> TempList = GetMenuItemNameList();
            for (String TempItem : TempList){
                if (MenuItemList.get(Name).Parent.equals(MenuItemList.get(TempItem).Name)){
                    if (MenuItemList.get(TempItem).Parent.equals(TopMenu)){
                        FullName = MenuItemList.get(TempItem).ButtonText + " / " + MenuItemList.get(Name).ButtonText ;
                        if (PrefixPadding==null){
                            System.out.println("ADM: GetMenuItemButtonTextFormatted: level - 2 for '" + Name + "' Path = '" + FullName + "'");
                            return FullName;
                        }else{
                            FullName = PrefixPadding + MenuItemList.get(Name).ButtonText;
                            System.out.println("ADM: GetMenuItemButtonTextFormatted: level - 2 for '" + Name + "' Path = '" + FullName + "'");
                            return FullName;
                        }
                    }else{
                        for (String TempItem2 : TempList){
                            if (MenuItemList.get(TempItem).Parent.equals(MenuItemList.get(TempItem2).Name)){
                                FullName = MenuItemList.get(TempItem2).ButtonText + " / " + MenuItemList.get(TempItem).ButtonText + " / " + MenuItemList.get(Name).ButtonText ;
                                if (PrefixPadding==null){
                                    System.out.println("ADM: GetMenuItemButtonTextFormatted: level - 3 for '" + Name + "' Path = '" + FullName + "'");
                                    return FullName;
                                }else{
                                    FullName = PrefixPadding + PrefixPadding + MenuItemList.get(Name).ButtonText;
                                    System.out.println("ADM: GetMenuItemButtonTextFormatted: level - 3 for '" + Name + "' Path = '" + FullName + "'");
                                    return FullName;
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        System.out.println("ADM: GetMenuItemButtonTextFormatted: failed to find level - 0 returned for '" + Name + "'");
        return MenuItemList.get(Name).ButtonText;
    }

    private static void SaveMenuItemtoSage(String Name, String PropType, String Setting){
        String PropLocation = SagePropertyLocation + Name;
        sagex.api.Configuration.SetProperty(PropLocation + "/" + PropType, Setting);
    }
    
    public static int GetMenuItemCount(){
        return GetMenuItemCount(null);
    }
    
    //Get the count of MenuItems for a parent that are active
    public static int GetMenuItemCount(String Parent){
        Collection<String> bSortedNames = GetMenuItemNameList(Parent);
        System.out.println("ADM: GetMenuItemCount for '" + Parent + "' :" + bSortedNames.size());
        return bSortedNames.size();
    }
    
}
