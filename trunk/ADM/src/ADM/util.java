/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */

import java.util.ArrayList;
import sagex.UIContext;
import java.lang.reflect.InvocationTargetException;


public class util {

    public static String Version = "0.10";
    
    public static Boolean MenuListLoaded = false;
    
    public static MenuItem[] MenuList = new MenuItem[5];

    public static void LoadMenuList(){
        
        if (!MenuListLoaded) {
            
            MenuList[0] = new MenuItem("xTopMenu", "xItemTV", "TV", "xSubmenuTV", "ExecuteWidget", "OPUS4A-174600", "gTVBackgroundImage");
            MenuList[1] = new MenuItem("xTopMenu", "xItemMusic", "Music", "xSubmenuMusic", "ExecuteWidget", "OPUS4A-174613", "C:\\Program Files\\SageTV\\SageTV\\STVs\\SageTV7\\Themes\\Standard\\MusicBackground.jpg");
            MenuList[2] = new MenuItem("xTopMenu", "xItemPhotos", "Photos", "xSubmenuPhotos", "ExecuteWidget", "OPUS4A-174617", "gPhotoBackgroundImage");
            MenuList[3] = new MenuItem("xTopMenu", "xItemVideos", "Videos SubMenu", "xSubmenuVideos", "ExecuteWidget", "OPUS4A-174615", "gVideoBackgroundImage");
            MenuList[4] = new MenuItem("xTopMenu", "DetailedSetup", "Detailed Setup", null, "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage");

            MenuListLoaded = true;

            System.out.println("JUSJOKEN: LoadMenuList - Loaded menu list");

        }

    }
    
    
//    public static MenuItem GetMenuItem(int Item){
//        return MenuList[Item];
//    }
//            
//    public static String GetMenuItemButtonText(int Item){
//        //return MenuList[Item].ButtonText;
//        return MenuItem.ButtonTextList.get(Item);
//    }
//
//    public static ArrayList<String> GetMenuItemButtonTextList(){
//        return MenuItem.ButtonTextList;
//    }
    
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
