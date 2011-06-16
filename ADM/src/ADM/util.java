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
            
            MenuList[0] = new MenuItem("MenuItem1", "Menu Item 1", "DoNothing", "LaunchMenuItem1", null);
            MenuList[1] = new MenuItem("MenuItem2", "Menu Item 2", "DoNothing", "LaunchMenuItem2", null);
            MenuList[2] = new MenuItem("MenuItem3", "Menu Item 3", "DoNothing", "LaunchMenuItem3", "gBackgroundImage");
            MenuList[3] = new MenuItem("MenuItem4", "Menu 4 SubMenu", "xMenuItem4", "gBackgroundImage");
            MenuList[4] = new MenuItem("DetailedSetup", "Detailed Setup", "ExecuteWidget", "OPUS4A-174758", "gSettingsBackgroundImage");

            MenuListLoaded = true;

            System.out.println("JUSJOKEN: LoadMenuList - Loaded menu list");

        }

    }
    
    
    public static MenuItem GetMenuItem(int Item){
        return MenuList[Item];
    }
            
    public static String GetMenuItemButtonText(int Item){
        //return MenuList[Item].ButtonText;
        return MenuItem.ButtonTextList.get(Item);
    }

    public static ArrayList<String> GetMenuItemButtonTextList(){
        return MenuItem.ButtonTextList;
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
