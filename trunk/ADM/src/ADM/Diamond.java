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
//        String views=util.GetProperty(PropName,"");
//        if(views.contains(";")){	
//            System.out.println("ADM Diamond : GetCustomViews = '" + views.split(";") + "'");
//            return views.split(";");
//        }
//        System.out.println("ADM Diamond : GetCustomViews = '" + views + "'");
//        return views;
//    }
//
    public static Collection<String> GetCustomViews(){
        String views=util.GetProperty(PropName,"");
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
  
    public static Boolean ShowWidgetswithQLM(){
        //ensure Diamond is installed and enabled
//        if (!IsDiamond()){
//            return Boolean.FALSE;
//        }
        //ensure at minimum that the option is enabled in QLM
        Boolean OptionOn = util.GetPropertyAsBoolean(util.SageADMSettingsPropertyLocation + "/qlm_show_diamond_widgets", Boolean.FALSE);
        if (OptionOn){
            //now ensure that the Diamond Widget options are turned on and should be showing
            String WidgetPanel = "JOrton/MainMenu/WidgetPanel";
            String Off = "Off";
            if (!util.GetProperty(WidgetPanel + "1", Off).equals(Off) || !util.GetProperty(WidgetPanel + "2", Off).equals(Off) || !util.GetProperty(WidgetPanel + "3", Off).equals(Off) || !util.GetProperty(WidgetPanel + "4", Off).equals(Off)){
                //as at least one panel is On then Show the Widget Panel
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }else{
            return Boolean.FALSE;
        }
    }
    
    public static void ExecuteWidgetswithQLM(){
        //show the Diamond Widgets
        String DiamondWidgetsPanelSymbol = "JUSJOKEN-167710";
        Action.ExecuteWidget(DiamondWidgetsPanelSymbol);
    }

    
}
