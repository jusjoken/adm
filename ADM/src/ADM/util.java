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
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.io.*;
import java.util.Collection;

public class util {

    public static String Version = "0.22";
    private static final String PropertyComment = "---ADM MenuItem Properties - Do Not Manually Edit---";
    private static final String SagePropertyLocation = "ADM/menuitem/";
    private static final String PropertyBackupFile = "ADMbackup.properties";
    private static final String ADMLocation = sagex.api.Utility.GetWorkingDirectory() + "\\userdata\\ADM";
    public static Boolean MenuListLoaded = false;
    public static MenuItem[] MenuList = new MenuItem[8];

    public static void LoadMenuList(){
        
        if (!MenuListLoaded) {

            //ensure the ADM file location exists
            try{
                boolean success = (new File(ADMLocation)).mkdirs();
                if (success) {
                    System.out.println("ADM: LoadMenuList - Directories created for '" + ADMLocation + "'");
                   }

                }catch (Exception ex){//Catch exception if any
                    System.out.println("ADM: LoadMenuList - error creating '" + ADMLocation + "'" + ex.getMessage());
                }
            
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
            sagex.api.Configuration.SetProperty(PropLocation + "/SortKey", entry.getValue().getSortKey().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            sagex.api.Configuration.SetProperty(PropLocation + "/HasSubMenu", entry.getValue().getHasSubMenu().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            sagex.api.Configuration.SetProperty(PropLocation + "/IsActive", entry.getValue().getIsActive().toString());
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
                NewMenuItem.setSortKey(sagex.api.Configuration.GetProperty(PropLocation + "/SortKey", null));
                NewMenuItem.setSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null));
                NewMenuItem.setHasSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/HasSubMenu", "false"));
                NewMenuItem.setIsDefault(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
                NewMenuItem.setIsActive(sagex.api.Configuration.GetProperty(PropLocation + "/IsActive", "true"));
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

        tObject = new MenuItem("xTopMenu", "xItemTV",1, "TV", true,"xSubmenuTV", "ExecuteWidget", "OPUS4A-174600", "gTVBackgroundImage", false,true);
        tObject = new MenuItem("xTopMenu", "xItemVideos",2, "Videos SubMenu", true,"xSubmenuVideos", "ExecuteWidget", "OPUS4A-174615", "gVideoBackgroundImage", false,true);
        tObject = new MenuItem("xTopMenu", "xItemMusic",3, "Music", true, "xSubmenuMusic", "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false,true);
        tObject = new MenuItem("xTopMenu", "xItemTestTop",4, "Test 1", true, null, "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage", false,true);
        tObject = new MenuItem("xTopMenu", "xItemPhotos",5, "Photos", true,"xSubmenuPhotos", "ExecuteWidget", "OPUS4A-174617", "gPhotoBackgroundImage", false,true);
        tObject = new MenuItem("xTopMenu", "xDetailedSetup",6, "Detailed Setup",false, null, "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage", false,true);
        tObject = new MenuItem("xItemTest", "xItemTestSub1",7, "Test 1 - 1", true,"xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174604", "gTVBackgroundImage", false,true);
        tObject = new MenuItem("xItemTest", "xItemTestSub2",8, "Test 1 - 2", true,"xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", true,true);
        tObject = new MenuItem("xItemTest", "xItemTestSub3",9, "Test 1 - 3", true,"xSubmenuTVScheduleRecord", null, null, "gTVBackgroundImage", false,true);
        tObject = new MenuItem("xItemTest", "xItemTestSub4",10, "Test 1 - 4", false, null, "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage", false,true);
        
    }

    public static void ImportMenuItems(String ImportPath){

        if (ImportPath==null){
            System.out.println("ADM: ImportMenuItems: null ImportPath passed.");
            return;
        }
        
        Properties MenuItemProps = new Properties();
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(ImportPath);
            try {
                MenuItemProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: ImportMenuItems: error inporting menus " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: ImportMenuItems: error inporting menus " + util.class.getName() + ex);
            return;
        }
        
        //backup existing MenuItems before processing the import
        ExportMenuItems(PropertyBackupFile);
        
        if (MenuItemProps.size()>0){
            //clean up existing MenuItems from the SageTV properties file before writing the new ones
            sagex.api.Configuration.RemovePropertyAndChildren(SagePropertyLocation);
            
            //load MenuItems from the properties file and write to the Sage properties
            for (String tPropertyKey : MenuItemProps.stringPropertyNames()){

                sagex.api.Configuration.SetProperty(tPropertyKey, MenuItemProps.getProperty(tPropertyKey));
                
                System.out.println("ADM: ImportMenuItems: imported - '" + tPropertyKey + "' = '" + MenuItemProps.getProperty(tPropertyKey) + "'");
            }
            
            //now load the properties from the Sage properties file
            LoadMenuItemsFromSage();

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
            if (entry.getValue().getSortKey()!=null){
                MenuItemProps.setProperty(PropLocation + "/SortKey", entry.getValue().getSortKey().toString());
            }
            if (entry.getValue().getSubMenu()!=null){
                MenuItemProps.setProperty(PropLocation + "/SubMenu", entry.getValue().getSubMenu());
            }
            MenuItemProps.setProperty(PropLocation + "/HasSubMenu", entry.getValue().getHasSubMenu().toString());
            MenuItemProps.setProperty(PropLocation + "/IsDefault", entry.getValue().getIsDefault().toString());
            MenuItemProps.setProperty(PropLocation + "/IsActive", entry.getValue().getIsActive().toString());
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
                System.out.println("ADM: ExportMenuItems: error exporting menus " + util.class.getName() + ex);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: ExportMenuItems: error exporting menus " + util.class.getName() + ex);
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
            System.out.println("ADM: ExecuteWidget: error executing widget" + util.class.getName() + ex);
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
    
    public static String GetVersion() {
        return Version;
    }
    
    public static String GetADMLocation() {
        return ADMLocation;
    }

    
}
