/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.util.Arrays;
import java.util.Collection;
import sagex.UIContext;

/**
 *
 * @author jusjoken
 */
public class Diamond {
    
    //class functions copied from diamond 3.30 release to support diamond Menu Customization

    public static final String PropName="JOrton/CustomViews";

//    public static Object GetCustomViews(){
//        String views=sagex.api.Configuration.GetProperty(new UIContext(sagex.api.Global.GetUIContextName()),PropName,"");
//        if(views.contains(";")){	
//            System.out.println("ADM Diamond : GetCustomViews = '" + views.split(";") + "'");
//            return views.split(";");
//        }
//        System.out.println("ADM Diamond : GetCustomViews = '" + views + "'");
//        return views;
//    }
//
    public static Collection<String> GetCustomViews(){
        String views=sagex.api.Configuration.GetProperty(new UIContext(sagex.api.Global.GetUIContextName()),PropName,"");
        if(views.contains(";")){	
            System.out.println("ADM Diamond : GetCustomViews (split) = '" + views.split(";") + "'");
            return Arrays.asList(views.split(";"));
        }
        System.out.println("ADM Diamond : GetCustomViews (single) = '" + views + "'");
        return Arrays.asList(views);
    }

    public static String GetViewName(String name){
        String[] SplitString = name.split("&&");
        if (SplitString.length == 2) {
            System.out.println("ADM Diamond : GetViewName("+name+") = '" + SplitString[0] + "'");
            return SplitString[0];
        } else {
            System.out.println("ADM Diamond : Not Found: GetViewName("+name+")");
            return util.OptionNotFound;
        }
    }

    public static void RenameFlow(String OldName, String NewName){
        //sagediamond.CustomViews.RenameView();
    }
    
    public static Boolean IsDiamond(){
        String DiamondPluginID = "DiamondSTVi";
        String DiamondWidgetSymbol = "AOSCS-65";
        // check to see if the Diamond Plugin is installed
        Object[] FoundWidget = new Object[1];
        FoundWidget[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(new UIContext(sagex.api.Global.GetUIContextName()), DiamondWidgetSymbol);
        if (sagex.api.PluginAPI.IsPluginEnabled(sagex.api.PluginAPI.GetAvailablePluginForID(DiamondPluginID)) || FoundWidget[0]!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
}
