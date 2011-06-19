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


public class util {

    public static String Version = "0.10";
    
    public static Boolean MenuListLoaded = false;
    
    public static MenuItem[] MenuList = new MenuItem[8];

    public static void LoadMenuList(){
        
        if (!MenuListLoaded) {
            
//            MenuList[0] = new MenuItem("xTopMenu", "xItemTV", "TV", "xSubmenuTV", "ExecuteWidget", "OPUS4A-174600", "gTVBackgroundImage");
//            MenuList[1] = new MenuItem("xTopMenu", "xItemMusic", "Music", "xSubmenuMusic", "ExecuteWidget", "OPUS4A-174613", "gMusicBackgroundImage");
//            MenuList[2] = new MenuItem("xTopMenu", "xItemTestTop", "Test 1", "xItemTest", null, null, "gMusicBackgroundImage");
//            MenuList[3] = new MenuItem("xItemTest", "xItemTestSub1", "Test 1 - 1", "xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174604", "gTVBackgroundImage");
//            MenuList[4] = new MenuItem("xItemTest", "xItemTestSub2", "Test 1 - 2", "xSubmenuTVScheduleRecord", "ExecuteWidget", "OPUS4A-174617", "gTVBackgroundImage");
//            MenuList[5] = new MenuItem("xTopMenu", "xItemPhotos", "Photos", "xSubmenuPhotos", "ExecuteWidget", "OPUS4A-174617", "gPhotoBackgroundImage");
//            MenuList[6] = new MenuItem("xTopMenu", "xDetailedSetup", "Detailed Setup", null, "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage");
//            MenuList[7] = new MenuItem("xTopMenu", "xItemVideos", "Videos SubMenu", "xSubmenuVideos", "ExecuteWidget", "OPUS4A-174615", "gVideoBackgroundImage");

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
            
            MenuListLoaded = true;

            System.out.println("JUSJOKEN: LoadMenuList - Loaded menu list:" + MenuItem.GetMenuItemNameList());

        }

    }
    
    public static void ExecuteWidget(String WidgetSymbol){
        UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("JUSJOKEN: ExecuteWidget - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
        }else{
            System.out.println("JUSJOKEN: ExecuteWidget - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

        try {
            sage.SageTV.apiUI(sagex.api.Global.GetUIContextName(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
        } catch (InvocationTargetException ex) {
            System.out.println("JUSJOKEN: error executing widget" + util.class.getName() + ex);
        }
            
            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }
    
    public static String GetVersion() {
        return Version;
    }
    

    
}
