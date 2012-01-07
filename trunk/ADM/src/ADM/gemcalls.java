/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.util.ArrayList;
import sagex.UIContext;
import Diamond.Flow;

/**
 *
 * @author jusjoken
 */
public class gemcalls {
    
    public static Boolean Isgemstone(){
        String gemstoneWidgetSymbol = "JUSJOKEN-3084835";
        // check to see if the gemstone Plugin is installed
        Object[] FoundWidget = new Object[1];
        FoundWidget[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(new UIContext(sagex.api.Global.GetUIContextName()), gemstoneWidgetSymbol);
        if (FoundWidget[0]!=null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static ArrayList<String> GetFlows(){
        ArrayList<String> tList = new ArrayList<String>();
        try {
            tList = Flow.GetFlows();
        } catch (NoClassDefFoundError e) {
            System.out.println("ADM gemcalls : gemstone class not found '" + e + "'");
        }
        return tList;
    }
    
}
