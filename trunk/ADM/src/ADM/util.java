/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */

import sagex.UIContext;
import sagex.SageAPI;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.io.*;
import java.lang.Float;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class util {

    public static String Version = "0.36";
    private static final UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
    private static final String PropertyComment = "---ADM MenuItem Properties - Do Not Manually Edit---";
    public static final String SageADMBasePropertyLocation = "ADM/";
    public static final String SagePropertyLocation = "ADM/menuitem/";
    public static final String SageFocusPropertyLocation = "ADM/focus/";
    public static final String SageCurrentMenuItemPropertyLocation = "ADM/currmenuitem/";
    public static final String SageTVRecordingViewsTitlePropertyLocation = "sagetv_recordings/view_title/";
    public static final String AdvancedModePropertyLocation = "ADM/settings/advanced_mode";
    public static final String TopMenu = "xTopMenu";
    private static final String PropertyBackupFile = "ADMbackup.properties";
    private static final String ADMLocation = sagex.api.Utility.GetWorkingDirectory() + File.separator + "userdata" + File.separator + "ADM";
    private static final String ADMDefaultsLocation = sagex.api.Utility.GetWorkingDirectory() + File.separator + "STVs" + File.separator + "ADM" + File.separator + "defaults";
    private static final String StandardActionListFile = "ADMStandardActions.properties";
    private static final String SageBGVariablesListFile = "ADMSageBGVariables.properties";
    private static final String SageSubMenusLevel1ListFile = "ADMSageSubMenus1.properties";
    private static final String SageSubMenusLevel2ListFile = "ADMSageSubMenus2.properties";
    public static final String ListNone = "<None>";
    public static final String OptionNotFound = "Option not Found";
    public static final String ActionTypeDefault = "DoNothing";
    public static final String ButtonTextDefault = "<Not defined>";
    public static final String SortStyleDefault = "xNaturalOrder";
    private static final char[] symbols = new char[36];
    private static final Random random = new Random();
    public static Boolean ADMInitComplete = false;
    public static MenuItem[] MenuList = new MenuItem[8];
    public static Properties StandardActionProps = new Properties();
    public static Collection<String> StandardActionKeys = new LinkedHashSet<String>();
    public static Properties SageBGVariablesProps = new Properties();
    public static Collection<String> SageBGVariablesKeys = new LinkedHashSet<String>();
    public static Collection<String> SageSubMenusKeys = new LinkedHashSet<String>();
    public static Properties SageSubMenusLevel1Props = new Properties();
    public static Collection<String> SageSubMenusLevel1Keys = new LinkedHashSet<String>();
    public static Properties SageSubMenusLevel2Props = new Properties();
    public static Collection<String> SageSubMenusLevel2Keys = new LinkedHashSet<String>();
    public static Map<String,String>  SageTVRecordingViews = new LinkedHashMap<String,String>();

//    public static final String ActionTypeExecuteWidgetbySymbol = "ExecuteWidget";
//    public static final String ActionTypeExecuteBrowseVideoFolder = "ExecuteBrowseVideoFolder";
//    public static final String ActionTypeExecuteStandardMenuAction = "ExecuteStandardMenuAction";

    public class ActionType {
        public static final String WidgetbySymbol = "ExecuteWidget";
        public static final String BrowseVideoFolder = "ExecuteBrowseVideoFolder";
        public static final String StandardMenuAction = "ExecuteStandardMenuAction";
        public static final String TVRecordingView = "ExecuteTVRecordingView";
    }
    
    public static void InitADM(){
        
        if (!ADMInitComplete) {

            //ensure the ADM file location exists
            try{
                boolean success = (new File(ADMLocation)).mkdirs();
                if (success) {
                    System.out.println("ADM: InitADM - Directories created for '" + ADMLocation + "'");
                   }

                }catch (Exception ex){//Catch exception if any
                    System.out.println("ADM: InitADM - error creating '" + ADMLocation + "'" + ex.getMessage());
                }
            
            LoadMenuItemsFromSage();
            
            //also load the standard actions list - only needs loaded at startup
            LoadStandardActionList();
            //also load the BGVariables for BG Images on Top Level Menus
            LoadSageBGVariablesList();
            SageBGVariablesKeys.add(ListNone);
            //also load SubMenu lists for levels 1 and 2 and Diamond
            LoadSubMenuListLevel1();
            LoadSubMenuListLevel2();
            //Add in a -None- option to the list
            SageSubMenusLevel1Keys.add(ListNone);
            SageSubMenusLevel2Keys.add(ListNone);

            //clean up existing focus items
            sagex.api.Configuration.RemovePropertyAndChildren(SageFocusPropertyLocation);
        
            //generate symbols to be used for new MenuItem names
            for (int idx = 0; idx < 10; ++idx)
                symbols[idx] = (char) ('0' + idx);
            for (int idx = 10; idx < 36; ++idx)
                symbols[idx] = (char) ('a' + idx - 10);
            
            //load the SageTV Recording views
            LoadSageTVRecordingViews();
            
            ADMInitComplete = true;

            System.out.println("ADM: InitADM - initialization complete.");

        }

    }
    
    public static void ClearAll(){

        //backup existing MenuItems before clearing settings and menus
        if (MenuItem.MenuItemList.size()>0){
            ExportMenuItems(PropertyBackupFile);
        }
        
        //clear all the Sage property settings for ADM
        System.out.println("ADM: ClearAll: clear Sage Properties");
        sagex.api.Configuration.RemovePropertyAndChildren(SageADMBasePropertyLocation);
        ADMInitComplete = Boolean.FALSE;
        System.out.println("ADM: ClearAll: load default menus");
        LoadMenuItemDefaults();
        System.out.println("ADM: ClearAll: initialize settings");
        InitADM();
        System.out.println("ADM: ClearAll: complete - settings restored to defaults");
        
    }
    
    //saves all MenuItems to Sage properties
    public static void SaveMenuItemsToSage(){
        String PropLocation = "";
        
        //clean up existing MenuItems from the SageTV properties file before writing the new ones
        sagex.api.Configuration.RemovePropertyAndChildren(SagePropertyLocation);
        
        //iterate through all the MenuItems and save to SageTV properties
        Iterator<Entry<String,MenuItem>> itr = MenuItem.MenuItemList.entrySet().iterator(); 
        while (itr.hasNext()) {
            Entry<String,MenuItem> entry = itr.next();
            PropLocation = SagePropertyLocation + entry.getValue().getName();
            sagex.api.Configuration.SetProperty(PropLocation + "/Action", entry.getValue().getAction());
            sagex.api.Configuration.SetProperty(PropLocation + "/ActionType", entry.getValue().getActionType());
            sagex.api.Configuration.SetProperty(PropLocation + "/BGImageFile", entry.getValue().getBGImageFile());
            sagex.api.Configuration.SetProperty(PropLocation + "/ButtonText", entry.getValue().getButtonText());
            sagex.api.Configuration.SetProperty(PropLocation + "/Name", entry.getValue().getName());
            sagex.api.Configuration.SetProperty(PropLocation + "/Parent", entry.getValue().getParent());
            sagex.api.Configuration.SetProperty(PropLocation + "/SortKey", entry.getValue().getSortKey().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            sagex.api.Configuration.SetProperty(PropLocation + "/HasSubMenu", entry.getValue().getHasSubMenu().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/IsActive", entry.getValue().getIsActive().toString());
            //System.out.println("ADM: SaveMenuItemsToSage: saved - '" + entry.getValue().getName() + "'");
        }         
        System.out.println("ADM: SaveMenuItemsToSage: saved " + MenuItem.MenuItemList.size() + " MenuItems");
        
        return;
    }
    
    public static void DeleteMenuItem(String Name){
        //store the parent for later cleanup
        String OldParent = MenuItem.GetMenuItemParent(Name);
        //do all the deletes first
        DeleteMenuItemChildren(Name);
        //Make sure there is still one default Menu Item
        MenuItem.ValidateSubMenuDefault(OldParent);
        //rebuild any lists
        SaveMenuItemsToSage();
        LoadMenuItemsFromSage();
        System.out.println("ADM: DeleteMenuItem: deleted '" + Name + "' and reloaded Menus");
    }
    
    public static void DeleteMenuItemChildren(String Name){
        
        //find all submenus if any and delete them first
        Collection<String> Children = MenuItem.GetMenuItemNameList(Name, Boolean.TRUE);
        for (String Child:Children){
            DeleteMenuItemChildren(Child);
        }
        //delete this item
        MenuItem.MenuItemList.remove(Name);
        System.out.println("ADM: DeleteMenuItemChildren: deleted '" + Name + "' and '" + Children.size() + "' Children");
    }
    
    public static void DeleteAllMenuItems(){

        //backup existing MenuItems before deleting
        if (MenuItem.MenuItemList.size()>0){
            ExportMenuItems(PropertyBackupFile);
        }
        
        //clean up existing MenuItems from the SageTV properties file
        sagex.api.Configuration.RemovePropertyAndChildren(SagePropertyLocation);
        MenuItem.MenuItemList.clear();
        
        //Create 1 new MenuItem at the TopMenu level
        NewMenuItem(TopMenu, 1, 1) ;

        //now load the properties from the Sage properties file
        LoadMenuItemsFromSage();

        System.out.println("ADM: DeleteAllMenuItems: completed");
    }
    
    public static String NewMenuItem(String Parent, Integer SortKey, Integer Level){
        String tMenuItemName = GetNewMenuItemName();

        //Create a new MenuItem with defaults
        MenuItem NewMenuItem = new MenuItem(tMenuItemName);
        MenuItem.SetMenuItemAction(tMenuItemName,null);
        MenuItem.SetMenuItemActionType(tMenuItemName,ActionTypeDefault);
        MenuItem.SetMenuItemBGImageFile(tMenuItemName,ListNone);
        MenuItem.SetMenuItemButtonText(tMenuItemName,ButtonTextDefault);
        MenuItem.SetMenuItemName(tMenuItemName);
        MenuItem.SetMenuItemParent(tMenuItemName,Parent);
        MenuItem.SetMenuItemSortKey(tMenuItemName,SortKey);
        MenuItem.SetMenuItemSubMenu(tMenuItemName,ListNone);
        MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsDefault(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsActive(tMenuItemName,Boolean.TRUE);
        MenuItem.SetMenuItemLevel(tMenuItemName,Level);
        
        System.out.println("ADM: NewMenuItem: created '" + tMenuItemName + "' SortKey = '" + SortKey + "' Level = '" + Level + "'");
        return tMenuItemName;
    }
    
    public static void LoadMenuItemsFromSage(){
        String PropLocation = "";
        
        //find all MenuItem Name entries from the SageTV properties file
        String[] MenuItemNames = sagex.api.Configuration.GetSubpropertiesThatAreBranches(SagePropertyLocation);
        
        if (MenuItemNames.length>0){
            //clear the existing MenuItems from the list
            MenuItem.MenuItemList.clear();
            
            //load MenuItems
            for (String tMenuItemName : MenuItemNames){
                PropLocation = SagePropertyLocation + tMenuItemName;
                MenuItem NewMenuItem = new MenuItem(tMenuItemName);
                NewMenuItem.setAction(sagex.api.Configuration.GetProperty(PropLocation + "/Action", null));
                NewMenuItem.setActionType(sagex.api.Configuration.GetProperty(PropLocation + "/ActionType", ActionTypeDefault));
                NewMenuItem.setBGImageFile(sagex.api.Configuration.GetProperty(PropLocation + "/BGImageFile", null));
                NewMenuItem.setButtonText(sagex.api.Configuration.GetProperty(PropLocation + "/ButtonText", ButtonTextDefault));
                NewMenuItem.setName(sagex.api.Configuration.GetProperty(PropLocation + "/Name", tMenuItemName));
                NewMenuItem.setParent(sagex.api.Configuration.GetProperty(PropLocation + "/Parent", "xTopMenu"));
                NewMenuItem.setSortKey(sagex.api.Configuration.GetProperty(PropLocation + "/SortKey", null));
                NewMenuItem.setSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null));
                NewMenuItem.setHasSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/HasSubMenu", "false"));
                NewMenuItem.setIsDefault(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
                NewMenuItem.setIsActive(sagex.api.Configuration.GetProperty(PropLocation + "/IsActive", "true"));
                //System.out.println("ADM: LoadMenuItemsFromSage: loaded - '" + tMenuItemName + "'");
            }

        }else{
            //load a default Menu here.  Load a Diamond Menu if Diamond if active
            System.out.println("ADM: LoadMenuItemsFromSage: no MenuItems found - loading default menu.");
            LoadMenuItemDefaults();
        }
        System.out.println("ADM: LoadMenuItemsFromSage: loaded " + MenuItem.MenuItemList.size() + " MenuItems");
        
        //now that the menus are loaded - set a level for each menu item and store it
        MenuItem.SetMenuItemLevels();
        //now ensure SortKeys are in order
        FixSortOrder();
        
        return;
    }

    private static Boolean IsDiamond(){
        String DiamondPluginID = "DiamondSTVi";
        String DiamondWidgetSymbol = "AOSCS-65";
        // check to see if the Diamond Plugin is installed
        UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
        Object[] FoundWidget = new Object[1];
        FoundWidget[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, DiamondWidgetSymbol);
        if (sagex.api.PluginAPI.IsPluginEnabled(sagex.api.PluginAPI.GetAvailablePluginForID(DiamondPluginID)) || FoundWidget[0]!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    public static void LoadMenuItemDefaults(){
        //load default MenuItems from one or more default .properties file
        String DefaultPropFile = "ADMDefault.properties";
        String DefaultPropFileDiamond = "ADMDefaultDiamond.properties";
        String DefaultsFullPath = ADMDefaultsLocation + File.separator + DefaultPropFile;
        String DiamondVideoMenuCheckProp = "JOrton/MainMenu/ShowDiamondMoviesTab";
        String DiamondMenuVideos = "admSageTVVideos";
        String DiamondMenuMovies = "admDiamondMovies";
        
        
        // check to see if the Diamond Plugin is installed
        if (IsDiamond()){
            DefaultsFullPath = ADMDefaultsLocation + File.separator + DefaultPropFileDiamond;
        }
        ImportMenuItems(DefaultsFullPath);
        
        //for Diamond we need to Hide either the Videos Menu Item or the Movies Menu Item
        if (IsDiamond()){
            //admSageTVVideos
            if ("true".equals(sagex.api.Configuration.GetProperty(DiamondVideoMenuCheckProp, "false"))){
                //show the Videos Menu
                MenuItem.SetMenuItemIsActive(DiamondMenuMovies, Boolean.TRUE);
                MenuItem.SetMenuItemIsActive(DiamondMenuVideos, Boolean.FALSE);
            }else{
                //show the Movies Menu
                MenuItem.SetMenuItemIsActive(DiamondMenuMovies, Boolean.FALSE);
                MenuItem.SetMenuItemIsActive(DiamondMenuVideos, Boolean.TRUE);
            }
            
        }
        
        System.out.println("ADM: LoadMenuItemDefaults: loading default menu items from '" + DefaultsFullPath + "'");
        
        
//        //use the following until the Load from properties is coded.
//        Object tObject;
//
//        tObject = new MenuItem("xTopMenu", "xItemTV",1, "TV", true,"xSubmenuTV", "ExecuteWidget", "OPUS4A-174600", "gTVBackgroundImage", false,true);
//        tObject = new MenuItem("xTopMenu", "xItemVideos",2, "Videos SubMenu", true,"xSubmenuVideos", "ExecuteWidget", "OPUS4A-174615", "gVideoBackgroundImage", false,true);
//        tObject = new MenuItem("xTopMenu", "xItemMusic",3, "Music", true, "xSubmenuMusic", "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false,true);
//        tObject = new MenuItem("xTopMenu", "xItemTestTop",4, "Test 1", true, null, "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false,true);
//        tObject = new MenuItem("xTopMenu", "xItemPhotos",5, "Photos", true,"xSubmenuPhotos", "ExecuteWidget", "OPUS4A-174617", "gPhotoBackgroundImage", false,true);
//        tObject = new MenuItem("xTopMenu", "xDetailedSetup",6, "Detailed Setup",false, null, "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage", false,true);
//        tObject = new MenuItem("xItemTest", "xItemTestSub1",7, "Test 1 - 1", true,"xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174604", "gTVBackgroundImage", false,true);
//        tObject = new MenuItem("xItemTest", "xItemTestSub2",8, "Test 1 - 2", true,"xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", true,true);
//        tObject = new MenuItem("xItemTest", "xItemTestSub3",9, "Test 1 - 3", true,"xSubmenuTVScheduleRecord", null, null, "gTVBackgroundImage", false,true);
//        tObject = new MenuItem("xItemTest", "xItemTestSub4",10, "Test 1 - 4", false, null, "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", false,true);
        
    }

    public static Boolean ImportMenuItems(String ImportPath){

        if (ImportPath==null){
            System.out.println("ADM: ImportMenuItems: null ImportPath passed.");
            return false;
        }
        
        Properties MenuItemProps = new Properties();
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(ImportPath);
            try {
                MenuItemProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: ImportMenuItems: IO exception inporting menus " + util.class.getName() + ex);
                return false;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: ImportMenuItems: file not found inporting menus " + util.class.getName() + ex);
            return false;
        }
        
        //backup existing MenuItems before processing the import if any exist
        if (MenuItem.MenuItemList.size()>0){
            ExportMenuItems(PropertyBackupFile);
        }
        
        if (MenuItemProps.size()>0){
            //clean up existing MenuItems from the SageTV properties file before writing the new ones
            sagex.api.Configuration.RemovePropertyAndChildren(SagePropertyLocation);
            
            //load MenuItems from the properties file and write to the Sage properties
            for (String tPropertyKey : MenuItemProps.stringPropertyNames()){
                sagex.api.Configuration.SetProperty(tPropertyKey, MenuItemProps.getProperty(tPropertyKey));
                
                //System.out.println("ADM: ImportMenuItems: imported - '" + tPropertyKey + "' = '" + MenuItemProps.getProperty(tPropertyKey) + "'");
            }
            
            //now load the properties from the Sage properties file
            LoadMenuItemsFromSage();

        }
        System.out.println("ADM: ImportMenuItems: completed for '" + ImportPath + "'");
        return true;
    }
    
    public static void ExportMenuItems(String ExportFile){
        String PropLocation = "";
        String ExportFilePath = ADMLocation + File.separator + ExportFile;
        //System.out.println("ADM: ExportMenuItems: Full Path = '" + ExportFilePath + "'");
        
        //iterate through all the MenuItems and save to a Property Collection
        Properties MenuItemProps = new Properties();

        Iterator<Entry<String,MenuItem>> itr = MenuItem.MenuItemList.entrySet().iterator(); 
        while (itr.hasNext()) {
            Entry<String,MenuItem> entry = itr.next();
            PropLocation = SagePropertyLocation + entry.getValue().getName();
            if (entry.getValue().getAction()!=null){
                MenuItemProps.setProperty(PropLocation + "/Action",entry.getValue().getAction());
            }
            if (entry.getValue().getActionType()!=null){
                MenuItemProps.setProperty(PropLocation + "/ActionType", entry.getValue().getActionType());
            }
            if (entry.getValue().getBGImageFile()!=null){
                MenuItemProps.setProperty(PropLocation + "/BGImageFile", entry.getValue().getBGImageFile());
            }
            if (entry.getValue().getButtonText()!=null){
                MenuItemProps.setProperty(PropLocation + "/ButtonText", entry.getValue().getButtonText());
            }
            MenuItemProps.setProperty(PropLocation + "/Name", entry.getValue().getName());
            if (entry.getValue().getParent()!=null){
                MenuItemProps.setProperty(PropLocation + "/Parent", entry.getValue().getParent());
            }
            if (entry.getValue().getSortKey()!=null){
                MenuItemProps.setProperty(PropLocation + "/SortKey", entry.getValue().getSortKey().toString());
            }
            if (entry.getValue().getSubMenu()!=null){
                MenuItemProps.setProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            }
            MenuItemProps.setProperty(PropLocation + "/HasSubMenu", entry.getValue().getHasSubMenu().toString());
            MenuItemProps.setProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            MenuItemProps.setProperty(PropLocation + "/IsActive", entry.getValue().getIsActive().toString());
            //System.out.println("ADM: ExportMenuItems: exported - '" + entry.getValue().getName() + "'");
        }         

        //if the export file exists then delete it before exporting
        
        //write the properties to the properties file
        try {
            FileOutputStream out = new FileOutputStream(ExportFilePath);
            try {
                MenuItemProps.store(out, PropertyComment);
                out.close();
            } catch (IOException ex) {
                System.out.println("ADM: ExportMenuItems: error exporting menus " + util.class.getName() + ex);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: ExportMenuItems: error exporting menus " + util.class.getName() + ex);
        }

        System.out.println("ADM: ExportMenuItems: exported " + MenuItem.MenuItemList.size() + " MenuItems");
        
        return;
    }
    
    public static void ExecuteMenuItem(String Name){
        String tActionType = MenuItem.GetMenuItemActionType(Name);
        if(tActionType.equals(ActionType.BrowseVideoFolder)){
            LaunchVideoBrowser(MenuItem.GetMenuItemAction(Name));
        }else if(tActionType.equals(ActionType.StandardMenuAction)){
            ExecuteWidget(MenuItem.GetMenuItemAction(Name));
        }else if(tActionType.equals(ActionType.TVRecordingView)){
            LaunchTVRecordingsView(MenuItem.GetMenuItemAction(Name));
        }else if(tActionType.equals(ActionType.WidgetbySymbol)){
            ExecuteWidget(MenuItem.GetMenuItemAction(Name));
        }
        //else do nothing
    }
    
    public static void ExecuteWidget(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: ExecuteWidget - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
        }else{
            System.out.println("ADM: ExecuteWidget - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

        try {
            sage.SageTV.apiUI(sagex.api.Global.GetUIContextName(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
        } catch (InvocationTargetException ex) {
            System.out.println("ADM: ExecuteWidget: error executing widget" + util.class.getName() + ex);
        }
            
            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }
    
    public static Boolean IsWidgetValid(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: IsWidgetValid - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: IsWidgetValid - FindWidgetSymbol passed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.TRUE;
        }
               
    }
    
    public static String GetWidgetName(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: GetWidgetName - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return null;
        }else{
            String WidgetName = sagex.api.WidgetAPI.GetWidgetName(MyUIContext, WidgetSymbol);
            System.out.println("ADM: GetWidgetName for Symbol = '" + WidgetSymbol + "' = '" + WidgetName + "'");
            return WidgetName;
        }
               
    }
    
    public static void LaunchVideoBrowser(String FoldertoLaunch){
        if (FoldertoLaunch.equals("/")){
            FoldertoLaunch = null;
        }
        System.out.println("ADM: LaunchVideoBrowser - for Folder = '" + FoldertoLaunch + "'");
        String WidgetSymbol = "OPUS4A-174637";
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: LaunchVideoBrowser - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
        }else{
            //set the current folder as a Global Context 
            sagex.api.Global.AddGlobalContext(MyUIContext, "gCurrentVideoBrowserFolder", FoldertoLaunch);
            
            System.out.println("ADM: LaunchVideoBrowser - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

            try {
                sage.SageTV.apiUI(sagex.api.Global.GetUIContextName(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
            } catch (InvocationTargetException ex) {
                System.out.println("ADM: LaunchVideoBrowser: error executing widget" + util.class.getName() + ex);
            }

            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }
    
    public static void LaunchTVRecordingsView(String ViewType){
        System.out.println("ADM: LaunchTVRecordingsView - for ViewType = '" + ViewType + "'");
        String WidgetSymbol = "OPUS4A-174116";
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: LaunchTVRecordingsView - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
        }else{
            //set the current ViewType as a Static Context 
            sagex.api.Global.AddStaticContext(MyUIContext, "ViewFilter", ViewType);
            
            System.out.println("ADM: LaunchTVRecordingsView - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

            try {
                sage.SageTV.apiUI(sagex.api.Global.GetUIContextName(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
            } catch (InvocationTargetException ex) {
                System.out.println("ADM: LaunchTVRecordingsView: error executing widget" + util.class.getName() + ex);
            }

            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }
    
    public static String GetElement(Collection<String> List, Integer element){
        System.out.println("ADM: GetElement: looking for element " + element + " in:" + List);
        Integer counter = 0;
        for (String CurElement:List){
            counter++;
            System.out.println("ADM: GetElement: checking element '" + counter + "' = '" + CurElement + "'");
            if (counter.equals(element)){
                System.out.println("ADM: GetElement: found '" + CurElement + "'");
                return CurElement;
            }
        }
        System.out.println("ADM: GetElement: not found.");
        return null;
    }
    
    //Create/Edit MenuItems Dialog Options
    public static Collection<String> GetEditOptionsList(String Name){
        Collection<String> EditOptions = new LinkedHashSet<String>();
        //Determine valid Edit Options for the passed in MenuItem Name
        EditOptions.add("admEditMenuItem"); //Edit current Menu Item
        EditOptions.add("admAddMenuItem"); //Add current Menu Item below
        if (MenuItem.GetMenuItemLevel(Name)<3){
            EditOptions.add("admAddSubMenuItem"); //Add SubMenu to current Menu Item
        }
        EditOptions.add("admDeleteMenuItem"); //Delete current Menu Item
        EditOptions.add("admCloseEdit"); //Close the Edit Menu
        System.out.println("ADM: GetEditOptionsList - Loaded list for '" + Name + "' :" + EditOptions);
        return EditOptions;
    }
    
    public static String GetEditOptionButtonText(String Option){
        String ButtonText = OptionNotFound;
        if("admEditMenuItem".equals(Option)){
            ButtonText = "Edit";
        }else if("admAddMenuItem".equals(Option)){
            ButtonText = "Add Menu Item below";
        }else if("admAddSubMenuItem".equals(Option)){
            ButtonText = "Add Submenu Item below";
        }else if("admDeleteMenuItem".equals(Option)){
            ButtonText = "Delete";
        }else if("admCloseEdit".equals(Option)){
            ButtonText = "Close";
        }
        System.out.println("ADM: GetEditOptionButtonText returned '" + ButtonText + "' for '" + Option + "'");
        return ButtonText;
    }
    
    public static String GetActionTypeButtonText(String Option){
        String ButtonText = OptionNotFound;
        if(ActionType.WidgetbySymbol.equals(Option)){
            ButtonText = "Execute Widget by Symbol";
        }else if(ActionTypeDefault.equals(Option)){
            ButtonText = "None";
        }else if(ActionType.StandardMenuAction.equals(Option)){
            ButtonText = "Execute Standard Sage Menu Action";
        }else if(ActionType.BrowseVideoFolder.equals(Option)){
            ButtonText = "Video Browser with specific Folder";
        }else if(ActionType.TVRecordingView.equals(Option)){
            ButtonText = "Launch Specific TV Recordings View";
        }
        System.out.println("ADM: GetActionTypeButtonText returned '" + ButtonText + "' for '" + Option + "'");
        return ButtonText;
    }
    
    public static void LoadSubMenuListLevel1(){
        String SubMenuPropsPath = ADMDefaultsLocation + File.separator + SageSubMenusLevel1ListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(SubMenuPropsPath);
            try {
                SageSubMenusLevel1Props.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSubMenuListLevel1: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSubMenuListLevel1: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> SageSubMenusList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String SageSubMenusItem : SageSubMenusLevel1Props.stringPropertyNames()){
            SageSubMenusList.put(SageSubMenusLevel1Props.getProperty(SageSubMenusItem),SageSubMenusItem);
        }

        //build a list of keys in the order of the values
        for (String SageSubMenusValue : SageSubMenusList.keySet()){
            SageSubMenusLevel1Keys.add(SageSubMenusList.get(SageSubMenusValue));
            SageSubMenusKeys.add(SageSubMenusList.get(SageSubMenusValue));
        }
        
        //Add in a -None- option to the list
        SageSubMenusLevel1Props.put(ListNone,ListNone);
        
        System.out.println("ADM: LoadSubMenuListLevel1: completed for '" + SubMenuPropsPath + "'");
        return;
    }

    public static void LoadSubMenuListLevel2(){
        String SubMenuPropsPath = ADMDefaultsLocation + File.separator + SageSubMenusLevel2ListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(SubMenuPropsPath);
            try {
                SageSubMenusLevel2Props.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSubMenuListLevel2: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSubMenuListLevel2: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> SageSubMenusList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String SageSubMenusItem : SageSubMenusLevel2Props.stringPropertyNames()){
            SageSubMenusList.put(SageSubMenusLevel2Props.getProperty(SageSubMenusItem),SageSubMenusItem);
        }

        //build a list of keys in the order of the values
        for (String SageSubMenusValue : SageSubMenusList.keySet()){
            SageSubMenusLevel2Keys.add(SageSubMenusList.get(SageSubMenusValue));
            SageSubMenusKeys.add(SageSubMenusList.get(SageSubMenusValue));
        }
        
        //Add in a -None- option to the list
        SageSubMenusLevel2Props.put(ListNone,ListNone);
        
        System.out.println("ADM: LoadSubMenuListLevel2: completed for '" + SubMenuPropsPath + "'");
        return;
    }

    public static void LoadStandardActionList(){
        String StandardActionPropsPath = ADMDefaultsLocation + File.separator + StandardActionListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(StandardActionPropsPath);
            try {
                StandardActionProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadStandardActionList: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadStandardActionList: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> ActionValuesList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String ActionItem : StandardActionProps.stringPropertyNames()){
            ActionValuesList.put(StandardActionProps.getProperty(ActionItem),ActionItem);
        }

        //build a list of keys in the order of the values
        for (String ActionValue : ActionValuesList.keySet()){
            StandardActionKeys.add(ActionValuesList.get(ActionValue));
        }
        
        System.out.println("ADM: LoadStandardActionList: completed for '" + StandardActionPropsPath + "'");
        return;
    }

    public static void LoadSageBGVariablesList(){
        String StandardActionPropsPath = ADMDefaultsLocation + File.separator + SageBGVariablesListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(StandardActionPropsPath);
            try {
                SageBGVariablesProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSageBGVariablesList: IO exception loading SageBGVariables " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSageBGVariablesList: file not found loading SageBGVariables " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> ActionValuesList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String ActionItem : SageBGVariablesProps.stringPropertyNames()){
            ActionValuesList.put(SageBGVariablesProps.getProperty(ActionItem),ActionItem);
        }

        //build a list of keys in the order of the values
        for (String ActionValue : ActionValuesList.keySet()){
            SageBGVariablesKeys.add(ActionValuesList.get(ActionValue));
        }
        
        System.out.println("ADM: LoadSageBGVariablesList: completed for '" + StandardActionPropsPath + "'");
        return;
    }

    public static String GetSageBGVariablesButtonText(String Option){
        if (Option==null || Option.equals(ListNone)){
            return ListNone;
        }
        //determine if using Advanced options
        if ("true".equals(sagex.api.Configuration.GetProperty(AdvancedModePropertyLocation, "false"))){
            return SageBGVariablesProps.getProperty(Option, ListNone) + " (" + Option + ")";
        }else{
            return SageBGVariablesProps.getProperty(Option, ListNone);
        }
    }

    public static String GetStandardActionButtonText(String Option){
        //determine if using Advanced options
        if ("true".equals(sagex.api.Configuration.GetProperty(AdvancedModePropertyLocation, "false"))){
            return StandardActionProps.getProperty(Option, OptionNotFound) + " (" + Option + ")";
        }else{
            return StandardActionProps.getProperty(Option, OptionNotFound);
        }
    }

    public static Collection<String> GetSageBGVariablesList(){
        return SageBGVariablesKeys;
    }

    public static Collection<String> GetStandardActionList(){
        return StandardActionKeys;
    }

    public static Boolean IsSageSubMenu(String SubMenu){
        return !MenuItem.MenuItemList.containsKey(SubMenu);
//        if (SubMenu.startsWith("adm", 0)){
//            System.out.println("ADM: IsSageSubMenu: SubMenu '" + SubMenu + "' NOT a Sage Submenu");
//            return Boolean.FALSE;
//        }else{
//            System.out.println("ADM: IsSageSubMenu: SubMenu '" + SubMenu + "' IS a Sage Submenu");
//            return Boolean.TRUE;
//        }
        //return SageSubMenusKeys.contains(SubMenu);
    }

    //save the current Folder item details to sage properties to assist the copy function
    public static void SaveCurrentVideoFolderDetails(String CurFolder, String FolderName){
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "Folder");
        String FolderPath = "";
        if (FolderName!=null){
            FolderPath = FolderName;
        }

        if (CurFolder!=null){
            FolderPath = CurFolder + FolderPath;
        }
        //ensure the Folder string ends in a "/"
        if (FolderPath.isEmpty() || !FolderPath.endsWith("/")){
            FolderPath = FolderPath + "/";
        }
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", FolderPath);
        
        System.out.println("ADM: SaveCurrentVideoFolderDetails: FolderName '" + FolderPath + "'");
    }
    
    public static String GetCurrentVideoFolderDetails(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", OptionNotFound);
    }
    
    public static String GetCurrentVideoFolderDetailsButtonText(){
        String ButtonText = sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", ButtonTextDefault);
        ButtonText = ButtonText.replace("/", " ").trim();
        if (ButtonText.isEmpty()){
            ButtonText = "Root";
        }
        return ButtonText;
    }
    
    public static Collection<String> GetCurrentVideoFolderDetailsParentList(){
        return MenuItem.GetMenuItemParentList();
    }
    
    //create a new Menu Item from the current Video Folder Menu Item details
    public static String CreateMenuItemfromVideoFolderCopyDetails(String Parent){
        //
        String tMenuItemName = GetNewMenuItemName();

        //Create a new MenuItem with defaults
        MenuItem NewMenuItem = new MenuItem(tMenuItemName);
        MenuItem.SetMenuItemAction(tMenuItemName,GetCurrentVideoFolderDetails());
        MenuItem.SetMenuItemActionType(tMenuItemName,ActionType.BrowseVideoFolder);

        MenuItem.SetMenuItemBGImageFile(tMenuItemName,ListNone);
        MenuItem.SetMenuItemButtonText(tMenuItemName,GetCurrentVideoFolderDetailsButtonText());
        MenuItem.SetMenuItemName(tMenuItemName);
        MenuItem.SetMenuItemParent(tMenuItemName,Parent);
        MenuItem.SetMenuItemSortKey(tMenuItemName,MenuItem.SortKeyCounter++);
        MenuItem.SetMenuItemSubMenu(tMenuItemName,ListNone);
        MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsDefault(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsActive(tMenuItemName,Boolean.TRUE);
        
        //Level needs to be 1 more than the Parent
        MenuItem.SetMenuItemLevel(tMenuItemName,MenuItem.GetMenuItemLevel(Parent)+1);
        
        System.out.println("ADM: CreateMenuItemfromVideoFolderCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    //save the current item details to sage properties to assist the copy function
    public static void SaveCurrentMenuItemDetails(String ButtonText, String SubMenu, String CurrentWidgetSymbol, Integer Level){
        //due to an issue with getting the current widget we save that info directly in the STV and then retrieve it here
        //String CurrentWidgetSymbol = sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", OptionNotFound);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "MenuItem");
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", CurrentWidgetSymbol);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", ButtonText);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", SubMenu);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Level", Level.toString());
        
        //determine if there is an Action Widget for this Menu Item
        String Action = null;
        Object[] Children = sagex.api.WidgetAPI.GetWidgetChildren(MyUIContext, CurrentWidgetSymbol);
        for (Object Child : Children){
            //System.out.println("ADM: SaveCurrentMenuItemDetails: WidgetName = '" + sagex.api.WidgetAPI.GetWidgetName(MyUIContext,Child) + "' WidgetType '" + sagex.api.WidgetAPI.GetWidgetType(MyUIContext,Child) + "'");
            if ("Action".equals(sagex.api.WidgetAPI.GetWidgetType(MyUIContext,Child))){
                //found an action so save it and leave
                Action = sagex.api.WidgetAPI.GetWidgetSymbol(MyUIContext,Child);
                break;
            }
        }
        if (Action!=null){
            sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Action", Action);
        }else{
            sagex.api.Configuration.RemoveProperty(SageCurrentMenuItemPropertyLocation + "Action");
        }
        System.out.println("ADM: SaveCurrentMenuItemDetails: ButtonText '" + ButtonText + "' SubMenu '" + SubMenu + "' WidgetSymbol '" + CurrentWidgetSymbol + "' Action '" + Action + " Level '" + Level + "'");
    }
    
    //create a new Menu Item from the current Menu Item details
    public static String CreateMenuItemfromCopyDetails(String Parent){
        //
        String tMenuItemName = GetNewMenuItemName();

        //Create a new MenuItem with defaults
        MenuItem NewMenuItem = new MenuItem(tMenuItemName);
        if (!GetCurrentMenuItemDetailsAction().equals(OptionNotFound)){
            MenuItem.SetMenuItemAction(tMenuItemName,GetCurrentMenuItemDetailsAction());
            MenuItem.SetMenuItemActionType(tMenuItemName,ActionType.WidgetbySymbol);
        }
        MenuItem.SetMenuItemBGImageFile(tMenuItemName,ListNone);
        MenuItem.SetMenuItemButtonText(tMenuItemName,GetCurrentMenuItemDetailsButtonText());
        MenuItem.SetMenuItemName(tMenuItemName);
        MenuItem.SetMenuItemParent(tMenuItemName,Parent);
        MenuItem.SetMenuItemSortKey(tMenuItemName,MenuItem.SortKeyCounter++);
        if (GetCurrentMenuItemDetailsSubMenu().equals(OptionNotFound)){
            MenuItem.SetMenuItemSubMenu(tMenuItemName,ListNone);
            MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.FALSE);
        }else{
            MenuItem.SetMenuItemSubMenu(tMenuItemName,GetCurrentMenuItemDetailsSubMenu());
            MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.TRUE);
        }
        MenuItem.SetMenuItemIsDefault(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsActive(tMenuItemName,Boolean.TRUE);
        
        //Level needs to be 1 more than the Parent
        MenuItem.SetMenuItemLevel(tMenuItemName,MenuItem.GetMenuItemLevel(Parent)+1);
        
        System.out.println("ADM: CreateMenuItemfromCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    public static String CreateMenuItemfromCopyDetails(){
        //default the Parent to TopMenu
        return CreateMenuItemfromCopyDetails(TopMenu);
    }
    
    public static Collection<String> GetCurrentMenuItemDetailsParentList(){
        if (GetCurrentMenuItemDetailsSubMenu().equals(OptionNotFound)){
            return MenuItem.GetMenuItemParentList();
        }else{
            return MenuItem.GetMenuItemParentList(GetCurrentMenuItemDetailsLevel());
        }
    }
    
    public static String GetCurrentMenuItemDetailsButtonText(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", OptionNotFound);
    }
    
    public static String GetCurrentMenuItemDetailsAction(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "Action", OptionNotFound);
    }
    
    public static Integer GetCurrentMenuItemDetailsLevel(){
        Integer tLevel = 0;
        try {
            tLevel = Integer.valueOf(sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "Level", "0"));
        } catch (NumberFormatException ex) {
            System.out.println("ADM: GetCurrentMenuItemDetailsLevel: error loading level: " + util.class.getName() + ex);
            tLevel = 0;
        }
        //System.out.println("ADM: GetCurrentMenuItemDetailsLevel: returning level = '" + tLevel + "'");
        return tLevel;
    }
    
    public static String GetCurrentMenuItemDetailsWidgetSymbol(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", OptionNotFound);
    }
    
    public static String GetCurrentMenuItemDetailsSubMenu(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", OptionNotFound);
    }
    
    //Save the current item that is focused for later retrieval
    public static void SetLastFocusForSubMenu(String SubMenu, String FocusItem){
        System.out.println("ADM: SetLastFocusForSubMenu: SubMenu '" + SubMenu + "' to '" + FocusItem + "'");
        sagex.api.Configuration.SetProperty(SageFocusPropertyLocation + SubMenu, FocusItem);
    }

    public static String GetLastFocusForSubMenu(String SubMenu){
        String LastFocus = sagex.api.Configuration.GetProperty(SageFocusPropertyLocation + SubMenu,OptionNotFound);
        if (LastFocus.equals(OptionNotFound)){
            //return the DefaultMenuItem for this SubMenu
            System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' not found - returning DEFAULT");
            return MenuItem.GetSubMenuDefault(SubMenu);
        }else{
            //check that the focus item stored in Sage is still valid
            if (MenuItem.IsSubMenuItem(SubMenu, LastFocus)){
                System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' returning = '" + LastFocus + "'");
                return LastFocus;
            }else{
                System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' not valid - returning DEFAULT");
                return MenuItem.GetSubMenuDefault(SubMenu);
            }
        }
    }
    
    public static String GetSubMenuListButtonText(String Option, Integer Level){
        return GetSubMenuListButtonText(Option, Level, Boolean.FALSE);
    }
    
    public static String GetSubMenuListButtonText(String Option, Integer Level, Boolean SkipAdvanced){
        System.out.println("ADM: GetSubMenuListButtonText: Option '" + Option + "' for Level = '" + Level + "'");
        if (Option==null || Option.equals(ListNone)){
            return ListNone;
        }
        String RetVal = "";
        if (Level==1){
            RetVal = SageSubMenusLevel1Props.getProperty(Option, ListNone);
        }else{
            RetVal = SageSubMenusLevel2Props.getProperty(Option, ListNone);
        }

        //determine if using Advanced options
        if ("true".equals(sagex.api.Configuration.GetProperty(AdvancedModePropertyLocation, "false")) && !SkipAdvanced){
            return RetVal + " (" + Option + ")";
        }else{
            return RetVal;
        }
    }

    public static Collection<String> GetSubMenuList(Integer Level){
        if (Level==1){
            return SageSubMenusLevel1Keys;
        }else{
            return SageSubMenusLevel2Keys;
        }
    }
            
    public static String GetVersion() {
        return Version;
    }
    
    public static String GetADMLocation() {
        return ADMLocation;
    }
    
    public static String GetNewMenuItemName(){
        Boolean UniqueName = Boolean.FALSE;
        String NewName = null;
        while (!UniqueName){
            NewName = GenerateRandomadmName();
            //check to see that the name is unique from other existing MenuItemNames
            UniqueName = !MenuItem.MenuItemList.containsKey(NewName);
        }
        return NewName;
    }

    private static String GenerateRandomadmName(){
        char[] buf = new char[10];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return "adm" + new String(buf);
    }

    public static Float[] GetMenuInsets(){
        Float[] Insets = new Float[]{0f,0f,0f,0f};
        Float[] DiamondInsets = new Float[]{0f,0f,0.015f,0f};
        if (IsDiamond()){
            System.out.println("ADM: GetMenuInsets: Diamond");
            return DiamondInsets;
        }else{
            System.out.println("ADM: GetMenuInsets:");
            return Insets;
        }
    }

    public static void FixSortOrder(){
        Integer Counter = 0;
        for (String Item : MenuItem.GetMenuItemSortedList(Boolean.FALSE)){
            ++Counter;
            if (!MenuItem.GetMenuItemSortKey(Item).equals(Counter)){
                MenuItem.SetMenuItemSortKeyNoCheck(Item, Counter);
                System.out.println("ADM: FixSortOrder: Name '" + Item + "' changed to SortKey = '" + Counter + "'");
            }
        }
    }

    public static void LoadSageTVRecordingViews(){
        //put the ViewType and Default View Name into a list
        SageTVRecordingViews.put("xAll","All Recordings");
        SageTVRecordingViews.put("xRecordings","Archived Recordings");
        SageTVRecordingViews.put("xArchives","Recorded Movies");
        SageTVRecordingViews.put("xMovies","Current Recordings");
        //SageTVRecordingViews.put("xPartials","");
        SageTVRecordingViews.put("xView5","Recording View5");
        SageTVRecordingViews.put("xView6","Recording View6");
        SageTVRecordingViews.put("xView7","Recording View7");
        SageTVRecordingViews.put("xView8","Recording View8");
    }

    public static Collection<String> GetSageTVRecordingViewsList(){
        return SageTVRecordingViews.keySet();
    }
    
    public static String GetSageTVRecordingViewsButtonText(String Name){
        //return the stored name from Sage or the Default Name if nothing is stored
        return sagex.api.Configuration.GetProperty(SageTVRecordingViewsTitlePropertyLocation + Name, SageTVRecordingViews.get(Name));
    }
    
}
