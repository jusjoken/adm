/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import sagex.UIContext;

/**
 *
 * @author jusjoken
 */
public class Diamond {
    
    //class functions copied from diamond 3.30 release to support diamond Menu Customization

    public static final String PropName="JOrton/CustomViews";
    public static Map<String,Diamond.DefaultFlow> DiamondDefaultFlows = new LinkedHashMap<String,Diamond.DefaultFlow>();

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
        if (!IsDiamond()){
            return Boolean.FALSE;
        }
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
    
    public static void LoadDiamondWidgetswithQLM(){
        DiamondWidgetswithQLM(Boolean.TRUE);
    }
    
    public static void UnloadDiamondWidgetswithQLM(){
        DiamondWidgetswithQLM(Boolean.FALSE);
    }
    
    private static void DiamondWidgetswithQLM(Boolean Load){
        //show the Diamond Widgets
        String DiamondWidgetsPanelSymbol = "AOSCS-679196";
        //AOSCS-679196 or JUSJOKEN-167710 or JUSJOKEN-1236101
        String QLMWidgetsParentPanelSymbol = "JUSJOKEN-1236101";
        //JUSJOKEN-1236101 or JUSJOKEN-1079122
        
        //connect/disconnect the Widget panel from it's parent
        if (Load){
            sagex.api.WidgetAPI.InsertWidgetChild(new UIContext(sagex.api.Global.GetUIContextName()), QLMWidgetsParentPanelSymbol, DiamondWidgetsPanelSymbol,0);
        }else{
            sagex.api.WidgetAPI.RemoveWidgetChild(new UIContext(sagex.api.Global.GetUIContextName()), QLMWidgetsParentPanelSymbol, DiamondWidgetsPanelSymbol);
        }
        
    }

    //Use the diamond widget width property and return it for the panel width
    public static Double DiamondWidgetsPanelWidth(){
        return util.GetPropertyAsInteger("JOrton/MainMenu/MenuWidgetWidth", 6)*0.038;
    }

    public static void LoadDiamondDefaultFlows(){
        DiamondDefaultFlows.clear();
        DiamondDefaultFlows.put("LCKOF-346154", new DefaultFlow("Wall Flow", "LCKOF-346154", 0,Boolean.TRUE));
        DiamondDefaultFlows.put("LCKOF-346153", new DefaultFlow("Cover Flow", "LCKOF-346153", 1));
        DiamondDefaultFlows.put("LCKOF-346152", new DefaultFlow("List Flow", "LCKOF-346152", 2));
        DiamondDefaultFlows.put("PLUCKYHD-1486084", new DefaultFlow("SideWays Flow", "PLUCKYHD-1486084", 3));
        DiamondDefaultFlows.put("AOSCS-186340", new DefaultFlow("Category Flow", "AOSCS-186340", 4));
        DiamondDefaultFlows.put("LCKOF-392395", new DefaultFlow("360 Flow", "LCKOF-392395", 5));
    }
    
    public static class DefaultFlow{
        public String ButtonText = "";
        public String WidgetSymbol = "";
        public Integer SortOrder = 0;
        public Boolean Default = Boolean.FALSE;
        public static SortedMap<String,String> ListSorted = new TreeMap<String,String>();
        
        public DefaultFlow(String ButtonText, String WidgetSymbol, Integer SortOrder){
            this(ButtonText, WidgetSymbol, SortOrder, Boolean.FALSE);
        }
        public DefaultFlow(String ButtonText, String WidgetSymbol, Integer SortOrder, Boolean Default){
            this.ButtonText = ButtonText;
            this.WidgetSymbol = WidgetSymbol;
            this.SortOrder = SortOrder;
            this.Default = Default;
            ListSorted.put(this.ButtonText, this.WidgetSymbol);
        }
        
    }
    
}
