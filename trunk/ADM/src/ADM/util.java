/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import sagex.UIContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class util {

    public static String Version = "0.14";
    private static final String PropertyComment = "---ADM MenuItem Properties - Do Not Manually Edit---";
    private static final String SagePropertyLocation = "ADM/menuitem/";
    private static final String PropertyBackupFile = "ADMbackup.properties";
    private static final String ADMLocation = sagex.api.Utility.GetWorkingDirectory() + "\\userdata\\ADM";
    public static Boolean MenuListLoaded = false;
    public static MenuItem[] MenuList = new MenuItem[8];

    public static void LoadMenuList(){
        
        if (!MenuListLoaded) {

            LoadMenuItemsFromSage();
           
            MenuListLoaded = true;

            System.out.println("ADM: LoadMenuList - Loaded menu list:" + MenuItem.GetMenuItemNameList());

        }

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
            sagex.api.Configuration.SetProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            sagex.api.Configuration.SetProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            System.out.println("ADM: SaveMenuItemsToSage: saved - '" + entry.getValue().getName() + "'");
        }         
        System.out.println("ADM: SaveMenuItemsToSage: saved " + MenuItem.MenuItemList.size() + " MenuItems");
        
        return;
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
                NewMenuItem.setActionType(sagex.api.Configuration.GetProperty(PropLocation + "/ActionType", null));
                NewMenuItem.setBGImageFile(sagex.api.Configuration.GetProperty(PropLocation + "/BGImageFile", null));
                NewMenuItem.setButtonText(sagex.api.Configuration.GetProperty(PropLocation + "/ButtonText", "<Not defined>"));
                NewMenuItem.setName(sagex.api.Configuration.GetProperty(PropLocation + "/Name", tMenuItemName));
                NewMenuItem.setParent(sagex.api.Configuration.GetProperty(PropLocation + "/Parent", "xTopMenu"));
                NewMenuItem.setSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null));
                NewMenuItem.setIsDefault(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
                System.out.println("ADM: LoadMenuItemsFromSage: loaded - '" + tMenuItemName + "'");
            }

        }else{
            //load a default Menu here.  Load a Diamond Menu if Diamond if active
            System.out.println("ADM: LoadMenuItemsFromSage: no MenuItems found - loading default menu.");
            LoadMenuItemDefaults();
        }
        System.out.println("ADM: LoadMenuItemsFromSage: loaded " + MenuItem.MenuItemList.size() + " MenuItems");
        
        return;
    }
    
    private static void LoadMenuItemDefaults(){
        //load default MenuItems from one or more default .properties file
        
        //use the following until the Load from properties is coded.
        Object tObject;

        tObject = new MenuItem("xTopMenu", "xItemTV", "TV", "xSubmenuTV", "ExecuteWidget", "OPUS4A-174600", "gTVBackgroundImage", false);
        tObject = new MenuItem("xTopMenu", "xItemMusic", "Music", "xSubmenuMusic", "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false);
        tObject = new MenuItem("xTopMenu", "xItemTestTop", "Test 1", "xItemTest", "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false);
        tObject = new MenuItem("xItemTest", "xItemTestSub1", "Test 1 - 1", "xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174604", "gTVBackgroundImage", false);
        tObject = new MenuItem("xItemTest", "xItemTestSub2", "Test 1 - 2", "xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", true);
        tObject = new MenuItem("xTopMenu", "xItemPhotos", "Photos", "xSubmenuPhotos", "ExecuteWidget", "OPUS4A-174617", "gPhotoBackgroundImage", false);
        tObject = new MenuItem("xTopMenu", "xDetailedSetup", "Detailed Setup", null, "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage", false);
        tObject = new MenuItem("xTopMenu", "xItemVideos", "Videos SubMenu", "xSubmenuVideos", "ExecuteWidget", "OPUS4A-174615", "gVideoBackgroundImage", false);
        tObject = new MenuItem("xItemTest", "xItemTestSub3", "Test 1 - 3", "xSubmenuTVScheduleRecord", null, null, "gTVBackgroundImage", false);
        tObject = new MenuItem("xItemTest", "xItemTestSub4", "Test 1 - 4", null, "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", false);
        
    }

    public static void ImportMenuItems(String ImportPath){

        Properties MenuItemProps = new Properties();
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(ImportPath);
            try {
                MenuItemProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: error inporting menus " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: error inporting menus " + util.class.getName() + ex);
            return;
        }
        
        //backup existing MenuItems before processing the import
        ExportMenuItems(PropertyBackupFile);
        
        if (MenuItemProps.size()>0){
            //clear the existing MenuItems from the list
            MenuItem.MenuItemList.clear();
            
            //load MenuItems
            for (String tPropertyKey : MenuItemProps.stringPropertyNames()){

//                PropLocation = SagePropertyLocation + tMenuItemName;
//                MenuItem NewMenuItem = new MenuItem(tMenuItemName);
//                NewMenuItem.setAction(sagex.api.Configuration.GetProperty(PropLocation + "/Action", null));
//                NewMenuItem.setActionType(sagex.api.Configuration.GetProperty(PropLocation + "/ActionType", null));
//                NewMenuItem.setBGImageFile(sagex.api.Configuration.GetProperty(PropLocation + "/BGImageFile", null));
//                NewMenuItem.setButtonText(sagex.api.Configuration.GetProperty(PropLocation + "/ButtonText", "<Not defined>"));
//                NewMenuItem.setName(sagex.api.Configuration.GetProperty(PropLocation + "/Name", tMenuItemName));
//                NewMenuItem.setParent(sagex.api.Configuration.GetProperty(PropLocation + "/Parent", "xTopMenu"));
//                NewMenuItem.setSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null));
//                NewMenuItem.setIsDefault(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
//                System.out.println("ADM: LoadMenuItemsFromSage: loaded - '" + tMenuItemName + "'");
            }

        }
        
        
        
    }
    
    public static void ExportMenuItems(String ExportFile){
        String PropLocation = "";
        String ExportFilePath = ADMLocation + "\\" + ExportFile;
        System.out.println("ADM: ExportMenuItems: Full Path = '" + ExportFilePath + "'");
        
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
            if (entry.getValue().getSubMenu()!=null){
                MenuItemProps.setProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            }
            MenuItemProps.setProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            System.out.println("ADM: ExportMenuItems: exported - '" + entry.getValue().getName() + "'");
        }         

        //if the export file exists then delete it before exporting
        
        //write the properties to the properties file
        try {
            FileOutputStream out = new FileOutputStream(ExportFilePath);
            try {
                MenuItemProps.store(out, PropertyComment);
                out.close();
            } catch (IOException ex) {
                System.out.println("ADM: error exporting menus " + util.class.getName() + ex);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: error exporting menus " + util.class.getName() + ex);
        }

        System.out.println("ADM: ExportMenuItems: exported " + MenuItem.MenuItemList.size() + " MenuItems");
        
        return;
    }
    
    public static void ExecuteWidget(String WidgetSymbol){
        UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: ExecuteWidget - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
        }else{
            System.out.println("ADM: ExecuteWidget - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

        try {
            sage.SageTV.apiUI(sagex.api.Global.GetUIContextName(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
        } catch (InvocationTargetException ex) {
            System.out.println("ADM: error executing widget" + util.class.getName() + ex);
        }
            
            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }
    
    public static String GetVersion() {
        return Version;
    }
    
    public static String GetADMLocation() {
        return ADMLocation;
    }

    
}
